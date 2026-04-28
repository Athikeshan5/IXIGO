package com.ixigo.testing.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.Set;

public class CookieManager {

    private static final String COOKIE_DIR  = "cookies";
    private static final String COOKIE_FILE = COOKIE_DIR + "/ixigo_cookies.data";
    private static final String IXIGO_URL   = "https://www.ixigo.com/trains";

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait longWait;

    // LOCATORS

    private final By mobileInput = By.xpath(
        "//input[@placeholder='Enter Mobile Number']"
        + " | //input[@inputmode='numeric' and contains(@class,'text-primary')]"
        + " | //span[contains(@class,'c-phone-email-input-wrapper')]//input"
    );

    // LOGIN button
    private final By continueBtn = By.xpath(
        "//button[contains(@class,'bg-brand-solid') and not(contains(@class,'disabled'))]"
        + " | //button[normalize-space()='Continue' and not(@disabled)]"
        + " | //button[normalize-space()='LOGIN' and not(@disabled)]"
        + " | //button[normalize-space()='GET OTP' and not(@disabled)]"
    );

    // OTP input field
    private final By otpInput = By.xpath(
        "//input[@inputmode='numeric' and @maxlength='1']"
        + " | //input[contains(@placeholder,'OTP')]"
        + " | //input[contains(@placeholder,'Enter OTP')]"
    );

    // Element visible only when user is logged in
    private final By loggedInIndicator = By.xpath(
        "//*[contains(@class,'user-avatar')]"
        + " | //*[contains(@class,'profile-icon')]"
        + " | //*[contains(@class,'account-icon')]"
        + " | //span[contains(text(),'Hi,')]"
        + " | //*[contains(@class,'user-name')]"
    );

    // Login popup heading 
    private final By loginPopupHeading = By.xpath(
        "//*[contains(text(),'Log in to ixigo')]"
        + " | //*[contains(text(),'Login to ixigo')]"
        + " | //*[contains(text(),'Sign in to ixigo')]"
    );

    public CookieManager(WebDriver driver) {
        this.driver   = driver;
        this.wait     = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(90));
    }


    public void loginWithCookiesOrManual(String mobileNumber) {
        if (cookieFileExists()) {
            System.out.println("[CookieManager] Cookie file found attempting cookie login");
            boolean loginSuccess = loadCookiesAndVerify();
            if (loginSuccess) {
                System.out.println("[CookieManager] Cookie login SUCCESS — already logged in");
                return;
            }
            System.out.println("[CookieManager] Cookies expired — falling back to manual OTP");
        } else {
            System.out.println("[CookieManager] No cookie file found  performing first-time login");
        }

        // Manual OTP login
        doManualLogin(mobileNumber);
    }

   
    public void performFirstTimeLogin(String mobileNumber) {
        System.out.println("[CookieManager] Starting first-time login for: " + mobileNumber);

        // Navigate to ixigo trains
        try {
            driver.get(IXIGO_URL);
            sleep(3000);
        } catch (Exception e) {
            System.out.println("[CookieManager] Navigation: " + e.getMessage());
        }

        // Check if already logged in
        if (isLoggedIn()) {
            System.out.println("[CookieManager] Already logged in saving cookies");
            saveCookies();
            return;
        }

        // Open login popup
        openLoginPopup();

        // Do login flow
        doManualLogin(mobileNumber);
    }

    private boolean loadCookiesAndVerify() {
        try {
            // Must be on ixigo.com domain before adding cookies
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("ixigo.com")) {
                driver.get(IXIGO_URL);
                sleep(2000);
            }

            // Clear existing cookies and load saved ones
            driver.manage().deleteAllCookies();
            loadCookiesFromFile();

            // Refresh to apply cookies
            driver.navigate().refresh();
            sleep(3000);

            // Verify login
            return isLoggedIn();

        } catch (Exception e) {
            System.out.println("[CookieManager] loadCookiesAndVerify error: " + e.getMessage());
            return false;
        }
    }

   
    private void doManualLogin(String mobileNumber) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Wait for the mobile input inside the popup
            System.out.println("[CookieManager] Waiting for mobile input...");
            WebElement mobileEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(mobileInput));

            // Clear and enter mobile number
            mobileEl.clear();
            sleep(300);
            mobileEl.sendKeys(mobileNumber);
            System.out.println("[CookieManager] Mobile entered: " + mobileNumber);
            sleep(800);

            // Click Continue button
            try {
                WebElement btn = wait.until(
                    ExpectedConditions.elementToBeClickable(continueBtn));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                sleep(300);
                try {
                    btn.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", btn);
                }
                System.out.println("[CookieManager] Continue/LOGIN clicked");
            } catch (Exception e) {
                // Fallback: press Enter on mobile field
                mobileEl.sendKeys(Keys.ENTER);
                System.out.println("[CookieManager] Pressed ENTER to submit");
            }

            // Wait for OTP — 90 seconds for manual entry
            System.out.println("[CookieManager] ============================================");
            System.out.println("[CookieManager] ENTER THE OTP IN THE BROWSER NOW");
            System.out.println("[CookieManager] You have 90 seconds...");
            System.out.println("[CookieManager] ============================================");

            // Count down in 10s intervals
            for (int i = 90; i > 0; i -= 10) {
                System.out.println("[CookieManager] Time " + i + "s remaining for OTP...");
                sleep(10000);

                // Check if already logged in (OTP accepted early)
                if (isLoggedIn()) {
                    System.out.println("[CookieManager] OTP accepted — logged in!");
                    break;
                }
            }

            // Final verification
            if (isLoggedIn()) {
                System.out.println("[CookieManager] Login successful saving cookies");
                saveCookies();
            } else {
                System.out.println("[CookieManager] Login may not have completed saving cookies anyway");
                saveCookies();
            }

        } catch (Exception e) {
            System.out.println("[CookieManager] doManualLogin error: " + e.getMessage());
        }
    }

   
    private void openLoginPopup() {
        try {
            By loginSignupBtn = By.xpath(
                "//button[contains(normalize-space(),'Log in') or contains(normalize-space(),'Sign up')]"
                + " | //*[contains(@class,'login') and contains(@role,'button')]"
            );
            WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(loginSignupBtn));
            btn.click();
            System.out.println("[CookieManager] Login popup opened");
            sleep(1500);

            // Verify popup appeared
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPopupHeading));

        } catch (Exception e) {
            System.out.println("[CookieManager] openLoginPopup: " + e.getMessage());
        }
    }

  
    private boolean isLoggedIn() {
        try {
            // Look for user profile indicator
            WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            quickWait.until(ExpectedConditions.visibilityOfElementLocated(loggedInIndicator));
            return true;
        } catch (Exception ignored) {}

        try {
            // If login popup heading is gone AND we're on a non-login page
            String url = driver.getCurrentUrl();
            if (url.contains("ixigo.com") && !url.contains("login") && !url.contains("signin")) {
                // Check if "Log in/Sign up" button is present
                By loginSignupBtn = By.xpath(
                    "//button[normalize-space()='Log in/Sign up']"
                    + " | //button[contains(normalize-space(),'Log in')]"
                );
                java.util.List<WebElement> elements = driver.findElements(loginSignupBtn);
                if (elements.isEmpty()) {
                    return true;
                }
            }
        } catch (Exception ignored) {}

        return false;
    }

    public void saveCookies() {
        try {
            // Create cookies directory
            Files.createDirectories(Paths.get(COOKIE_DIR));

            Set<Cookie> cookies = driver.manage().getCookies();
            StringBuilder sb = new StringBuilder();

            for (Cookie c : cookies) {
                // Format: name||value||domain||path||expiry||isSecure||isHttpOnly
                sb.append(c.getName()).append("||")
                  .append(c.getValue()).append("||")
                  .append(c.getDomain() != null ? c.getDomain() : "").append("||")
                  .append(c.getPath() != null ? c.getPath() : "/").append("||")
                  .append(c.getExpiry() != null ? c.getExpiry().getTime() : "").append("||")
                  .append(c.isSecure()).append("||")
                  .append(c.isHttpOnly())
                  .append("\n");
            }

            Files.write(Paths.get(COOKIE_FILE), sb.toString().getBytes());
            System.out.println("[CookieManager] ✅ Saved " + cookies.size() + " cookies to: " + COOKIE_FILE);

        } catch (Exception e) {
            System.out.println("[CookieManager] saveCookies error: " + e.getMessage());
        }
    }

    private void loadCookiesFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(COOKIE_FILE)));
            String[] lines = content.split("\n");
            int loaded = 0;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String[] parts = line.split("\\|\\|", -1);
                    if (parts.length < 4) continue;

                    String name    = parts[0];
                    String value   = parts[1];
                    String domain  = parts[2];
                    String path    = parts[3].isEmpty() ? "/" : parts[3];
                    java.util.Date expiry = null;

                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        try {
                            long expiryMs = Long.parseLong(parts[4]);
                            expiry = new java.util.Date(expiryMs);
                            // Skip expired cookies
                            if (expiry.before(new java.util.Date())) continue;
                        } catch (NumberFormatException ignored) {}
                    }

                    boolean isSecure   = parts.length > 5 && "true".equals(parts[5]);
                    boolean isHttpOnly = parts.length > 6 && "true".equals(parts[6]);

                    Cookie cookie = new Cookie.Builder(name, value)
                        .domain(domain.isEmpty() ? ".ixigo.com" : domain)
                        .path(path)
                        .expiresOn(expiry)
                        .isSecure(isSecure)
                        .isHttpOnly(isHttpOnly)
                        .build();

                    driver.manage().addCookie(cookie);
                    loaded++;

                } catch (Exception e) {
                    // Skip 
                }
            }

            System.out.println("[CookieManager] Loaded " + loaded + " cookies from: " + COOKIE_FILE);

        } catch (Exception e) {
            System.out.println("[CookieManager] loadCookiesFromFile error: " + e.getMessage());
        }
    }

    public static void clearSavedCookies() {
        try {
            Files.deleteIfExists(Paths.get(COOKIE_FILE));
            System.out.println("[CookieManager] Cookie file deleted — next run will require OTP");
        } catch (Exception e) {
            System.out.println("[CookieManager] clearSavedCookies: " + e.getMessage());
        }
    }

    private boolean cookieFileExists() {
        return Files.exists(Paths.get(COOKIE_FILE));
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

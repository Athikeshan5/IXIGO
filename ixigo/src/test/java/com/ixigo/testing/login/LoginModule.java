package com.ixigo.testing.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.CookieManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;


public class LoginModule {

    private static final String COOKIE_FILE = "cookies/ixigo_cookies.data";

    @Test(description = "Perform one-time login and save cookies for all test modules")
    public void performLoginAndSaveCookies() {

        AllUtilityFunctions util = new AllUtilityFunctions();

        // Read config
        String browser      = util.getPropertyValue("browser");
        String mobileNumber = util.getPropertyValue("mobileNumber");
        String url          = util.getPropertyValue("url");

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  IXIGO LOGIN MODULE — One-Time Session Setup");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  Browser      : " + browser);
        System.out.println("  Mobile       : " + mobileNumber);
        System.out.println("  URL          : " + url);
        System.out.println("  Cookie file  : " + COOKIE_FILE);
        System.out.println("═══════════════════════════════════════════════════════");

        // Always delete old cookie file before re-login to ensure fresh cookies
        // This fixes the issue where stale cookies were loaded and login wasn't detected
        if (Files.exists(Paths.get(COOKIE_FILE))) {
            System.out.println("[LoginModule] Deleting old cookie file to ensure fresh login...");
            try { Files.delete(Paths.get(COOKIE_FILE)); } catch (Exception ignored) {}
        }

        // Create directories
        new File("cookies").mkdirs();
        new File("ScreenShot").mkdirs();
        new File("Reports").mkdirs();

        WebDriver driver = null;

        try {
            // Launch browser
            driver = launchBrowser(browser);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
            driver.manage().window().maximize();

            System.out.println("[LoginModule] Browser launched: " + browser);

            // Use CookieManager to perform the login
            CookieManager cookieManager = new CookieManager(driver);
            cookieManager.performFirstTimeLogin(mobileNumber);

            // Final confirmation
            if (Files.exists(Paths.get(COOKIE_FILE))) {
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("  LOGIN MODULE COMPLETE");
                System.out.println("  Cookies saved to: " + COOKIE_FILE);
                System.out.println("  All test modules will now use these cookies.");
                System.out.println("  You will NOT be asked to log in again.");
                System.out.println("═══════════════════════════════════════════════════════");
            } else {
                System.out.println("[LoginModule] Cookie file was not created. Check logs above.");
            }

        } catch (Exception e) {
            System.out.println("[LoginModule] Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                try {
                    Thread.sleep(2000);
                    driver.quit();
                    System.out.println("[LoginModule] Browser closed.");
                } catch (Exception ignored) {}
            }
        }
    }

    private WebDriver launchBrowser(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions opts = new ChromeOptions();
                // opts.addArguments("--headless=new"); // Uncomment for headless
                return new ChromeDriver(opts);
            case "edge":
                return new EdgeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Invalid browser in commonData.properties: " + browser);
        }
    }
}

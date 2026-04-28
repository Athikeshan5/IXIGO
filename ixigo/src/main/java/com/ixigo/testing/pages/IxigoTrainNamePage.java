package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.SeatAvailabilityHandler;

public class IxigoTrainNamePage {

    // locators

    @FindBy(xpath = "//a[contains(@href,'/trains/name-number')]")
    private WebElement searchByName;

    @FindBy(xpath = "//input[@placeholder='Search trains by name or number']")
    private WebElement trainInput;

    // First train result in the suggestion list
    @FindBy(xpath = "(//div[contains(@class,'flex pb-15')])[1]")
    private WebElement firstTrain;

    // "View 2 Months" calendar link on train detail/availability page
    @FindBy(xpath = "//a[contains(@class,'calendar-page-link')]")
    private WebElement viewCalendar;

    // ACTIONS

    public void goToTrainNamePage(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        util.waitForClickable(driver, searchByName, 20);
        searchByName.click();
    }

    public void enterTrainName(WebDriver driver, String train) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(trainInput));
        trainInput.click();
        trainInput.clear();
        trainInput.sendKeys(train);
        util.sleep(2);
        trainInput.sendKeys(Keys.ENTER);
    }

    public void selectTrain(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(firstTrain));
        firstTrain.click();
        System.out.println("Train selected from list");
    }

   
    public void selectAvailableDate(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        util.sleep(2);

        System.out.println("selectAvailableDate — current URL: " + driver.getCurrentUrl());

        // ── If already on seat-availability, delegate immediately ─────────────
        SeatAvailabilityHandler seatHandler = new SeatAvailabilityHandler(driver);
        if (seatHandler.isOnSeatAvailabilityPage()) {
            System.out.println("Already on seat-availability — delegating to handler");
            seatHandler.handleSeatAvailabilityAndBook();
            return;
        }

        // Click "Availability" tab if present
        try {
            By availTab = By.xpath(
                "//a[normalize-space()='Availability' or normalize-space()='AVAILABILITY']" +
                " | //li[normalize-space()='Availability']" +
                " | //div[normalize-space()='Availability' and (contains(@class,'tab') or contains(@class,'link'))]" +
                " | //span[normalize-space()='Availability']/parent::*[self::a or self::li or self::div]"
            );
            WebElement tab = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(availTab));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
            util.sleep(1);
            try { tab.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", tab); }
            System.out.println("Clicked Availability tab");
            util.sleep(2);
        } catch (Exception e) {
            System.out.println("Availability tab not found (may already be on it): " + e.getMessage());
        }

        // Try class boxes on train detail page 
        js.executeScript("window.scrollBy(0, 300)");
        util.sleep(1);

        // New UI class boxes: on train detail page these are clickable tabs like "2S", "SL", "3A", "2A", "1A"
        // They live inside the availability section
        By classTabLocator = By.xpath(
            "//div[contains(@class,'train-class-main') and contains(@class,'green')]" +
            " | //div[contains(@class,'class-tab') and contains(.,'AVL')]" +
            " | //div[contains(@class,'class-item') and contains(.,'AVL')]" +
            " | //button[contains(@class,'class') and (contains(.,'2S') or contains(.,'SL') or contains(.,'3A') or contains(.,'2A') or contains(.,'1A'))]" +
            " | //div[contains(@class,'seat-class') and not(contains(@class,'disabled'))]"
        );

        boolean classClicked = false;
        try {
            List<WebElement> classTabs = driver.findElements(classTabLocator);
            for (WebElement tab : classTabs) {
                try {
                    if (tab.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
                        util.sleep(1);
                        try { tab.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", tab); }
                        System.out.println("Clicked class tab: " + tab.getText().trim().replace("\n", " "));
                        classClicked = true;
                        util.sleep(2);
                        break;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("Class tab search failed: " + e.getMessage());
        }

        // STEP 3: After clicking class tab, check for seat-availability 
        if (classClicked) {
            util.sleep(2);
            if (seatHandler.isOnSeatAvailabilityPage()) {
                System.out.println("Redirected to seat-availability after class click");
                seatHandler.handleSeatAvailabilityAndBook();
                return;
            }
        }

        // STEP 4: Try day tiles directly on the page
        js.executeScript("window.scrollBy(0, 400)");
        util.sleep(2);

        By dayItemsAll = By.xpath("//div[contains(@class,'day-item')]");
        boolean dateClicked = false;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(dayItemsAll));
            List<WebElement> days = driver.findElements(dayItemsAll);

            // First pass: look for AVL tiles
            for (WebElement day : days) {
                try {
                    String text = day.getText();
                    if (text.contains("AVL") && !text.contains("NOT AVL")) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                        util.sleep(1);
                        try { day.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", day); }
                        System.out.println("Selected AVL date tile: " + text.trim());
                        dateClicked = true;
                        util.sleep(2);
                        break;
                    }
                } catch (Exception ignored) {}
            }

            // Second pass: try 2-month calendar if no direct AVL tile found
            if (!dateClicked) {
                System.out.println("No AVL in strip → Clicking 2 Months Calendar");
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(viewCalendar));
                    js.executeScript("arguments[0].click();", viewCalendar);
                    util.sleep(3);
                    List<WebElement> fullDates = driver.findElements(dayItemsAll);
                    for (WebElement day : fullDates) {
                        String text = day.getText();
                        if (text.contains("AVL") && !text.contains("NOT AVL")) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                            util.sleep(1);
                            js.executeScript("arguments[0].click();", day);
                            System.out.println("Clicked AVL date from full calendar: " + text.trim());
                            dateClicked = true;
                            util.sleep(2);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Calendar view fallback failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Day tiles not found: " + e.getMessage());
        }

        if (!dateClicked) {
            System.out.println("No AVL date found — train may be fully booked. Proceeding to assert.");
            return;
        }

        // STEP 5: Check if redirected to seat-availability after date click
        util.sleep(2);
        if (seatHandler.isOnSeatAvailabilityPage()) {
            System.out.println("Redirected to seat-availability after date click");
            seatHandler.handleSeatAvailabilityAndBook();
        }
    }

    
    public void clickBook(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // If already on booking page, skip
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("/booking/") || currentUrl.contains("/payment") || currentUrl.contains("/checkout")) {
            System.out.println("Already on booking/payment page — skipping BOOK click");
            return;
        }

        // Check if login popup is already visible
        try {
            By loginPopup = By.xpath(
                "//div[contains(@class,'ixigo-login')] | //*[contains(text(),'Log in to ixigo')]"
            );
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPopup));
            System.out.println("Login popup already visible — skipping BOOK click");
            return;
        } catch (Exception ignored) {}

        System.out.println("Clicking BOOK button...");

        String[] bookXpaths = {
            "//div[contains(@class,'book-btn')]//button",
            "//button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]",
            "//div[contains(@class,'sticky') or contains(@class,'bottom') or contains(@class,'fixed')]//button[contains(.,'Book') or contains(.,'BOOK')]",
            "//button[normalize-space()='BOOK']",
            "//button[normalize-space()='Book']",
            "//button[normalize-space()='Book Now']",
            "//button[contains(text(),'BOOK')]"
        };

        for (String xpath : bookXpaths) {
            try {
                List<WebElement> btns = driver.findElements(By.xpath(xpath));
                for (WebElement btn : btns) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        util.sleep(1);
                        try { btn.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("Clicked BOOK via: " + xpath);
                        util.sleep(2);

                        handlePostBookRedirect(driver, js, util);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }

        // Full button scan
        for (WebElement btn : driver.findElements(By.tagName("button"))) {
            try {
                String t = btn.getText().trim().toUpperCase();
                if ((t.equals("BOOK") || t.equals("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    util.sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("Clicked BOOK via full scan");
                    util.sleep(2);
                    handlePostBookRedirect(driver, js, util);
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — login popup may appear directly");
    }

  
    private void handlePostBookRedirect(WebDriver driver, JavascriptExecutor js, AllUtilityFunctions util) {
        String url = driver.getCurrentUrl();
        System.out.println("Post-BOOK URL: " + url);

        // Already on booking/payment
        if (url.contains("/booking/") || url.contains("/payment") || url.contains("/checkout")) {
            System.out.println("On booking page — no redirect needed");
            return;
        }

        // Train info page: /trains/12610 or similar numeric
        if (url.matches(".*\\/trains\\/\\d+[^/]*$") && !url.contains("seat-availability")) {
            System.out.println("Landed on train info page — navigating to seat-availability");
            try {
                String trainNum = url.replaceAll(".*\\/trains\\/(\\d+).*", "$1");
                String seatAvailUrl = "https://www.ixigo.com/trains/" + trainNum + "/seat-availability";
                driver.get(seatAvailUrl);
                System.out.println("Navigated to seat-availability: " + seatAvailUrl);
                util.sleep(3);
                SeatAvailabilityHandler seatHandler = new SeatAvailabilityHandler(driver);
                if (seatHandler.isOnSeatAvailabilityPage()) {
                    seatHandler.handleSeatAvailabilityAndBook();
                }
            } catch (Exception e) {
                System.out.println("Seat-availability navigation failed: " + e.getMessage());
            }
        }
    }
}
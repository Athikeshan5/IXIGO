package com.ixigo.testing.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

import java.time.Duration;
import java.util.List;

public class IxigoPlatformPage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(xpath = "//span[text()='Trains']")
    private WebElement trainsTab;

    @FindBy(xpath = "//a[contains(@href,'platform-locator')]")
    private WebElement platformLocator;

    @FindBy(xpath = "//input[@placeholder='Enter the train name or number']")
    private WebElement trainInput;

    @FindBy(xpath = "//div[contains(@class,'result-row') and contains(@class,'selected')]")
    private WebElement suggestion;

    @FindBy(xpath = "//button[contains(text(),'Search Platform')]")
    private WebElement searchBtn;

    @FindBy(xpath = "//div[contains(@class,'train-class-main') and contains(@class,'green')]")
    private WebElement avlClassBox;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

    public void navigateToPlatformLocator(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        driver.get("https://www.ixigo.com");
        dismissPopupIfPresent(driver);

        wait.until(ExpectedConditions.elementToBeClickable(trainsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(platformLocator)).click();
        System.out.println("Navigated to Platform Locator");
    }

    private void dismissPopupIfPresent(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        try {
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(8));
            w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("wiz-iframe-intent"));
            w.until(ExpectedConditions.elementToBeClickable(By.id("closeButton"))).click();
            driver.switchTo().defaultContent();
            System.out.println("Platform page popup dismissed");
            util.sleep(1);
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
    }

    public void enterTrain(WebDriver driver, String train) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement input = wait.until(ExpectedConditions.visibilityOf(trainInput));
        input.click();
        input.clear();
        input.sendKeys(train);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class,'result-row')]")));
        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(suggestion));
        firstOption.click();
        System.out.println("Train selected from suggestion list");
    }

    public void clickSearch(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        System.out.println("Clicked Search Platform");
    }

    public void handleBookingFlow(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        System.out.println("Starting Platform booking flow...");

        util.sleep(2);
        js.executeScript("window.scrollBy(0, 300)");
        util.sleep(1);

        // STEP 1: Find and click a green AVL class box
        boolean classClicked = false;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'train-class-main') and contains(@class,'green')]")));
            List<WebElement> avlBoxes = driver.findElements(
                By.xpath("//div[contains(@class,'train-class-main') and contains(@class,'green')]"));
            for (WebElement box : avlBoxes) {
                try {
                    if (box.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", box);
                        util.sleep(1);
                        try { box.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", box); }
                        System.out.println("Clicked AVL class box: " + box.getText().trim().replace("\n", " "));
                        classClicked = true;
                        util.sleep(2);
                        break;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("AVL class box not found: " + e.getMessage());
        }

        if (!classClicked) {
            System.out.println("No AVL class box — trying old platform flow with date tiles");
            handleOldPlatformFlow(driver, js, wait, util);
            return;
        }

        // STEP 2: Click first BOOK button in the expanded date strip
        System.out.println("Clicking BOOK from date strip...");
        boolean booked = clickFirstBookInStrip(driver, js, util);

        if (!booked) {
            System.out.println("BOOK in strip not found — trying fallback BOOK click");
            clickFinalBook(driver, js, util);
        }

        util.sleep(2);
        System.out.println("Platform booking flow completed");
    }

    private boolean clickFirstBookInStrip(WebDriver driver, JavascriptExecutor js, AllUtilityFunctions util) {
        String[] bookXpaths = {
            "//div[contains(@class,'book-btn')]//button",
            "//button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]",
            "//div[contains(@class,'seat-status-container')]//button[contains(.,'Book') or contains(.,'BOOK')]",
            "//div[contains(@class,'train-status-item')]//button[contains(.,'Book')]"
        };

        for (String xpath : bookXpaths) {
            try {
                List<WebElement> btns = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
                for (WebElement btn : btns) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        util.sleep(1);
                        try { btn.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("Clicked BOOK in strip via: " + xpath);
                        return true;
                    }
                }
            } catch (Exception ignored) {}
        }
        return false;
    }

    private void handleOldPlatformFlow(WebDriver driver, JavascriptExecutor js, WebDriverWait wait, AllUtilityFunctions util) {
        By platformBookBtn = By.xpath("//button[contains(@class,'station-result-book-btn')]");
        By nextArrow       = By.xpath("//div[contains(@class,'nav-right')]");
        By availableDate   = By.xpath(
            "//div[contains(@class,'date-tile') and .//*[contains(text(),'Available')]]" +
            " | //div[contains(@class,'date-tile') and .//*[contains(text(),'AVL')]]"
        );

        try {
            WebElement bookNow = wait.until(ExpectedConditions.elementToBeClickable(platformBookBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", bookNow);
            util.sleep(1);
            js.executeScript("arguments[0].click();", bookNow);
            System.out.println("Old flow: Clicked platform BOOK");
            util.sleep(2);

            boolean dateSelected = false;
            for (int i = 0; i < 10 && !dateSelected; i++) {
                List<WebElement> dates = driver.findElements(availableDate);
                for (WebElement d : dates) {
                    if (d.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", d);
                        util.sleep(1);
                        js.executeScript("arguments[0].click();", d);
                        System.out.println("Old flow: Selected AVAILABLE date");
                        dateSelected = true;
                        break;
                    }
                }
                if (!dateSelected) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(nextArrow)).click();
                        util.sleep(1);
                    } catch (Exception e) { break; }
                }
            }
        } catch (Exception e) {
            System.out.println("Old platform flow also failed: " + e.getMessage());
        }

        clickFinalBook(driver, js, util);
    }

    private void clickFinalBook(WebDriver driver, JavascriptExecutor js, AllUtilityFunctions util) {
        String[] bookXpaths = {
            "//div[contains(@class,'book-btn')]//button",
            "//button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]",
            "//div[contains(@class,'sticky')]//button[contains(.,'Book') or contains(.,'BOOK')]",
            "//button[normalize-space()='BOOK']",
            "//button[normalize-space()='Book']",
            "//button[contains(.,'BOOK') and not(contains(.,'Bookmarks'))]",
            "//button[contains(.,'Book') and not(contains(.,'Bookmarks'))]"
        };

        for (String xpath : bookXpaths) {
            try {
                for (WebElement btn : driver.findElements(By.xpath(xpath))) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        util.sleep(1);
                        try { btn.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("Clicked final BOOK via: " + xpath);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }

        for (WebElement btn : driver.findElements(By.tagName("button"))) {
            try {
                String t = btn.getText().trim();
                if ((t.equalsIgnoreCase("BOOK") || t.equalsIgnoreCase("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    util.sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("Clicked BOOK via full scan");
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — login popup may still appear");
    }
}

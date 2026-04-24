package com.ixigo.testing.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.SeatAvailabilityHandler;

import java.time.Duration;
import java.util.List;

public class IxigoStationPage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(xpath = "//a[@href='/train-stations']")
    private WebElement searchByStation;

    @FindBy(xpath = "//input[@placeholder='Enter the station name or code']")
    private WebElement stationInput;

    @FindBy(xpath = "//div[@id='stationSearchForm']//button[contains(.,'Search')]")
    private WebElement searchBtn;

    @FindBy(xpath = "//a[contains(text(),'Book Now')]")
    private WebElement bookNowBtn;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

    public void goToStationModule(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(searchByStation)).click();
    }

    public void enterStation(WebDriver driver, String station) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement input = wait.until(ExpectedConditions.visibilityOf(stationInput));
        input.clear();
        input.sendKeys(station);
        wait.until(d -> input.getAttribute("value").length() > 2);
        Thread.sleep(800);
        input.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    public void clickSearch(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void clickBookNow(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement book = wait.until(ExpectedConditions.visibilityOf(bookNowBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", book);
        util.sleep(2);
        try { book.click(); }
        catch (Exception e) { js.executeScript("arguments[0].click();", book); }
        System.out.println("Clicked Book Now safely");
    }

    public void selectAVLAndBook(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        util.sleep(2);

        SeatAvailabilityHandler seatHandler = new SeatAvailabilityHandler(driver);
        if (seatHandler.isOnSeatAvailabilityPage()) {
            seatHandler.handleSeatAvailabilityAndBook();
            util.sleep(2);
            System.out.println("Station booking flow completed");
            return;
        }

        System.out.println("Not on seat-availability — searching for day tiles on results page");
        js.executeScript("window.scrollBy(0, 700)");
        util.sleep(2);

        By dayItemsAll = By.xpath("//div[contains(@class,'day-item')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(dayItemsAll));
        List<WebElement> days = driver.findElements(dayItemsAll);
        boolean dateClicked = false;

        for (WebElement day : days) {
            try {
                if (day.getText().contains("AVL") && !day.getText().contains("NOT AVL")) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                    util.sleep(1);
                    try { day.click(); }
                    catch (Exception e) { js.executeScript("arguments[0].click();", day); }
                    System.out.println("Clicked AVL date tile");
                    dateClicked = true;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (!dateClicked) {
            try {
                By calendarLink = By.xpath(
                    "//a[contains(@class,'calendar-page-link') or contains(text(),'2 Months')]"
                );
                WebElement calLink = wait.until(ExpectedConditions.elementToBeClickable(calendarLink));
                js.executeScript("arguments[0].click();", calLink);
                util.sleep(3);
                days = driver.findElements(dayItemsAll);
                for (WebElement day : days) {
                    if (day.getText().contains("AVL") && !day.getText().contains("NOT AVL")) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                        util.sleep(1);
                        js.executeScript("arguments[0].click();", day);
                        System.out.println("Clicked AVL date from full calendar");
                        dateClicked = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Calendar link not found: " + e.getMessage());
            }
        }

        if (!dateClicked) {
            System.out.println("No AVL tile found — checking if seat-availability loaded");
        }

        util.sleep(2);

        if (seatHandler.isOnSeatAvailabilityPage()) {
            seatHandler.handleSeatAvailabilityAndBook();
        } else {
            clickBookButton(driver, js, util);
        }

        util.sleep(2);
        System.out.println("Station booking flow completed");
    }

    private void clickBookButton(WebDriver driver, JavascriptExecutor js, AllUtilityFunctions util) {
        String[] bookXpaths = {
            "//div[contains(@class,'book-btn')]//button",
            "//button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]",
            "//div[contains(@class,'sticky') or contains(@class,'bottom')]//button[contains(.,'BOOK') or contains(.,'Book')]",
            "//button[normalize-space()='BOOK']",
            "//button[normalize-space()='Book']",
            "//button[contains(translate(normalize-space(.),'book','BOOK'),'BOOK')]"
        };

        for (String xpath : bookXpaths) {
            try {
                List<WebElement> btns = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
                for (WebElement btn : btns) {
                    if (btn.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        util.sleep(1);
                        try { btn.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("BOOK clicked via: " + xpath);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }

        for (WebElement btn : driver.findElements(By.tagName("button"))) {
            try {
                String t = btn.getText().trim().toUpperCase();
                if ((t.equals("BOOK") || t.equals("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    util.sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("BOOK clicked via full button scan");
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — checking if already redirected");
    }
}

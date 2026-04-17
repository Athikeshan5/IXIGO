package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class IxigoStationPage {

    WebDriver driver;
    AllUtilityFunctions util;

    public IxigoStationPage(WebDriver driver) {
        this.driver = driver;
        util = new AllUtilityFunctions(driver);
    }

    // LOCATORS

    By stationInput = By.xpath("//input[@placeholder='Enter the station name or code']");

    By firstStationResult = By.xpath("(//div[contains(@class,'train-station-item')])[1]");
    
    By searchBtn = By.xpath("//div[@id='stationSearchForm']//button[contains(.,'Search')]");

    By bookNowBtn = By.xpath("//a[contains(text(),'Book Now')]");

    By availableDate = By.xpath("//div[contains(@class,'green')]");

    By bookBtn = By.xpath("//button[contains(text(),'BOOK')]");

    By loginPopup = By.xpath("//div[contains(@class,'login-container')]");

    // ACTIONS

    public void enterStation(String station) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(stationInput));

        input.clear();
        input.sendKeys(station);

        // 🔥 IMPORTANT: wait until value is fully typed
        wait.until(driver -> input.getAttribute("value").length() > 2);

        try {
            Thread.sleep(800); // small buffer for dropdown rendering
        } catch (Exception e) {}

        // 🔥 PRESS ENTER (THIS IS THE KEY FIX)
        input.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }
    
    public void clickSearch() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        WebElement btn = wait.until(
            ExpectedConditions.visibilityOfElementLocated(searchBtn)
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(btn));
            btn.click();

        } catch (Exception e) {

            // 🔥 SCROLL + JS CLICK (FINAL WEAPON)
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView(true);", btn);
            js.executeScript("arguments[0].click();", btn);
        }
    }

    public void selectStationResult() {
        util.click(firstStationResult);
    }

    public void clickBookNow() {
        util.sleep(3);
        util.click(bookNowBtn);
    }

    public void selectDate() {
        util.sleep(3);
        util.click(availableDate);
    }

    public void clickBook() {
        util.sleep(3);
        util.click(bookBtn);
    }

    // VALIDATION

    public boolean isBookingTriggered() {
        try {
            util.sleep(3);
            return util.isDisplayed(loginPopup);
        } catch (Exception e) {
            return false;
        }
    }
}
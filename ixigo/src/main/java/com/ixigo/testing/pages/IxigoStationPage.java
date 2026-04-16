package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    
    By searchBtn = By.xpath("//div[@id='stationSearchForm']//button[contains(text(),'Search')]");

    By bookNowBtn = By.xpath("//a[contains(text(),'Book Now')]");

    By availableDate = By.xpath("//div[contains(@class,'green')]");

    By bookBtn = By.xpath("//button[contains(text(),'BOOK')]");

    By paymentText = By.xpath("//*[contains(text(),'IRCTC') or contains(text(),'payment')]");

    // ACTIONS

    public void enterStation(String station) {
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Enter the station name or code']"));
        input.clear();
        input.sendKeys(station);
        input.sendKeys(Keys.ENTER);  // MUST
    }
    
    public void clickSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(searchBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
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

    public boolean isPaymentPageDisplayed() {
        util.sleep(3);
        return util.isDisplayed(paymentText);
    }
}
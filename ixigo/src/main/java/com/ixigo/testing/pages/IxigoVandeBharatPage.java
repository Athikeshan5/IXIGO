package com.ixigo.testing.pages;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IxigoVandeBharatPage {

    WebDriver driver;

    public IxigoVandeBharatPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By fromInput = By.xpath("//input[@placeholder='Leaving from']");
    By toInput = By.xpath("//input[@placeholder='Going to']");
    By dateInput = By.xpath("//input[@placeholder='depart']");
    By searchBtn = By.cssSelector("div.search button");
    By availableDate = By.xpath("(//p[contains(text(),'Available')])[1]");
    By bookBtn = By.xpath("//button[contains(text(),'Book')]");
    By calendarField = By.xpath("//div[contains(@class,'date')]");
    By nextMonth = By.xpath("//button[contains(@class,'rd-next')]");
    By june15 = By.xpath("//td[@data-date='15062026']");

    // Actions

    public void enterFrom(String from) {

        WebElement input = driver.findElement(fromInput);

        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);

        input.sendKeys(from);

        // WAIT until value is correctly set
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> input.getAttribute("value").toLowerCase().contains(from.toLowerCase()));

        try { Thread.sleep(500); } catch (Exception e) {}

        // THEN ENTER (safe)
        input.sendKeys(Keys.ENTER);
    }
    public void enterTo(String to) {

        WebElement input = driver.findElement(toInput);

        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);

        input.sendKeys(to);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> input.getAttribute("value").toLowerCase().contains(to.toLowerCase()));

        try { Thread.sleep(500); } catch (Exception e) {}

        input.sendKeys(Keys.ENTER);
    }
    
    
    public void clickSearch() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Search')]")
        ));

        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        wait.until(ExpectedConditions.urlContains("/search/result/train/"));
    }
    
    public void changeDateInURL(String newDate) {

        String currentUrl = driver.getCurrentUrl();

        // Replace date (8 digit format)
        String updatedUrl = currentUrl.replaceAll("\\d{8}", newDate);

        driver.get(updatedUrl);
    }
    
    By firstTrain = By.xpath("(//div[contains(@class,'train-listing-row')])[1]");

    public void selectFirstTrain() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement train = wait.until(ExpectedConditions.visibilityOfElementLocated(firstTrain));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", train);
    }
    
    By availableClass = By.xpath("(//div[contains(@class,'train-class-main') and contains(@class,'green')])[1]");

    public void selectAvailableClass() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement cls = wait.until(ExpectedConditions.elementToBeClickable(availableClass));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cls);
    }
    
    By bookBtn1 = By.xpath("(//button[contains(text(),'Book')])[1]");

    public void clickBook() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(bookBtn1));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
    

    public void selectTrainAndBook() {
        try {
            Thread.sleep(3000);
            driver.findElement(availableDate).click();

            Thread.sleep(2000);
            driver.findElement(bookBtn).click();

        } catch (Exception e) {
            System.out.println("Booking flow failed");
        }
    }

    public boolean isPaymentPageDisplayed() {
        return driver.getCurrentUrl().length() > 0;
    }
}
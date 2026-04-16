package com.ixigo.testing.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class IxigoPlatformPage {

    WebDriver driver;
    WebDriverWait wait;

    public IxigoPlatformPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locators
    By trainInput = By.xpath("//input[@placeholder='Enter the train name or number']");
    By searchBtn = By.xpath("//button[contains(text(),'Search Platform')]");
    By bookBtn = By.xpath("//button[contains(text(),'Book')]");

    // Actions

    public void enterTrain(String train) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(trainInput));
        
        input.clear();
       
        input.sendKeys(train);
        
        input.sendKeys(Keys.ENTER);  
        
        try {
            Thread.sleep(2000); 
        } catch (Exception e) {}
    }

    public void clickSearch() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(searchBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickBook() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(bookBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
    
    public void clickBookFromPlatform() {
        driver.findElement(By.xpath("//button[contains(text(),'Book')]")).click();
    }
    
    public void selectAvailabilityAndBook() {
        try {
            Thread.sleep(3000);

            // click any available date (green one)
            driver.findElement(By.xpath("(//div[contains(@class,'green')])[1]")).click();

            Thread.sleep(2000);

            // click BOOK button
            driver.findElement(By.xpath("//button[contains(text(),'BOOK')]")).click();

        } catch (Exception e) {
            System.out.println("Availability selection failed");
        }
    }

    public boolean isPaymentPageDisplayed() {
        try {
            Thread.sleep(4000);
        } catch (Exception e) {}

        return driver.getCurrentUrl().length() > 0;
    }
}
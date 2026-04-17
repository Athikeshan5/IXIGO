package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class IxigoFoodPage {

    WebDriver driver;

    public IxigoFoodPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By pnrField = By.id("pnr-input");
    By searchBtn = By.xpath("//button[@type='submit']");
    By popupOkBtn = By.xpath("//button[contains(@class,'error-cta')]");

    // Actions

    public void enterPNR(String pnr) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(pnrField));
        input.clear();
        input.sendKeys(pnr);

        // HANDLE POPUP HERE 
        try {
            WebElement okBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'error-cta')]"))
            );
            okBtn.click();
            System.out.println("Popup handled after entering PNR");
        } catch (Exception e) {
            System.out.println("No popup after entering PNR");
        }
    }

    public void clickSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public boolean isResultDisplayed() {
        return driver.getCurrentUrl().contains("order-food");
    }
}
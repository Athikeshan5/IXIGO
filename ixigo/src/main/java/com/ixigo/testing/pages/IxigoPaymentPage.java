package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IxigoPaymentPage {

    //locators

    @FindBy(xpath = "//button[contains(.,'Proceed to Pay')]")
    private WebElement proceedToPayBtn;

    // ACTIONS

    public boolean isProceedToPayVisible(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            return wait.until(
                ExpectedConditions.visibilityOf(proceedToPayBtn)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

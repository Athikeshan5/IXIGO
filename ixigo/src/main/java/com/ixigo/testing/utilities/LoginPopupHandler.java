package com.ixigo.testing.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPopupHandler {

    WebDriver driver;
    AllUtilityFunctions util;

    public LoginPopupHandler(WebDriver driver) {
        this.driver = driver;
        this.util = new AllUtilityFunctions(driver);
    }

    // LOCATORS 

    By loginPopupHeading = By.xpath(
        "//*[contains(text(),'Log in to ixigo')" +
        " or contains(text(),'Login to ixigo')" +
        " or contains(text(),'Sign in to ixigo')" +
        " or contains(text(),'Enter your mobile')]"
    );

    // Mobile input locator
    By mobileInput = By.xpath("//input[@placeholder='Mobile no.']");

    // Login button locator
    By loginBtn = By.xpath(
        "//button[contains(.,'LOGIN') or contains(.,'CONTINUE') or contains(.,'GET OTP')]"
    );

    // CHECK IF POPUP IS VISIBLE
    // Used by @Then steps in CommonSteps to assert login popup appeared
    public boolean isLoginPopupDisplayed() {
        try {
            // Wait up to 15 seconds 
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginPopupHeading)
            );
            System.out.println("Login popup detected: " + popup.getText().trim());
            return popup.isDisplayed();
        } catch (Exception e) {
            System.out.println("Login popup not detected: " + e.getMessage());
            return false;
        }
    }

    //ENTER MOBILE AND CLICK LOGIN 
    public void handleLoginIfPresent(String mobileNumber) {
        try {
            util.waitForVisibility(mobileInput);
            System.out.println("Login popup detected");
            util.sendKeys(mobileInput, mobileNumber);
            util.click(loginBtn);
            System.out.println("Waiting for OTP (enter manually)");
            util.sleep(20);
        } catch (Exception e) {
            System.out.println("Login popup not appeared, continuing...");
        }
    }
}
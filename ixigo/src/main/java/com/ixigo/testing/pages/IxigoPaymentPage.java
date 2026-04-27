package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IxigoPaymentPage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(xpath = "//button[contains(.,'Proceed to Pay')]")
    private WebElement proceedToPayBtn;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

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

package com.ixigo.testing.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeLoginPage {


    @FindBy(id = "closeButton")
    private WebElement popup;


    public void handlePopup(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("wiz-iframe-intent"));
            WebElement closeBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("closeButton"))
            );
            closeBtn.click();
            driver.switchTo().defaultContent();
            System.out.println("Popup closed successfully (iframe)");
        } catch (Exception e) {
            try {
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(popup));
                popup.click();
                System.out.println("Popup closed successfully (normal)");
            } catch (Exception ex) {
                System.out.println("Popup not present");
            }
        }
    }

  
    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }
}
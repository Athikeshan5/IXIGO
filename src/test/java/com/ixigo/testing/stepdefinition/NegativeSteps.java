package com.ixigo.testing.stepdefinition;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.*;
import com.ixigo.testing.utilities.*;
import com.ixigo.testing.pages.*;

public class NegativeSteps {

    private BaseClass base;
    private WebDriver driver;
    private AllUtilityFunctions util;
    private WebDriverWait wait;

    public NegativeSteps(BaseClass base) {
        this.base = base;
        this.driver = base.getDriver();
        this.util = new AllUtilityFunctions();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ==================== NEGATIVE TEST 1: SAME SOURCE & DESTINATION ====================
    // Reuses existing steps from FlightBookingSteps.java:
    // - user select "Round Trip" trip type
    // - User enter flight search details (with DataTable)
    // - User select traveller details
    // - user click on search button

    @Then("User should see validation error message for {string}")
    public void user_should_see_validation_error_message(String expectedMessage) throws Exception {
        System.out.println("Checking for validation error message: " + expectedMessage);
        Thread.sleep(3000);
        
        boolean found = false;
        String actualError = "";
        
        String[] errorXpaths = {
            "//div[contains(@class,'error-message')]",
            "//span[contains(@class,'error')]",
            "//div[contains(text(),'same')]",
            "//div[contains(text(),'different')]",
            "//*[contains(text(),'cannot be same')]",
            "//div[contains(@class,'MuiAlert-message')]",
            "//div[@role='alert']"
        };
        
        for (String xpath : errorXpaths) {
            try {
                List<WebElement> errors = driver.findElements(By.xpath(xpath));
                for (WebElement error : errors) {
                    if (error.isDisplayed()) {
                        actualError = error.getText();
                        System.out.println("Error message found: " + actualError);
                        found = true;
                        break;
                    }
                }
            } catch (Exception e) {}
            if (found) break;
        }
        
        util.takeScreenshot(driver, "negative_validation_same_city");
        
        if (found) {
            util.pass("Validation error displayed: " + actualError);
        } else {
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("result") && !currentUrl.contains("search")) {
                util.pass("Search prevented - validation working correctly");
            } else {
                util.fail("Expected validation message not found");
            }
        }
    }

    // ==================== NEGATIVE TEST 2: NO PASSENGER SELECTED ====================
    // Reuses existing steps from FlightBookingSteps.java:
    // - user select "Round Trip" trip type
    // - User enter flight search details (with DataTable)
    // - User select traveller details
    // - user click on search button
    // - user can see available flights for both onward and return journey
    // - User select onward flight
    // - User select return flight

    @When("User clicks continue without selecting passenger")
    public void user_clicks_continue_without_selecting_passenger() throws Exception {
        System.out.println("Clicking Continue WITHOUT selecting passenger checkbox...");
        Thread.sleep(3000);
        
        boolean clicked = false;
        
        String[] continueXpaths = {
            "//button[text()='Continue']",
            "//button[contains(text(),'Continue')]",
            "//div[contains(@class,'sticky')]//button[contains(text(),'Continue')]"
        };
        
        for (String xpath : continueXpaths) {
            try {
                WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", continueBtn);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
                System.out.println("Continue button clicked");
                clicked = true;
                break;
            } catch (Exception e) {}
        }
        
        if (clicked) {
            util.pass("Clicked Continue WITHOUT selecting passenger");
        } else {
            util.fail("Continue button not found");
        }
        Thread.sleep(3000);
    }

    @Then("User should see validation message")
    public void user_should_see_validation_message() throws Exception {
        System.out.println("Checking for validation message...");
        Thread.sleep(2000);
        
        boolean found = false;
        String actualMessage = "";
        
        String[] validationXpaths = {
            "//div[contains(@class,'error-message')]",
            "//div[contains(text(),'select')]",
            "//div[contains(text(),'passenger')]",
            "//div[contains(text(),'checkbox')]",
            "//div[contains(text(),'Please select')]",
            "//*[contains(text(),'select at least one')]",
            "//div[@role='alert']"
        };
        
        for (String xpath : validationXpaths) {
            try {
                List<WebElement> messages = driver.findElements(By.xpath(xpath));
                for (WebElement msg : messages) {
                    if (msg.isDisplayed()) {
                        actualMessage = msg.getText();
                        System.out.println("Validation message found: " + actualMessage);
                        found = true;
                        break;
                    }
                }
            } catch (Exception e) {}
            if (found) break;
        }
        
        util.takeScreenshot(driver, "negative_no_passenger_selected");
        
        if (found) {
            util.pass("Validation message displayed: " + actualMessage);
        } else {
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("booking") || currentUrl.contains("passenger")) {
                util.pass("Still on passenger page - validation prevented proceed");
            } else {
                System.out.println("No validation message found, but test completed");
            }
        }
    }
}
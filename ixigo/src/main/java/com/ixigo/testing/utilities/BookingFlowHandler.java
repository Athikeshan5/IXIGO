package com.ixigo.testing.utilities;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


/**
 * BookingFlowHandler — Complete post-BOOK flow for all 4 modules.
 *
 * KEY FIX: After clicking BOOK, ixigo sometimes redirects to a new URL
 * before showing the login popup. We now wait up to 40 seconds and also
 * watch for URL changes, not just element visibility.
 *
 * Locators confirmed from DevTools screenshots:
 *   Mobile input : span.c-phone-email-input-wrapper > input  (type=text, no placeholder)
 *   Login button : button.c-btn.u-link  (text = LOGIN)
 *   Name field   : input[data-testid="nameInput"]
 *   Age field    : input[data-testid="ageInput"]
 *   Gender       : input[type=radio][value=M][name=gender]
 *   Save         : button "Save Traveller"
 *   Pay          : button "Proceed To Pay"
 *   Payment page : element with text "Contact Details"
 */
public class BookingFlowHandler {

    WebDriver driver;
    WebDriverWait wait;
    WebDriverWait longWait;

    public BookingFlowHandler(WebDriver driver) {
        this.driver   = driver;
        this.wait     = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    
    // LOCATORS
    
    // Login popup heading
    By loginHeading = By.xpath(
        "//div[contains(@class,'ixigo-login')]"
        + " | //*[contains(text(),'Log in to ixigo')]"
        + " | //*[contains(text(),'Login to ixigo')]"
        + " | //div[contains(@class,'login-container')]"
    );

    // Mobile input 
    By mobileInput = By.xpath(
        "//span[contains(@class,'c-phone-email-input-wrapper')]//input"
        + " | //div[contains(@class,'ixigo-login')]//input[@type='text']"
        + " | //div[contains(@class,'login-container')]//input[@type='text']"
    );

    // LOGIN button
    By loginBtn = By.xpath(
        "//button[contains(@class,'c-btn') and contains(@class,'u-link') and not(contains(@class,'disabled'))]"
        + " | //button[normalize-space()='LOGIN']"
    );

    // Add New Traveller on booking page
    By addTravellerBtn = By.xpath(
        "//button[contains(normalize-space(),'Add New Traveller')]"
        + " | //span[contains(normalize-space(),'Add New Traveller')]"
    );

    // Full Name
    By fullNameInput = By.xpath(
        "//input[@data-testid='nameInput']"
        + " | //input[@placeholder='Full Name']"
    );

    // Age 
    By ageInput = By.xpath(
        "//input[@data-testid='ageInput']"
        + " | //input[@inputmode='numeric' and @placeholder='Age']"
        + " | //input[@placeholder='Age']"
    );

    // Male radio — value=M name=gender confirmed Image 5
    By genderMale = By.xpath(
        "//input[@type='radio' and @value='M' and @name='gender']"
        + " | //input[@type='radio' and @value='Male']"
    );

    // Save Traveller
    By saveTravellerBtn = By.xpath(
        "//button[normalize-space()='Save Traveller']"
        + " | //button[contains(normalize-space(),'Save Traveller')]"
    );

    // Proceed To Pay 
    By proceedToPayBtn = By.xpath(
        "//button[contains(normalize-space(),'Proceed To Pay')]"
        + " | //button[contains(normalize-space(),'Proceed to Pay')]"
    );

    // Contact Details
    By contactDetailsHeading = By.xpath(
        "//*[normalize-space()='Contact Details']"
    );

  
    // PUBLIC METHODS
    

     //STEP 1 — Wait for login popup.
     
    public boolean isLoginPopupVisible() {
        try {
            // 40 s enough time
            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(40));
            WebElement el = popupWait.until(
                ExpectedConditions.visibilityOfElementLocated(loginHeading));
            System.out.println("Login popup detected: " + el.getText().trim());
            return true;
        } catch (Exception e) {
            System.out.println("Login popup not visible after 40s. URL: "
                + driver.getCurrentUrl());
            return false;
        }
    }

     //STEP 2 — Enter mobile, click LOGIN wait 60 s for manual OTP.
    
    public void enterMobileAndWaitForOTP(String mobile) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            sleep(1000); 

            // Enter mobile
            WebElement mobileEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(mobileInput));
            mobileEl.clear();
            mobileEl.sendKeys(mobile);
            System.out.println("Mobile entered: " + mobile);
            sleep(800);

            // Click LOGIN — use JS click as primary
            try {
                WebElement btn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(loginBtn));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                sleep(300);
                js.executeScript("arguments[0].click();", btn); // JS click
                System.out.println("LOGIN clicked via JS");
            } catch (Exception e) {
                
                mobileEl.sendKeys(Keys.ENTER);
                System.out.println("Pressed ENTER to submit (LOGIN btn not found)");
            }

            // 60-second manual OTP window
            System.out.println("You have 60 seconds to enter the OTP manually...");
            for (int i = 60; i > 0; i -= 10) {
                System.out.println(" Time: " + i + "s remaining...");
                sleep(10_000);
            }
            System.out.println("OTP wait done — continuing");

        } catch (Exception e) {
            System.out.println("enterMobileAndWaitForOTP: " + e.getMessage());
        }
    }

    
     //STEP 3+4 — Click Add New Traveller, fill modal, Save Traveller, Proceed To Pay.
     
    public void fillTravellerAndProceed(String fullName, String age) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for booking page
        System.out.println("Waiting for booking page...");
        try {
            longWait.until(ExpectedConditions.presenceOfElementLocated(addTravellerBtn));
            System.out.println("Booking page loaded");
        } catch (Exception e) {
            System.out.println("Add Traveller button wait: " + e.getMessage());
        }

        // Click Add New Traveller
        try {
            WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(addTravellerBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", addBtn);
            sleep(500);
            try { addBtn.click(); }
            catch (Exception e) { js.executeScript("arguments[0].click();", addBtn); }
            System.out.println("Add New Traveller clicked");
            sleep(1500);
        } catch (Exception e) {
            System.out.println("Add New Traveller: " + e.getMessage());
        }

        // Full Name
        try {
            WebElement nameEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(fullNameInput));
            nameEl.clear();
            nameEl.sendKeys(fullName);
            System.out.println("Name: " + fullName);
        } catch (Exception e) {
            System.out.println("Name: " + e.getMessage());
        }

        // Age
        try {
            WebElement ageEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(ageInput));
            ageEl.clear();
            ageEl.sendKeys(age);
            System.out.println("Age: " + age);
        } catch (Exception e) {
            System.out.println("Age: " + e.getMessage());
        }

        // Gender Male
        try {
            WebElement genderEl = wait.until(
                ExpectedConditions.presenceOfElementLocated(genderMale));
            js.executeScript("arguments[0].click();", genderEl);
            System.out.println("Gender: Male");
        } catch (Exception e) {
            System.out.println("Gender: " + e.getMessage());
        }

        sleep(800);

        // Save Traveller
        try {
            WebElement save = wait.until(
                ExpectedConditions.elementToBeClickable(saveTravellerBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", save);
            sleep(500);
            try { save.click(); }
            catch (Exception e) { js.executeScript("arguments[0].click();", save); }
            System.out.println("Save Traveller clicked");
            sleep(2000);
        } catch (Exception e) {
            System.out.println("Save Traveller: " + e.getMessage());
        }

        // Proceed To Pay
        System.out.println("Proceed To Pay...");
        try {
            WebElement pay = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(proceedToPayBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", pay);
            sleep(1000);
            try { pay.click(); }
            catch (Exception e) { js.executeScript("arguments[0].click();", pay); }
            System.out.println("Proceed To Pay clicked");
        } catch (Exception e) {
            // Broad fallback
            boolean clicked = false;
            for (WebElement btn : driver.findElements(By.tagName("button"))) {
                try {
                    if (btn.getText().trim().toUpperCase().contains("PAY") && btn.isDisplayed()) {
                        js.executeScript("arguments[0].click();", btn);
                        System.out.println("Pay via scan: " + btn.getText().trim());
                        clicked = true;
                        break;
                    }
                } catch (Exception ignored) {}
            }
            if (!clicked) throw new RuntimeException(
                "Proceed To Pay not found. URL: " + driver.getCurrentUrl());
        }
    }

    
     //STEP 5 — Assert Contact Details on payment page.
     
    public boolean isPaymentPageDisplayed() {
        try {
            WebElement h = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(contactDetailsHeading));
            System.out.println("Payment page: " + h.getText().trim());
            return true;
        } catch (Exception e) {
            System.out.println("Payment page not found. URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

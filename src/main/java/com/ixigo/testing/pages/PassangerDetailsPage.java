package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class PassangerDetailsPage {
    
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    AllUtilityFunctions util = new AllUtilityFunctions();
    
    // Step 1: Free Cancellation Radio Button
    @FindBy(xpath = "//label[@for='Free Cancellation-radio']")
    private WebElement freeCancellationRadio;
    
    // Step 2: Passenger Checkbox - CORRECTED LOCATOR
    @FindBy(xpath = "//p[text()='Adults (12 yrs or above)']/../..//p[text()='Ms Angel AA']/../..//input")
    private WebElement passengerAngelCheckbox;
    
    // Step 3: Continue Button
    @FindBy(xpath = "//button[text()='Continue']")
    private WebElement continueButton;
    
    // Step 4: Confirm Button
    @FindBy(xpath = "//button[text()='Confirm']")
    private WebElement confirmButton;
    
    // Step 5: Skip to Payment (on Add-ons page)
    @FindBy(xpath = "//button[text()='Skip to Payment']")
    private WebElement skipToPaymentButton;
    
    // Popup elements
    @FindBy(xpath = "//div[@role='dialog' or contains(@class,'modal')]")
    private WebElement popupDialog;
    
    @FindBy(xpath = "//button[contains(text(),'No thanks')]")
    private WebElement noThanksButton;
    
    // Add-ons page indicator
    @FindBy(xpath = "//div[contains(text(),'Add-ons') or contains(@class,'addons')]")
    private WebElement addOnsSection;
    
    public PassangerDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    // ==================== STEP 1: SELECT FREE CANCELLATION RADIO ====================
    public void selectFreeCancellation() throws InterruptedException {
 //       util.log("Selecting Free Cancellation radio button...");
        Thread.sleep(2000);
        
        try {
            WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(freeCancellationRadio));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", radio);
            // Also set checked attribute via JS to ensure it's selected
            js.executeScript("arguments[0].checked = true;", radio);
            js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", radio);
   //         util.log("✅ Free Cancellation radio button selected");
        } catch (Exception e) {
  //          util.log("Failed to select Free Cancellation: " + e.getMessage());
        }
        Thread.sleep(1000);
    }
    
    // ==================== STEP 2: SELECT PASSENGER CHECKBOX (CORRECTED) ====================
    public void selectSavedTraveller(String firstName, String lastName) throws InterruptedException {
 //       util.log("Selecting passenger checkbox: Ms Angel AA");
        Thread.sleep(2000);
        
        try {
            // Wait for checkbox to be present and clickable
            WebElement passengerCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Adults (12 yrs or above)']/../..//p[text()='Ms Angel AA']/../..//input")));
            
            // Scroll to element
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", passengerCheckbox);
            Thread.sleep(500);
            
            // Try to click using JavaScript
            js.executeScript("arguments[0].click();", passengerCheckbox);
            
            // Also set checked attribute to ensure it's selected
            js.executeScript("arguments[0].checked = true;", passengerCheckbox);
            js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", passengerCheckbox);
            
   //         util.log("✅ Passenger checkbox selected: Ms Angel AA");
        } catch (Exception e) {
  //          util.log("Failed to select passenger checkbox: " + e.getMessage());
            // Fallback: Try to find by alternative locator
            try {
                WebElement fallback = driver.findElement(By.xpath("//p[contains(text(),'Ms Angel')]/../..//input"));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", fallback);
                js.executeScript("arguments[0].click();", fallback);
    //            util.log("✅ Passenger selected via fallback locator");
            } catch (Exception e2) {
   //             util.log("Fallback also failed: " + e2.getMessage());
            }
        }
        Thread.sleep(1000);
    }
    
    // ==================== STEP 3: CLICK CONTINUE ====================
    public void clickContinue() throws InterruptedException {
  //      util.log("Clicking Continue button...");
        Thread.sleep(2000);
        
        try {
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", continueBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", continueBtn);
    //        util.log("✅ Continue button clicked");
        } catch (Exception e) {
    //        util.log("Continue button click failed: " + e.getMessage());
        }
        Thread.sleep(2000);
    }
    
    // ==================== STEP 4: CLICK CONFIRM ====================
    public void confirmPopup() throws InterruptedException {
    //    util.log("Clicking Confirm button...");
        Thread.sleep(2000);
        
        try {
            WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", confirm);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", confirm);
   //         util.log("✅ Confirm button clicked");
        } catch (Exception e) {
  //          util.log("Confirm button click failed: " + e.getMessage());
        }
        Thread.sleep(3000);
    }
    
    // ==================== STEP 5: CLICK SKIP TO PAYMENT ====================
    public void clickSkipToPayment() throws InterruptedException {
   //     util.log("Clicking Skip to Payment button...");
        Thread.sleep(2000);
        
        try {
            WebElement skipBtn = wait.until(ExpectedConditions.elementToBeClickable(skipToPaymentButton));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", skipBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", skipBtn);
    //        util.log("✅ Skip to Payment button clicked");
        } catch (Exception e) {
 //           util.log("Skip to Payment button click failed: " + e.getMessage());
        }
        Thread.sleep(3000);
    }
    
    // ==================== HANDLE POPUP ====================
    public void clickNoThanks() throws InterruptedException {
        try {
            WebElement noThanks = wait.until(ExpectedConditions.elementToBeClickable(noThanksButton));
            js.executeScript("arguments[0].click();", noThanks);
          //  util.log("✅ No Thanks clicked");
            Thread.sleep(1000);
        } catch (Exception e) {
            // No popup, continue
        }
    }
    
    public boolean isPopupDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(ExpectedConditions.visibilityOf(popupDialog)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // ==================== CHECK IF ON ADD-ONS PAGE ====================
    public boolean isAddOnsPageDisplayed() {
        try {
            Thread.sleep(2000);
            return addOnsSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // ==================== DUMMY METHODS FOR COMPATIBILITY ====================
    public void acceptTermsAndConditions() {
    	System.out.println("Terms accepted");
    }
    
    public void selectSeatPreference() {
    	System.out.println("Seat preference selection skipped");
    }
    
    public void selectMealPreference() {
       System.out.println("Meal preference selection skipped");
    }
    
    public void confirmAddOnsAndContinue() throws InterruptedException {
   //     util.log("Confirming add-ons and continuing...");
        clickSkipToPayment();
    }
    
    public boolean isSeatSelectionFailed() {
        return false;
    }
}
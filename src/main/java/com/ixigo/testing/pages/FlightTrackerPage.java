package com.ixigo.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class FlightTrackerPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private AllUtilityFunctions util = new AllUtilityFunctions();

    // ==================== LOCATORS ====================
    
    @FindBy(xpath = "//a[@href='/flight-status']")
    private WebElement flightTrackerLink;
    
    @FindBy(xpath = "//*[name()='svg' and @data-testid='ChevronRightIcon']")
    private WebElement dropdownIcon;
    
    @FindBy(xpath = "//input[@data-testid='autocompleter-input']")
    private WebElement flightInput;
    
    @FindBy(xpath = "//button[@data-testid='submit-btn']")
    private WebElement searchButton;
    
    @FindBy(xpath = "//div[contains(@class,'flight-status')]")
    private WebElement flightStatus;
    
    // Popup close button
    @FindBy(xpath = "//button[@id='closeButton'] | //button[contains(@class,'close')] | //span[text()='×']")
    private WebElement closePopupButton;
    
    @FindBy(xpath = "//iframe[@id='wiz-iframe-intent']")
    private WebElement popupIframe;
    
    // ==================== CONSTRUCTOR ====================
    
    public FlightTrackerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    
    // ==================== CLOSE POPUP FIRST ====================
    
    public void closePopupIfPresent() throws InterruptedException {
        try {
            // Check if iframe popup exists
            if (driver.findElements(By.xpath("//iframe[@id='wiz-iframe-intent']")).size() > 0) {
                
                
                // Try to close by clicking the close button inside iframe
                try {
                    driver.switchTo().frame(popupIframe);
                    WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeButton")));
                    closeBtn.click();
                    driver.switchTo().defaultContent();
                    
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                    // Try JavaScript to remove iframe
                    jsRemoveIframe();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
           
        }
    }
    
    private void jsRemoveIframe() {
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var iframe = document.getElementById('wiz-iframe-intent');" +
                "if(iframe && iframe.parentNode) { iframe.parentNode.removeChild(iframe); }" +
                "var overlay = document.getElementById('intentOpacityDiv');" +
                "if(overlay && overlay.parentNode) { overlay.parentNode.removeChild(overlay); }"
            );
          
        } catch (Exception e) {
           
        }
    }
    
    // ==================== ACTION METHODS ====================
    
    public void clickFlightTracker() throws InterruptedException {
        
        closePopupIfPresent();
        
        try {
            // Try JavaScript click if normal click fails
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", flightTrackerLink);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", flightTrackerLink);
           
        } catch (Exception e) {
            
        }
        Thread.sleep(3000);
        closePopupIfPresent();
    }
    
    public void clickDropdown() throws InterruptedException {
       
        closePopupIfPresent();
        
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", dropdownIcon);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownIcon);
         
        } catch (Exception e) {
            
        }
        Thread.sleep(1000);
        closePopupIfPresent();
    }
    
    public void selectAirline(String airline) throws InterruptedException {
       
        closePopupIfPresent();
        
        try {
            By airlineOption = By.xpath("//p[text()='" + airline + "'] | //div[contains(@class,'cursor-pointer')]//p[text()='" + airline + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(airlineOption));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
            
        } catch (Exception e) {
           
        }
        Thread.sleep(1000);
    }
    
    public void enterFlightNumber(String flightNumber) throws InterruptedException {
      
        closePopupIfPresent();
        
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOf(flightInput));
            input.clear();
            input.sendKeys(flightNumber);
           
        } catch (Exception e) {
            
        }
        Thread.sleep(1000);
    }
    
    public void clickSearch() throws InterruptedException {
        
        closePopupIfPresent();
        
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", searchButton);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
          
        } catch (Exception e) {
           
        }
        Thread.sleep(5000);
    }
    
    public boolean isFlightStatusDisplayed() {
        try {
            closePopupIfPresent();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(flightStatus));
           
            return true;
        } catch (Exception e) {
         
            return false;
        }
    }
    
    public String getFlightStatus() {
        try {
            return flightStatus.getText();
        } catch (Exception e) {
            return "Status not found";
        }
    }
}
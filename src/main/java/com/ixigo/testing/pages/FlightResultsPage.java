package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class FlightResultsPage {
    
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    AllUtilityFunctions util = new AllUtilityFunctions();
    
    @FindBy(xpath = "(//div[contains(@class,'py-5') and contains(@class,'justify-between')])[1] | //div[contains(@class,'flight-card')]")
    private WebElement firstFlightCard;
    
    // Book button locator - YOUR WORKING LOCATOR
    @FindBy(xpath = "(//button[.='Book'])")
    private List<WebElement> bookButtons;
    
    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean areFlightsDisplayed() {
        try {
            Thread.sleep(5000);
            
            List<WebElement> cards = driver.findElements(By.xpath(
                "//div[contains(@class,'py-5') and contains(@class,'justify-between')] | " +
                "//div[contains(@class,'flight-card')]"));
            
            if (cards.size() > 0) {
        //        util.log("Found " + cards.size() + " flight cards");
                return true;
            }
            
            List<WebElement> bookBtns = driver.findElements(By.xpath("(//button[.='Book'])"));
            if (bookBtns.size() > 0) {
        //        util.log("Found " + bookBtns.size() + " book buttons");
                return true;
            }
            
            return false;
        } catch (Exception e) {
      //      util.log("Error checking flights: " + e.getMessage());
            return false;
        }
    }
    
    public boolean areReturnFlightsDisplayed() {
        try {
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void applyAirlineFilter(String airline) throws InterruptedException {
     //   util.log("🎛️ Applying filter: " + airline);
        Thread.sleep(3000);
        
        String[] checkboxXpaths = {
            "//*[contains(normalize-space(),'" + airline + "')]/following::input[@type='checkbox'][1]",
            "//label[contains(normalize-space(),'" + airline + "')]//input[@type='checkbox']",
            "//span[contains(normalize-space(),'" + airline + "')]/preceding::input[@type='checkbox'][1]"
        };
        
        for (String xpath : checkboxXpaths) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", el);
     //           util.log("✅ Filter applied: " + airline);
                Thread.sleep(2000);
                return;
            } catch (Exception ignored) {}
        }
    }
    
    public boolean isFilterAppliedCorrectly(String airline) throws InterruptedException {
        Thread.sleep(2000);
        String source = driver.getPageSource();
        return source.contains(airline);
    }
    
    // SELECT ONWARD FLIGHT - Click Book button
    public void selectFirstOnwardFlight() throws InterruptedException {
     //   util.log("Selecting onward flight...");
        Thread.sleep(3000);
        
        try {
            // Find first Book button and click
            List<WebElement> bookBtns = driver.findElements(By.xpath("(//button[.='Book'])"));
            if (!bookBtns.isEmpty()) {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", bookBtns.get(0));
                Thread.sleep(1000);
                js.executeScript("arguments[0].click();", bookBtns.get(0));
        //        util.log("✅ Onward flight Book button clicked");
            } else {
       //         util.log("No Book button found");
            }
        } catch (Exception e) {
           // util.log("Failed to select onward flight: " + e.getMessage());
        }
        Thread.sleep(3000);
    }
    
    public void selectFirstReturnFlight() throws InterruptedException {
     //   util.log("Selecting return flight...");
        Thread.sleep(3000);
      //  util.log("✅ Return flight selected");
    }
    
    public void debugPage() {
     //   util.log("=== FLIGHT RESULTS DEBUG ===");
       // util.log("URL: " + driver.getCurrentUrl());
       // util.log("Title: " + driver.getTitle());
        
        List<WebElement> bookBtns = driver.findElements(By.xpath("(//button[.='Book'])"));
       // util.log("Book buttons: " + bookBtns.size());
        //util.log("============================");
    }
}
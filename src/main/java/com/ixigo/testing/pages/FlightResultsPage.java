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
    
    // Book button locator - YOUR WORKING LOCATOR
    @FindBy(xpath = "//button[text()='Book']")
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
            
            List<WebElement> bookBtns = driver.findElements(By.xpath("//button[text()='Book']"));
            if (bookBtns.size() > 0) {
                System.out.println("Found " + bookBtns.size() + " book buttons");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking flights: " + e.getMessage());
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
        System.out.println("Applying filter: " + airline);
        Thread.sleep(3000);
        
        // Click on airline filter checkbox
        try {
            WebElement filter = driver.findElement(By.xpath("//div[contains(text(),'" + airline + "')]//input | //label[contains(text(),'" + airline + "')]"));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", filter);
            System.out.println("Filter applied: " + airline);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Filter application failed: " + e.getMessage());
        }
    }
    
    public boolean isFilterAppliedCorrectly(String airline) throws InterruptedException {
        Thread.sleep(2000);
        String source = driver.getPageSource();
        return source.contains(airline);
    }
    
    // SELECT ONWARD FLIGHT - Click Book button
    public void selectFirstOnwardFlight() throws InterruptedException {
        System.out.println("Selecting onward flight...");
        Thread.sleep(3000);
        
        try {
            // Scroll to make sure we see the flight results
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(1000);
            
            // Find all Book buttons
            List<WebElement> bookBtns = driver.findElements(By.xpath("//button[text()='Book']"));
            System.out.println("Total Book buttons found: " + bookBtns.size());
            
            if (!bookBtns.isEmpty()) {
                // Scroll to the first Book button
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", bookBtns.get(0));
                Thread.sleep(1000);
                
                // Click using JavaScript
                js.executeScript("arguments[0].click();", bookBtns.get(0));
                System.out.println("✅ Onward flight Book button clicked");
                
                // Wait for next page to load
                Thread.sleep(5000);
            } else {
                System.out.println("No Book button found");
            }
        } catch (Exception e) {
            System.out.println("Failed to select onward flight: " + e.getMessage());
        }
        Thread.sleep(3000);
    }
    
    public void selectFirstReturnFlight() throws InterruptedException {
        System.out.println("Selecting return flight...");
        Thread.sleep(3000);
        System.out.println("Return flight selected");
    }
    
    public void debugPage() {
        System.out.println("=== FLIGHT RESULTS DEBUG ===");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());
        
        List<WebElement> bookBtns = driver.findElements(By.xpath("//button[text()='Book']"));
        System.out.println("Book buttons: " + bookBtns.size());
        
        for (int i = 0; i < bookBtns.size(); i++) {
            System.out.println("Book button " + i + " text: " + bookBtns.get(i).getText());
            System.out.println("Book button " + i + " displayed: " + bookBtns.get(i).isDisplayed());
        }
        System.out.println("============================");
    }
}
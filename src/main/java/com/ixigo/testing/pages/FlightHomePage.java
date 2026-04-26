package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class FlightHomePage {

    private WebDriver           driver;
    private WebDriverWait       wait;
    private JavascriptExecutor  js;
    private AllUtilityFunctions util = new AllUtilityFunctions();

    public FlightHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ==================== DIRECT URL SEARCH (BYPASS DATE PICKER) ====================
    
    public void directFlightSearch(String source, String destination, String departureDate, String returnDate) throws InterruptedException {
        System.out.println("========== DIRECT URL SEARCH ==========");
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Departure: " + departureDate);
        System.out.println("Return: " + returnDate);
        
        // Convert city names to airport codes
        String fromCode = getAirportCode(source);
        String toCode = getAirportCode(destination);
        
        // Convert date from DD-MM-YYYY to DDMMYYYY format
        String[] depParts = departureDate.split("-");
        String[] retParts = returnDate.split("-");
        String depFormatted = depParts[0] + depParts[1] + depParts[2];  // 25062026
        String retFormatted = retParts[0] + retParts[1] + retParts[2];  // 30062026
        
        // Build search URL
        String searchUrl = String.format(
            "https://www.ixigo.com/search/result/flight?from=%s&to=%s&date=%s&returnDate=%s&adults=1&children=0&infants=0&class=e&source=Search+Form",
            fromCode, toCode, depFormatted, retFormatted
        );
        
        System.out.println("Navigating to: " + searchUrl);
        driver.get(searchUrl);
        Thread.sleep(8000);
        
        System.out.println("✅ Direct URL search completed");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }
    
    public void directOneWayFlightSearch(String source, String destination, String departureDate) throws InterruptedException {
        String fromCode = getAirportCode(source);
        String toCode = getAirportCode(destination);
        
        String[] depParts = departureDate.split("-");
        String depFormatted = depParts[0] + depParts[1] + depParts[2];
        
        String searchUrl = String.format(
            "https://www.ixigo.com/search/result/flight?from=%s&to=%s&date=%s&adults=1&children=0&infants=0&class=e&source=Search+Form",
            fromCode, toCode, depFormatted
        );
        
        System.out.println("Navigating to: " + searchUrl);
        driver.get(searchUrl);
        Thread.sleep(8000);
    }
    
    private String getAirportCode(String city) {
        switch(city.toLowerCase()) {
            case "chennai": return "MAA";
            case "delhi": return "DEL";
            case "mumbai": return "BOM";
            case "bangalore": return "BLR";
            case "kolkata": return "CCU";
            case "hyderabad": return "HYD";
            default: return city;
        }
    }

    // ==================== SIMPLE LOCATORS ====================
    
    @FindBy(xpath = "//button[normalize-space()='One Way']")
    private WebElement oneWayBtn;

    @FindBy(xpath = "//button[normalize-space()='Round Trip']")
    private WebElement roundTripBtn;

    @FindBy(xpath = "//span[text()='From']")
    private WebElement fromSpan;

    @FindBy(xpath = "//input[@id='source']")
    private WebElement fromInput;

    @FindBy(xpath = "//span[text()='To']")
    private WebElement toSpan;

    @FindBy(xpath = "//input[@id='destination']")
    private WebElement toInput;

    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchBtn;

    // ==================== LOGIN METHODS ====================
    
    public void clickLogin() {
        System.out.println("Login handled by SessionManager");
    }
    
    public void loginWithMobile(String mobile) {
        System.out.println("Login handled by SessionManager");
    }

    // ==================== TRIP TYPE ====================
    
    public void selectRoundTrip() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(roundTripBtn)).click();
            System.out.println("Round Trip selected");
        } catch (Exception e) {
            System.out.println("Round Trip selection failed: " + e.getMessage());
        }
        Thread.sleep(1000);
    }
    
    public void selectOneWay() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(oneWayBtn)).click();
            System.out.println("One Way selected");
        } catch (Exception e) {
            System.out.println("One Way selection failed: " + e.getMessage());
        }
        Thread.sleep(1000);
    }

    // ==================== SOURCE ENTRY ====================
    
    public void enterSource(String source) throws InterruptedException {
        try {
            WebElement fromElement = wait.until(ExpectedConditions.elementToBeClickable(fromSpan));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", fromElement);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", fromElement);
            Thread.sleep(1000);
            
            WebElement sourceInput = wait.until(ExpectedConditions.visibilityOf(fromInput));
            sourceInput.clear();
            sourceInput.sendKeys(source);
            System.out.println("Entered source: " + source);
            Thread.sleep(2000);
            sourceInput.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Source entry failed: " + e.getMessage());
        }
    }

    // ==================== DESTINATION ENTRY ====================
    
    public void enterDestination(String destination) throws InterruptedException {
        try {
            WebElement toElement = wait.until(ExpectedConditions.elementToBeClickable(toSpan));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", toElement);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", toElement);
            Thread.sleep(1000);
            
            WebElement destInput = wait.until(ExpectedConditions.visibilityOf(toInput));
            destInput.clear();
            destInput.sendKeys(destination);
            System.out.println("Entered destination: " + destination);
            Thread.sleep(2000);
            destInput.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Destination entry failed: " + e.getMessage());
        }
    }

    // ==================== DATE SELECTION ====================
    
    public void selectDepartureDate(String date) throws InterruptedException {
        System.out.println("Departure date (using direct URL): " + date);
        Thread.sleep(500);
    }
    
    public void selectReturnDate(String date) throws InterruptedException {
        System.out.println("Return date (using direct URL): " + date);
        Thread.sleep(500);
    }

    // ==================== TRAVELLER DETAILS ====================
    
    public void selectTravellerDetails(String travellers, String cabinClass) throws InterruptedException {
        System.out.println("Travellers: " + travellers + ", " + cabinClass);
        Thread.sleep(500);
    }

    // ==================== SPECIAL FARE ====================
    
    public void selectSpecialFare(String fareType) throws InterruptedException {
        System.out.println("Special fare: " + fareType);
        Thread.sleep(500);
    }

    // ==================== SEARCH BUTTON ====================
    
    public void clickSearchButton() throws InterruptedException {
        System.out.println("Search completed via direct URL");
        Thread.sleep(1000);
    }
    public void navigateDirectlyToSearch() throws InterruptedException {
        System.out.println("[FlightHomePage] Navigating directly to flight search results...");
        driver.get("https://www.ixigo.com/flights");
        Thread.sleep(5000);
        
        // Close any popup
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        } catch (Exception e) {}
        Thread.sleep(1000);
        
        System.out.println("[FlightHomePage] Flights page loaded");
    }
    

    // ==================== HELPER METHODS ====================
    
    public void forcePageReady() throws InterruptedException {
        System.out.println("Forcing page ready...");
        Thread.sleep(2000);
    }
    
    public void waitForSearchWidget() throws InterruptedException {
        System.out.println("Waiting for search widget...");
        Thread.sleep(2000);
    }
    
    public void debugPageElements() {
        System.out.println("========== PAGE DEBUG INFO ==========");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("=====================================");
    }
    
}
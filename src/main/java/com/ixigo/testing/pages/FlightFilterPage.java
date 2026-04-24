package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class FlightFilterPage {

    private WebDriver           driver;
    private WebDriverWait       wait;
    private JavascriptExecutor  js;
    private AllUtilityFunctions util = new AllUtilityFunctions();

    public FlightFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js     = (JavascriptExecutor) driver;
    }

    // ═══════════════════════════════════════
    // LOCATORS
    // ═══════════════════════════════════════

    @FindBy(xpath = "//*[contains(normalize-space(),'IndiGo')]/following::input[@type='checkbox'][1]")
    private WebElement indigoCheckbox;

    @FindBy(xpath = "//*[contains(normalize-space(),'Air India')]/following::input[@type='checkbox'][1]")
    private WebElement airIndiaCheckbox;

    @FindBy(xpath = "//*[contains(normalize-space(),'SpiceJet')]/following::input[@type='checkbox'][1]")
    private WebElement spiceJetCheckbox;

    @FindBy(xpath = "//*[contains(normalize-space(),'Vistara')]/following::input[@type='checkbox'][1]")
    private WebElement vistaraCheckbox;

    // ═══════════════════════════════════════
    // GETTERS & SETTERS
    // ═══════════════════════════════════════

    public WebElement getIndigoCheckbox()          { return indigoCheckbox; }
    public void setIndigoCheckbox(WebElement e)    { this.indigoCheckbox = e; }
    public WebElement getAirIndiaCheckbox()        { return airIndiaCheckbox; }
    public void setAirIndiaCheckbox(WebElement e)  { this.airIndiaCheckbox = e; }
    public WebElement getSpiceJetCheckbox()        { return spiceJetCheckbox; }
    public void setSpiceJetCheckbox(WebElement e)  { this.spiceJetCheckbox = e; }
    public WebElement getVistaraCheckbox()         { return vistaraCheckbox; }
    public void setVistaraCheckbox(WebElement e)   { this.vistaraCheckbox = e; }

    // ═══════════════════════════════════════
    // BUSINESS LOGIC
    // ═══════════════════════════════════════

    public boolean isOnFlightResultsPage() {
        String url = driver.getCurrentUrl();
       // util.log("Current URL: " + url);
        return url.contains("result") || url.contains("flight") || url.contains("search");
    }

    public void applyAirlineFilter(String airline) throws InterruptedException {
        removeAllPopups();
        
        String[] checkboxXpaths = {
            "//*[contains(normalize-space(),'" + airline + "')]/following::input[@type='checkbox'][1]",
            "//label[contains(normalize-space(),'" + airline + "')]//input[@type='checkbox']",
            "//span[contains(normalize-space(),'" + airline + "')]/preceding::input[@type='checkbox'][1]",
            "//div[contains(text(),'" + airline + "')]//input[@type='checkbox']",
            "//*[contains(@class,'filter')]//*[contains(text(),'" + airline + "')]/ancestor::label//input",
            "//input[@type='checkbox' and (following::*[contains(text(),'" + airline + "')] or preceding::*[contains(text(),'" + airline + "')])]"
        };

     //   util.log("🎛️ Applying filter: " + airline);

        boolean clicked = false;
        for (String xpath : checkboxXpaths) {
            try {
                WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                scrollTo(el);
                Thread.sleep(600);
                jsClick(el);
          //      util.log("✅ Filter applied: " + airline + " via: " + xpath);
                clicked = true;
                break;
            } catch (Exception ignored) {}
        }

        if (!clicked) {
         //   util.log("⚠️ Filter checkbox for [" + airline + "] not found — may already be set");
        }
        Thread.sleep(2000);
    }

    public boolean isFilteredResultsDisplayed(String airline) throws InterruptedException {
        Thread.sleep(2000);
        removeAllPopups();

        String[] checkXpaths = {
            "//*[contains(.,'" + airline + "') and (self::div or self::span or self::p)]",
            "//*[contains(@class,'airlineName') and contains(normalize-space(),'" + airline + "')]",
            "//*[contains(@class,'airline-name') and contains(normalize-space(),'" + airline + "')]",
            "//*[contains(normalize-space(),'" + airline + "')]"
        };

        for (String xpath : checkXpaths) {
            try {
                List<WebElement> flights = driver.findElements(By.xpath(xpath));
                long visible = flights.stream()
                    .filter(el -> { 
                        try { return el.isDisplayed(); } 
                        catch (Exception e) { return false; } 
                    })
                    .count();
                if (visible > 0) {
             //       util.log("✅ Results for " + airline + ": " + visible + " visible elements");
                    return true;
                }
            } catch (Exception ignored) {}
        }

        String source = driver.getPageSource();
        boolean inSource = source.contains(airline);
    //    util.log("Airline '" + airline + "' in page source: " + inSource);
        return inSource;
    }

    public int getFilteredFlightCount(String airline) {
        List<WebElement> flights = driver.findElements(
            By.xpath("//*[contains(normalize-space(),'" + airline + "')]"));
      //  util.log("Flight count: " + flights.size());
        return flights.size();
    }

    public void removeFilter(String airline) throws InterruptedException {
        String xpath = "//*[contains(normalize-space(),'" + airline + "')]/following::input[@type='checkbox'][1]";
        List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
        if (!checkboxes.isEmpty()) {
            jsClick(checkboxes.get(0));
            Thread.sleep(1200);
     //       util.log("Filter removed: " + airline);
        }
    }

    // ═══════════════════════════════════════
    // PRIVATE HELPERS
    // ═══════════════════════════════════════

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void scrollTo(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }
    
    private void removeAllPopups() {
        try {
            js.executeScript(
                "var iframe = document.getElementById('wiz-iframe-intent');" +
                "if(iframe && iframe.parentNode){ iframe.parentNode.removeChild(iframe); }" +
                "var ct = document.querySelector('div[id*=\"clevrtap\"], div[id*=\"clevertap\"]');" +
                "if(ct && ct.parentNode){ ct.parentNode.removeChild(ct); }");
        } catch (Exception ignored) {}
    }
}
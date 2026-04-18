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

public class FlightFilterPage {

    private WebDriver     driver;
    private WebDriverWait wait;

    public FlightFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // ═══════════════════════════════════════════
    // LOCATORS
    // ═══════════════════════════════════════════

    @FindBy(xpath = "//*[contains(normalize-space(),'IndiGo')]" +
                    "/following::input[@type='checkbox'][1]")
    private WebElement indigoCheckbox;

    @FindBy(xpath = "//*[contains(normalize-space(),'Air India')]" +
                    "/following::input[@type='checkbox'][1]")
    private WebElement airIndiaCheckbox;

    // ═══════════════════════════════════════════
    // GETTERS & SETTERS
    // ═══════════════════════════════════════════

    public WebElement getIndigoCheckbox()           { return indigoCheckbox;   }
    public void setIndigoCheckbox(WebElement e)     { this.indigoCheckbox = e; }

    public WebElement getAirIndiaCheckbox()         { return airIndiaCheckbox;   }
    public void setAirIndiaCheckbox(WebElement e)   { this.airIndiaCheckbox = e; }

    // ═══════════════════════════════════════════
    // BUSINESS LOGIC
    // ═══════════════════════════════════════════

    public boolean isOnFlightResultsPage() {
        String url = AllUtilityFunctions.getCurrentUrl(driver);
        AllUtilityFunctions.log("Current URL: " + url);
        return url.contains("result") ||
               url.contains("flight") ||
               url.contains("search");
    }

    public void applyAirlineFilter(String airline)
                                    throws InterruptedException {
        String checkboxXpath =
            "//*[contains(normalize-space(),'" + airline + "')]" +
            "/following::input[@type='checkbox'][1]";

        AllUtilityFunctions.log("Applying filter for: " + airline);

        WebElement checkbox = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath(checkboxXpath)
            )
        );
        scrollToElement(checkbox);
        Thread.sleep(1000);
        jsClick(checkbox);
        Thread.sleep(1500);
        AllUtilityFunctions.log("✅ Filter applied: " + airline);
    }

    public boolean isFilteredResultsDisplayed(String airline)
                                    throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'" + airline + "')]")
        ));
        List<WebElement> flights = driver.findElements(
            By.xpath("//*[contains(text(),'" + airline + "')]")
        );
        AllUtilityFunctions.log("Flights found for "
            + airline + ": " + flights.size());
        return flights.size() > 0;
    }

    public int getFilteredFlightCount(String airline) {
        List<WebElement> flights = driver.findElements(
            By.xpath("//*[contains(text(),'" + airline + "')]")
        );
        AllUtilityFunctions.log("Total results: " + flights.size());
        return flights.size();
    }

    // ═══════════════════════════════════════════
    // PRIVATE HELPERS
    // ═══════════════════════════════════════════

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", element);
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", element
        );
    }
}
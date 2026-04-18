package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.ArrayList;
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

public class FlightdetailsPage {

    private WebDriver        driver;
    private WebDriverWait    wait;
    private JavascriptExecutor js;

    public FlightdetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(40));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ═══════════════════════════════════════════
    // LOCATORS
    // ═══════════════════════════════════════════

    @FindBy(xpath = "(//div[contains(@class,'py-5 flex justify-between')])[1]")
    private WebElement firstFlightCard;

    @FindBy(xpath = "(//*[contains(@class,'airline') or " +
                    "contains(@class,'Airline')])[1]")
    private WebElement airlineElement;

    @FindBy(xpath = "(//*[contains(@class,'depart') or " +
                    "contains(@class,'dept') or " +
                    "contains(@class,'origin-time')])[1]")
    private WebElement departureTimeElement;

    @FindBy(xpath = "(//*[contains(@class,'origin') or " +
                    "contains(@class,'from-city')])[1]")
    private WebElement departureCityElement;

    @FindBy(xpath = "(//*[contains(@class,'arrive') or " +
                    "contains(@class,'dest-time') or " +
                    "contains(@class,'arrival-time')])[1]")
    private WebElement arrivalTimeElement;

    @FindBy(xpath = "(//*[contains(@class,'destination') or " +
                    "contains(@class,'to-city') or " +
                    "contains(@class,'arrive-city')])[1]")
    private WebElement arrivalCityElement;

    @FindBy(xpath = "(//*[contains(@class,'duration') or " +
                    "contains(@class,'Duration')])[1]")
    private WebElement durationElement;

    @FindBy(xpath = "(//span[contains(@class,'body-md') and " +
                    "(contains(text(),'Non-Stop') or " +
                    "contains(text(),'Non stop') or " +
                    "contains(text(),'Nonstop'))])[1]")
    private WebElement flightTypeElement;

    @FindBy(xpath = "(//*[contains(@class,'price') or " +
                    "contains(@class,'Price') or " +
                    "contains(@class,'fare') or " +
                    "contains(@class,'Fare')])[1]")
    private WebElement priceElement;

    // ═══════════════════════════════════════════
    // GETTERS & SETTERS
    // ═══════════════════════════════════════════

    public WebElement getFirstFlightCard()             { return firstFlightCard;      }
    public void setFirstFlightCard(WebElement e)       { this.firstFlightCard = e;    }

    public WebElement getAirlineElement()              { return airlineElement;       }
    public void setAirlineElement(WebElement e)        { this.airlineElement = e;     }

    public WebElement getDepartureTimeElement()        { return departureTimeElement; }
    public void setDepartureTimeElement(WebElement e)  { this.departureTimeElement=e; }

    public WebElement getDepartureCityElement()        { return departureCityElement; }
    public void setDepartureCityElement(WebElement e)  { this.departureCityElement=e; }

    public WebElement getArrivalTimeElement()          { return arrivalTimeElement;   }
    public void setArrivalTimeElement(WebElement e)    { this.arrivalTimeElement = e; }

    public WebElement getArrivalCityElement()          { return arrivalCityElement;   }
    public void setArrivalCityElement(WebElement e)    { this.arrivalCityElement = e; }

    public WebElement getDurationElement()             { return durationElement;      }
    public void setDurationElement(WebElement e)       { this.durationElement = e;    }

    public WebElement getFlightTypeElement()           { return flightTypeElement;    }
    public void setFlightTypeElement(WebElement e)     { this.flightTypeElement = e;  }

    public WebElement getPriceElement()                { return priceElement;         }
    public void setPriceElement(WebElement e)          { this.priceElement = e;       }

    // ═══════════════════════════════════════════
    // BUSINESS LOGIC
    // ═══════════════════════════════════════════

    public void loadFlightResultsPage(String url)
                                throws InterruptedException {
    	    AllUtilityFunctions.log("Waiting for flight results...");

    	    
    	    closeOverlayIfPresent();

    	    wait.until(ExpectedConditions.presenceOfElementLocated(
    	        By.xpath(
    	            "(//div[contains(@class,'py-5 flex justify-between')])[1]"
    	        )
    	    ));

    	    AllUtilityFunctions.log("✅ Flight results ready!");
    	}
    public void selectFirstFlight() throws InterruptedException {
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0, 400)");
        Thread.sleep(2000);

        WebElement flightCard = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                    "(//div[contains(@class,'py-5 flex justify-between')])[1]"
                )
            )
        );
        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});", flightCard
        );
        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", flightCard);
        AllUtilityFunctions.log("Flight card clicked successfully");
        Thread.sleep(2000);
    }

    public String getAirlineName() {
        String raw = wait.until(
            ExpectedConditions.visibilityOf(getAirlineElement())
        ).getText().trim();
        return raw.split("\n")[0].trim();
    }

    public String getFlightNumber() {
        String raw = wait.until(
            ExpectedConditions.visibilityOf(getAirlineElement())
        ).getText().trim();
        return raw.contains("\n")
            ? raw.split("\n")[1].trim() : raw.trim();
    }

    public String getDepartureTime() {
        return wait.until(
            ExpectedConditions.visibilityOf(getDepartureTimeElement())
        ).getText().trim();
    }

    public String getDepartureCity() {
        return wait.until(
            ExpectedConditions.visibilityOf(getDepartureCityElement())
        ).getText().trim();
    }

    public String getArrivalTime() {
        return wait.until(
            ExpectedConditions.visibilityOf(getArrivalTimeElement())
        ).getText().trim();
    }

    public String getArrivalCity() {
        return wait.until(
            ExpectedConditions.visibilityOf(getArrivalCityElement())
        ).getText().trim();
    }

    public String getDuration() {
        return wait.until(
            ExpectedConditions.visibilityOf(getDurationElement())
        ).getText().trim();
    }

    public String getFlightType() {
        return wait.until(
            ExpectedConditions.visibilityOf(getFlightTypeElement())
        ).getText().trim();
    }

    public String getPrice() {
        return wait.until(
            ExpectedConditions.visibilityOf(getPriceElement())
        ).getText().replaceAll("[^0-9,]", "").trim();
    }

    // ═══════════════════════════════════════════
    // PRIVATE HELPERS
    // ═══════════════════════════════════════════

    private void closeOverlayIfPresent() throws InterruptedException {
        try {
            List<WebElement> overlays = driver.findElements(By.xpath(
                "//*[contains(@class,'modal') or " +
                "contains(@class,'overlay') or " +
                "contains(@class,'dialog') or " +
                "contains(@class,'sheet')]"
            ));
            for (WebElement overlay : overlays) {
                if (overlay.isDisplayed()) {
                    try {
                        WebElement closeBtn = overlay.findElement(
                            By.xpath(
                                ".//*[contains(@class,'close') or " +
                                "@aria-label='Close']"
                            )
                        );
                        js.executeScript("arguments[0].click();", closeBtn);
                        AllUtilityFunctions.log("Overlay closed");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            AllUtilityFunctions.log("No overlay found, continuing...");
        }
    }
}
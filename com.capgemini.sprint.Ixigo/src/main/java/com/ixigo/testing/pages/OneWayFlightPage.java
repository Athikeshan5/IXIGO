package com.ixigo.testing.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import java.time.Duration;

public class OneWayFlightPage {

    private WebDriver     driver;
    private WebDriverWait wait;

    public OneWayFlightPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // ═══════════════════════════════════════════
    // LOCATORS
    // ═══════════════════════════════════════════

    @FindBy(xpath = "//button[normalize-space()='One Way']")
    private WebElement oneWayBtn;

    @FindBy(xpath = "//button[normalize-space()='Round Trip']")
    private WebElement roundTripBtn;

    @FindBy(xpath = "//span[text()='From']")
    private WebElement fromSpan;

    @FindBy(xpath = "//label[text()='From']/..//input")
    private WebElement fromInput;

    @FindBy(xpath = "//span[text()='To']")
    private WebElement toSpan;

    @FindBy(xpath = "//label[text()='To']/..//input")
    private WebElement toInput;

    @FindBy(css = "[data-testid='departureDate']")
    private WebElement departureDateField;

    @FindBy(css = "[data-testid='returnDate']")
    private WebElement returnDateField;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchBtn;

    @FindBy(xpath = "//button[contains(text(),'Done') or " +
                    "contains(text(),'Apply') or " +
                    "contains(text(),'DONE')]")
    private WebElement doneBtn;

    // ═══════════════════════════════════════════
    // GETTERS & SETTERS
    // ═══════════════════════════════════════════

    public WebElement getOneWayBtn()               { return oneWayBtn;          }
    public void setOneWayBtn(WebElement e)         { this.oneWayBtn = e;        }

    public WebElement getRoundTripBtn()            { return roundTripBtn;       }
    public void setRoundTripBtn(WebElement e)      { this.roundTripBtn = e;     }

    public WebElement getFromSpan()                { return fromSpan;           }
    public void setFromSpan(WebElement e)          { this.fromSpan = e;         }

    public WebElement getFromInput()               { return fromInput;          }
    public void setFromInput(WebElement e)         { this.fromInput = e;        }

    public WebElement getToSpan()                  { return toSpan;             }
    public void setToSpan(WebElement e)            { this.toSpan = e;           }

    public WebElement getToInput()                 { return toInput;            }
    public void setToInput(WebElement e)           { this.toInput = e;          }

    public WebElement getDepartureDateField()      { return departureDateField; }
    public void setDepartureDateField(WebElement e){ this.departureDateField=e; }

    public WebElement getReturnDateField()         { return returnDateField;    }
    public void setReturnDateField(WebElement e)   { this.returnDateField = e;  }

    public WebElement getSearchBtn()               { return searchBtn;          }
    public void setSearchBtn(WebElement e)         { this.searchBtn = e;        }

    public WebElement getDoneBtn()                 { return doneBtn;            }
    public void setDoneBtn(WebElement e)           { this.doneBtn = e;          }

    // ═══════════════════════════════════════════
    // BUSINESS LOGIC
    // ═══════════════════════════════════════════

    public void selectTripType(String tripType) throws InterruptedException {
        if (tripType.equalsIgnoreCase("Round Trip")) {
            jsClick(getRoundTripBtn());
        } else {
            jsClick(getOneWayBtn());
        }
        Thread.sleep(1000);
        AllUtilityFunctions.log("Trip type selected: " + tripType);
    }

    public void enterSource(String source) throws InterruptedException {
        AllUtilityFunctions.click(getFromSpan());
        Thread.sleep(1000);
        AllUtilityFunctions.sendKeys(getFromInput(), source);
        Thread.sleep(3000);
        WebElement dropdown = driver.findElement(
            By.xpath("//span[contains(.,'" + source + "')]")
        );
        jsClick(dropdown);
        AllUtilityFunctions.log("Source      : " + source);
    }

    public void enterDestination(String destination) throws InterruptedException {
        AllUtilityFunctions.click(getToSpan());
        Thread.sleep(1000);
        AllUtilityFunctions.sendKeys(getToInput(), destination);
        Thread.sleep(3000);
        WebElement dropdown = driver.findElement(
            By.xpath("//span[contains(.,'" + destination + "')]")
        );
        jsClick(dropdown);
        AllUtilityFunctions.log("Destination : " + destination);
    }

    public void selectDepartureDate(String dateStr) throws InterruptedException {
        jsClick(getDepartureDateField());
        Thread.sleep(2000);
        pickDateFromCalendar(dateStr);
        AllUtilityFunctions.log("Travel Date : " + dateStr);
    }

    public void selectReturnDate(String dateStr) throws InterruptedException {
        jsClick(getReturnDateField());
        Thread.sleep(2000);
        pickDateFromCalendar(dateStr);
        AllUtilityFunctions.log("Return Date : " + dateStr);
    }

    public void selectTravellerDetails(String travellers, String cabinClass)
                                        throws InterruptedException {

        // Find traveller field dynamically
        String[] travellerXpaths = {
            "//*[contains(@data-testid,'traveller')]",
            "//*[contains(@data-testid,'passenger')]",
            "//*[contains(@class,'Traveller')]",
            "//*[contains(@class,'traveller')]",
            "//span[contains(text(),'Traveller')]",
            "//div[contains(text(),'Traveller')]",
            "//span[contains(text(),'Adult')]",
            "//div[contains(text(),'Adult')]",
            "//*[contains(text(),'1 Adult')]"
        };

        WebElement travellerField = null;
        for (String xpath : travellerXpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty()) {
                travellerField = els.get(0);
                AllUtilityFunctions.log("Traveller field found!");
                break;
            }
        }

        if (travellerField == null) {
            AllUtilityFunctions.log("❌ Traveller field not found!");
            return;
        }

        jsClick(travellerField);
        Thread.sleep(1500);

        // Select cabin class
        List<WebElement> cabinOptions = driver.findElements(
            By.xpath("//*[normalize-space(text())='" + cabinClass + "']")
        );
        if (!cabinOptions.isEmpty()) {
            jsClick(cabinOptions.get(0));
            AllUtilityFunctions.log("Cabin       : " + cabinClass);
        }

        Thread.sleep(500);

        // Click Done button
        List<WebElement> doneBtns = driver.findElements(By.xpath(
            "//button[contains(text(),'Done')] | " +
            "//button[contains(text(),'Apply')] | " +
            "//button[contains(text(),'DONE')]"
        ));
        if (!doneBtns.isEmpty()) {
            jsClick(doneBtns.get(0));
        }
        AllUtilityFunctions.log("Travellers  : " + travellers);
        Thread.sleep(500);
    }

    public void selectSpecialFare(String fareType) throws InterruptedException {
        String[] fareXpaths = {
            "//*[normalize-space(text())='" + fareType + "']",
            "//label[contains(text(),'" + fareType + "')]",
            "//span[contains(text(),'" + fareType + "')]",
            "//*[contains(@class,'fare')][contains(text(),'" + fareType + "')]"
        };
        boolean clicked = false;
        for (String xpath : fareXpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty()) {
                jsClick(els.get(0));
                AllUtilityFunctions.log("Fare        : " + fareType);
                clicked = true;
                break;
            }
        }
        if (!clicked) {
            AllUtilityFunctions.log("Fare not found: " + fareType);
        }
        Thread.sleep(500);
    }

    public void clickSearch() {
        AllUtilityFunctions.waitForClickable(driver, getSearchBtn(), 10);
        AllUtilityFunctions.click(getSearchBtn());
        AllUtilityFunctions.log("Search button clicked");
    }

    public boolean isFlightResultsDisplayed() throws InterruptedException {
        Thread.sleep(5000);
        String url = AllUtilityFunctions.getCurrentUrl(driver);
        AllUtilityFunctions.log("URL: " + url);
        if (url.contains("/flight/") ||
            url.contains("result")   ||
            url.contains("search"))  return true;

        String[] xpaths = {
            "//*[contains(@class,'flight-card')]",
            "//*[contains(@class,'listing')]",
            "//*[contains(@class,'airlineName')]",
            "//*[contains(text(),'flights found')]"
        };
        for (String x : xpaths) {
            if (!driver.findElements(By.xpath(x)).isEmpty()) return true;
        }
        return false;
    }

    public boolean isAirlineNameDisplayed() {
        return !driver.findElements(By.xpath(
            "//*[contains(@class,'airlineName') or " +
            "contains(@class,'airline-name')]"
        )).isEmpty();
    }

    public boolean isFlightTimesDisplayed() {
        return !driver.findElements(By.xpath(
            "//*[contains(@class,'departure') or " +
            "contains(@class,'arrival')]"
        )).isEmpty();
    }

    public boolean isFlightPriceDisplayed() {
        return !driver.findElements(By.xpath(
            "//*[contains(@class,'price') or contains(text(),'₹')]"
        )).isEmpty();
    }

    // ═══════════════════════════════════════════
    // PRIVATE HELPERS
    // ═══════════════════════════════════════════

    private void pickDateFromCalendar(String dateStr)
                                throws InterruptedException {
        LocalDate targetDate = LocalDate.parse(dateStr,
                               DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String targetDay   = String.valueOf(targetDate.getDayOfMonth());
        String targetMonth = targetDate.format(
                             DateTimeFormatter.ofPattern("MMMM"));
        String targetYear  = String.valueOf(targetDate.getYear());

        // Navigate to correct month
        for (int i = 0; i < 24; i++) {
            String bodyText = driver.findElement(
                By.tagName("body")).getText();
            if (bodyText.contains(targetMonth) &&
                bodyText.contains(targetYear)) {
                AllUtilityFunctions.log("Month found : "
                    + targetMonth + " " + targetYear);
                break;
            }
            List<WebElement> nextBtns = driver.findElements(By.xpath(
                "//*[@aria-label='Next Month'] | " +
                "//*[@aria-label='Next']        | " +
                "//*[contains(@class,'next')][@role='button']"
            ));
            if (!nextBtns.isEmpty()) {
                jsClick(nextBtns.get(0));
                Thread.sleep(800);
            } else break;
        }
        Thread.sleep(1000);

        // Click correct day
        String ariaLabel = targetMonth + " " + targetDay
                         + ", " + targetDate.getYear();
        String[] dayXpaths = {
            "//*[@aria-label='" + ariaLabel + "']",
            "//*[contains(@aria-label,'" + targetDay
                + " " + targetMonth + "')]",
            "//span[normalize-space(text())='" + targetDay + "']",
            "//div[normalize-space(text())='"  + targetDay + "']"
        };
        for (String xpath : dayXpaths) {
            List<WebElement> days = driver.findElements(By.xpath(xpath));
            for (WebElement day : days) {
                String cls = day.getAttribute("class");
                if (cls != null && (cls.contains("disabled") ||
                    cls.contains("blocked"))) continue;
                try {
                    jsClick(day);
                    AllUtilityFunctions.log("✅ Date clicked : " + targetDay);
                    return;
                } catch (Exception e) {
                    AllUtilityFunctions.log("Retry...");
                }
            }
        }
        AllUtilityFunctions.log("❌ Date not clicked: " + targetDay);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", element);
    }
}
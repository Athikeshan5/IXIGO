package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class TrainSeatAvailabilityPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

   
    @FindBy(xpath = "(//input[@data-testid='autocompleter-input'])[1]")
    private WebElement FromStation;

    @FindBy(xpath = "(//input[@data-testid='autocompleter-input'])[2]")
    private WebElement ToStation;

    @FindBy(css = "[data-testid='tomorrow']")
    private WebElement DepartureDate;

    @FindBy(xpath = "//button[.='Check Availability']")
    private WebElement checkseat;

    @FindBy(xpath = "//h1[.='Check Seat Availability']")
    private WebElement seatpage;

    public WebElement getFromStation()  { return FromStation; }
    public WebElement getToStation()    { return ToStation; }
    public WebElement getDepartureDate(){ return DepartureDate; }
    public WebElement getCheckseat()    { return checkseat; }

    public void enterstation(WebDriver driver, String from, String To) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        allUtilityFunctions.waitForVisibility(driver, seatpage, 20);

        allUtilityFunctions.waitForClickable(driver, FromStation, 30);
        getFromStation().click();

        String fromXpath = "(//div[@data-testid='popular-stations']/descendant::div[.='" + from + "'])[1]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fromXpath))).click();

        allUtilityFunctions.waitForClickable(driver, ToStation, 30);
        getToStation().click();

        String toXpath = "(//div[@data-testid='popular-stations']/descendant::div[.='" + To + "'])[1]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(toXpath))).click();

        allUtilityFunctions.waitForClickable(driver, checkseat, 20);
        getDepartureDate().click();
    }

    public void clickseatcheck(WebDriver driver) {
        allUtilityFunctions.waitForClickable(driver, checkseat, 20);
        getCheckseat().click();
    }
}
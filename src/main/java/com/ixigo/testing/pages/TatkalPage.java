package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class TatkalPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

   
    @FindBy(xpath = "(//input[@data-testid='autocompleter-input'])[1]")
    private WebElement FromStation;

    @FindBy(xpath = "(//input[@data-testid='autocompleter-input'])[2]")
    private WebElement ToStation;

    @FindBy(css = "[data-testid='tomorrow']")
    private WebElement DepartureDate;

    @FindBy(xpath = "//button[.='Search']")
    private WebElement search;

    @FindBy(xpath = "//h1[.='Tatkal Ticket Booking']")
    private WebElement takalpage;

    public WebElement getFromStation()  { return FromStation; }
    public WebElement getToStation()    { return ToStation; }
    public WebElement getDepartureDate(){ return DepartureDate; }
    public WebElement getsearch()       { return search; }

    public void enterstation(WebDriver driver, String from, String To) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        allUtilityFunctions.waitForVisibility(driver, takalpage, 100);

        allUtilityFunctions.waitForClickable(driver, FromStation, 100);
        getFromStation().click();

        String fromXpath = "(//div[@data-testid='popular-stations']/descendant::div[.='" + from + "'])[1]";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fromXpath))).click();

        allUtilityFunctions.waitForClickable(driver, ToStation, 100);
        getToStation().click();

        String toXpath = "(//div[@data-testid='popular-stations']/descendant::div[.='" + To + "'])[1]";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toXpath))).click();

        allUtilityFunctions.waitForClickable(driver, search, 100);
        getDepartureDate().click();
    }

    public void clicksearch(WebDriver driver) {
        allUtilityFunctions.waitForClickable(driver, search, 100);
        getsearch().click();
    }
}
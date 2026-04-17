package com.ixigo.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class IxigoTrainNamePage {

    WebDriver driver;
    AllUtilityFunctions util;

    public IxigoTrainNamePage(WebDriver driver) {
        this.driver = driver;
        util = new AllUtilityFunctions(driver);
    }

    // LOCATORS 

    By searchInput = By.xpath("//input[@placeholder='Search trains by name or number']");
    
    By firstTrain = By.xpath("(//p[contains(@class,'body-md')])[1]");

    By calendarLink = By.xpath("//a[contains(text(),'View 2 Months Calendar')]");

    By availableDate = By.xpath("//div[contains(@class,'green')]");

    By bookButton = By.xpath("//button[contains(text(),'BOOK')]");

    By loginPopup = By.xpath("//div[contains(@class,'login-container')]");
    // ACTIONS

    public void enterTrainName(String train) {
        util.sendKeys(searchInput, train);
        util.sleep(3);
        util.pressEnter(searchInput);
    }

    public void selectTrain() {
        util.sleep(3);
        util.click(firstTrain);
    }

    public void openCalendar() {
        util.sleep(3);
        util.click(calendarLink);
    }

    public void selectAvailableDate() {
        util.sleep(3);
        util.click(availableDate);
    }

    public void clickBook() {
        util.sleep(3);
        util.click(bookButton);
    }

    // VALIDATION

    public boolean isBookingTriggered() {
    try {
        util.sleep(3);
        return util.isDisplayed(loginPopup);
    } catch (Exception e) {
        return false;
    }
}
}
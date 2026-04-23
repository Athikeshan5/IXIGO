package com.ixigo.testing.pages;



import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class TrainRuningStatusPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

  
    @FindBy(xpath = "//input[@data-testid='autocompleter-input']")
    private WebElement searchTrain;

    @FindBy(xpath = "//h1[.='Train Running Status']")
    private WebElement page;

    @FindBy(xpath = "//button[.='Check Live Status']")
    private WebElement check;

    @FindBy(xpath = "(//p[contains(text(), '19037')]/../..)[1]")
    private WebElement dropdown;

    public WebElement getSearchTrain() { return searchTrain; }
    public WebElement getdropdown()    { return dropdown; }
    public WebElement getCheck()       { return check; }

    public void clickcheck(WebDriver driver) {
        allUtilityFunctions.waitForClickable(driver, check, 100);
        getCheck().click();
    }

    public void searchtrainfield(WebDriver driver) {
        allUtilityFunctions.waitForVisibility(driver, page, 100);
        allUtilityFunctions.waitForClickable(driver, searchTrain, 100);
        getSearchTrain().click();
    }

    public void entertrainname(WebDriver driver, String s) throws InterruptedException {
        allUtilityFunctions.waitForVisibility(driver, page, 100);
        allUtilityFunctions.waitForClickable(driver, searchTrain, 100);
        getSearchTrain().sendKeys(s);
        allUtilityFunctions.waitForVisibility(driver, getdropdown(), 100);
        allUtilityFunctions.doubleClick(driver, getdropdown());
    }

    public void recentSearches(WebDriver driver, String name) {
        driver.findElement(By.xpath(
            "//p[@class='body-md text-primary truncate font-medium' and text()='" + name + "']"
        )).click();
    }
}
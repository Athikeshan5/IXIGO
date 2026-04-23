package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class RunningStatusPage {

    

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

    @FindBy(xpath = "//button[text()='DOWNLOAD']")
    private WebElement trainname;

    public WebElement getTrainname() {
        return trainname;
    }

    public String verifyrunningStatusPage(WebDriver driver) {
        
        allUtilityFunctions.waitForVisibility(driver, getTrainname(), 100);
        return getTrainname().getText();
    }
}
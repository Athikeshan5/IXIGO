package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class PNRStatusPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

    

    @FindBy(xpath = "//input[@inputmode='numeric' or contains(@placeholder,'PNR') or contains(@class,'pnr')]")
    private WebElement enterpnr;

    @FindBy(xpath = "//button[.='Check PNR Status']")
    private WebElement checkpnr;

    public WebElement getEnterpnr() {
        return enterpnr;
    }

    public WebElement getCheckpnr() {
        return checkpnr;
    }

    public void enterpnr(WebDriver driver, String a) {
        
        allUtilityFunctions.waitForVisibility(driver, getEnterpnr(), 100);
        getEnterpnr().click();
        getEnterpnr().clear();
        getEnterpnr().sendKeys(a);
    }

    public void clickpnr(WebDriver driver) {
        allUtilityFunctions.waitForClickable(driver, getCheckpnr(), 100);
        getCheckpnr().click();
    }
}
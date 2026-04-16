package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;

public class PNRStatusPage {

	
	//webElement locator
	@FindBy(xpath = "//input[@inputmode='numeric' or contains(@placeholder,'PNR') or contains(@class,'pnr')]")
	private WebElement enterpnr;
	
	@FindBy(xpath="//button[.='Check PNR Status']")
    private WebElement checkpnr;

	
	//getter methods
	public WebElement getEnterpnr() {
		return enterpnr;
	}

	public WebElement getCheckpnr() {
		return checkpnr;
	}
	
	
	//bussiness logic
	 public void enterpnr(String a) {

	        AllUtilityFunctions.waitForVisibility(BaseClass.driver, getEnterpnr(), 20);
	        getEnterpnr().click();
	        getEnterpnr().clear();
	        getEnterpnr().sendKeys(a);
	    }

	    public void clickpnr() {
	       AllUtilityFunctions.waitForClickable(BaseClass.driver, getCheckpnr(), 20);
	       getCheckpnr().click();
	    }

}

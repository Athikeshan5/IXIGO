package com.ixigo.testing.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.BaseClass;

public class PNRValidationPage {

	//webelement locators
	@FindBy(linkText = "PNR No. is not valid")
	private WebElement pnrmessage;

	//getter method
	public WebElement getPnrmessage() {
		return pnrmessage;
	}
	
	//bussiness logic
	public boolean validpnr(String name) {
		String f=BaseClass.driver.getCurrentUrl();
		if(f.contains(name))
		      return true;
		return false;
	}
}

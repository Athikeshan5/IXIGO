package com.ixigo.testing.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class RunningStatusPage {

	//webelement locator
	@FindBy(xpath="//h1[@class='name-number-cntr u-ib u-v-align-middle']")
	private WebElement trainname;

	
	
	//getter methods
	public WebElement getTrainname() {
		return trainname;
	}
	
	
	//bussiness logic 
	public boolean verifyrunningStatusPage(String name) {
		if(name.contains(getTrainname().getText())) {
			return true;
		}
		return false;
	}
}

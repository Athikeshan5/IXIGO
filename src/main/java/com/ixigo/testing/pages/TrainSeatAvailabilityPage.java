package com.ixigo.testing.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TrainSeatAvailabilityPage {

	//webelement locator
	@FindBy(css="[placeholder='Enter Origin']")
	private WebElement FromStation;
	
	@FindBy(css="[placeholder='Enter Destination']")
	private WebElement ToStation;
	
	@FindBy(xpath="//p[.='Departure Date']/..//span")
	private WebElement DepartureDate;
	
	@FindBy(xpath="//button[.='Check Availability']")
	private WebElement checkseat;

	
	//getter method
	public WebElement getFromStation() {
		return FromStation;
	}

	public WebElement getToStation() {
		return ToStation;
	}

	public WebElement getDepartureDate() {
		return DepartureDate;
	}

	public WebElement getCheckseat() {
		return checkseat;
	}
	
	//bussiness logic
	public void enterstation(String from,String To) {
		getFromStation().sendKeys(from);
		getToStation().sendKeys(To);
		
	}
	
	public void clickDate() {
		getDepartureDate().click();
	}
	
	public void clickseatcheck() {
		getCheckseat().click();
	}
}

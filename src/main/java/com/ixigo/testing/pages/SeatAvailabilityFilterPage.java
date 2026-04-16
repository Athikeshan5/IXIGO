package com.ixigo.testing.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeatAvailabilityFilterPage {

	
	//webelement locator
	@FindBy(xpath="//p[.='Best Available']")
	private WebElement filterBest;
	
	@FindBy(xpath="//p[.='AC Only']")
	private WebElement filterac;
	
	
}

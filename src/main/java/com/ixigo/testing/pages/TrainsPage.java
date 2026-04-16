package com.ixigo.testing.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TrainsPage {

	
	//create webelement locator for train page
	@FindBy(xpath="//div[.='Running']")
	private WebElement liveRuningTrain;
	
	@FindBy(xpath="//div[.='PNR Status']")
	private WebElement pnr;
	
	@FindBy(xpath="//div[.='Train Seat']")
	private WebElement seat;
	
	@FindBy(xpath="//div[.='Tatkal Railway']")
	private WebElement tatkal;
    
	
	//getter method to access the variable
	public WebElement getLiveRuningTrain() {
		return liveRuningTrain;
	}

	public WebElement getPnr() {
		return pnr;
	}

	public WebElement getSeat() {
		return seat;
	}

	public WebElement getTatkal() {
		return tatkal;
	}
	
	
	
	//bussiness logic for train page
	
	public void clickliveruntrain() {
		getLiveRuningTrain().click();
	}
	public void clickpnr() {
		getPnr().click();
	}
	public void clickseat() {
		getSeat().click();
	}
	public void clicktatkal() {
		getTatkal().click();
	}
	
}

package com.ixigo.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

public class TrainRuningStatusPage {

	
	
	@FindBy(css="input[data-testid='autocompleter-input']")
	private WebElement searchTrain;
	
	@FindBy(xpath="//button[.='Check Live Status']")
	private WebElement check;

	
	
	//getter method 
	public WebElement getSearchTrain() {
		return searchTrain;
	}
	public WebElement getCheck() {
		return check;
	}
	
	
	
	//bussiness logic
	public void clickcheck() {
		getCheck().click();
	}
	public void entertrainname(String s) throws InterruptedException {
		Thread.sleep(2000);
		getSearchTrain().sendKeys(s);
		AllUtilityFunctions.doubleClick(BaseClass.driver, getCheck());
		
	}
	public void searchtrainfield() {
		getSearchTrain().click();
	}
	public void recentSearches(String name,WebDriver driver) {
	 driver.findElement(By.xpath("//p[@class='body-md text-primary truncate font-medium' and text()='"+name+"']")).click();
	}
	
	
}

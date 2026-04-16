package com.ixigo.testing.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.BaseClass;

public class HomePage {
   
	
	//create webelement locator for train module
	@FindBy(xpath="//ul/child::li/child::a[@href='/trains']/child::p[.='Trains' and @class='body-sm text-xl text-secondary']")
	private WebElement trains;
	
	@FindBy(css="[id='closeButton']")
	private WebElement popup;
	
	@FindBy(xpath="//button[.='Log in/Sign up']/..//div")
	private WebElement login;
	
	@FindBy(xpath="//label[.='Enter Mobile Number']")
	private WebElement phonenumber;
	
	@FindBy(xpath="//button[.='Continue']")
	private WebElement continuebutton;
	
	@FindBy(xpath="//button[.='Verify']")
	private WebElement verify;

	
	
	//getter method to access private varaible
	public WebElement getTrains() {
		return trains;
	}

	public WebElement getLogin() {
		return login;
	}

	public WebElement getPhonenumber() {
		return phonenumber;
	}

	public WebElement getContinuebutton() {
		return continuebutton;
	}

	public WebElement getVerify() {
		return verify;
	}
	
	public WebElement getpopup() {
		return popup;
	}
	
	//bussiness logic for home page
	public void clicklogin() {
		getLogin().click();
	}
	public void clickTrains() {
	   getTrains().click();
	}
	public void clickPhoneno() {
		getPhonenumber().click();
	}
	public void clickcontinue() {
		getContinuebutton().click();
	}
	public void clickpopup() {
		getpopup().click();
	}
	
}

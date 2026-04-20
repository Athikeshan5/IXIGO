package com.ixigo.testingPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddHotelToWishListPage {

	WebDriver driver;
	WebDriverWait wait;

	public AddHotelToWishListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// LOCATORS
	@FindBy(xpath = "//p[contains(text(),'Hotels')]")
	private WebElement hotelPage;

	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name' and @data-testid='location-input-web']")
	private WebElement destinationField;

	@FindBy(xpath = "(//div[@data-testid='search-destinations']//div[@role='button'])[1]")
	private WebElement selectSuggestion;

	@FindBy(xpath = "//button[@data-testid='search-hotels']")
	WebElement searchButton;
	
	@FindBy(css = "//div[contains(@class,'rounded') or contains(@class,'shadow')]//svg")
	WebElement popupBtn;

	@FindBy(xpath = "//p[contains(text(),'Sort by')]")
	private WebElement sortDropdown;

	@FindBy(xpath = "//p[text()='User Rating']/ancestor::div[@role='listitem']")
	private WebElement userRating;

	@FindBy(xpath = "(//a[.//div[@data-testid='hotel-card']])[1]") // ❌ fixed bracket
	private WebElement firstHotel;

	@FindBy(xpath = "//button[.='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//button[.='Saved']")
	private WebElement savedBtn;

	// getter methods
	public WebElement getHotelPage() {
		return hotelPage;
	}

	public WebElement getDestinationField() {
		return destinationField;
	}

	public WebElement getSelectSuggestion() {
		return selectSuggestion;
	}

	public WebElement getSearchButton() {
		return searchButton;
	}

	public WebElement getPopupBtn() {
		return popupBtn;
	}

	

	public WebElement getSortDropdown() {
		return sortDropdown;
	}

	public WebElement getUserRating() {
		return userRating;
	}

	public WebElement getFirstHotel() {
		return firstHotel;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}

	public WebElement getSavedBtn() {
		return savedBtn;
	}

	// Business Logic

	public void clickHotelsTab() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", getHotelPage());
	}

	public void enterDestination(String destination) {
		getDestinationField().sendKeys(destination);
		getDestinationField().click();
		 
	}

	public void selectSuggestion() {
		wait.until(ExpectedConditions.elementToBeClickable(getSelectSuggestion()));
		 getSelectSuggestion().click();
	}

	public void clickSearch() {
		wait.until(ExpectedConditions.elementToBeClickable(getSearchButton()));
		getSearchButton().click();
	}
	
//	public void clickPopup() {
//		getPopupBtn().click();
//	}
	public void closePopupIfPresent() {
		By closePopup = By.xpath("//div[contains(@class,'rounded') or contains(@class,'shadow')]//svg");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
		    WebElement close = wait.until(ExpectedConditions.elementToBeClickable(closePopup));
//		    close.click();
		    ((JavascriptExecutor) driver).executeScript(
		    	    "document.querySelectorAll('div[style*=\"z-index\"]').forEach(e => e.remove());"
		    	);
		    System.out.println("Popup closed");
		} catch (Exception e) {
		    System.out.println("Popup not present");
		}
	}
	
	 public static void handlePopup(WebDriver driver, String frame, By closeBtn) {
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));

	            wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();

	            driver.switchTo().defaultContent();

	        } catch (Exception e) {
	            System.out.println("Popup not present");
	            try {
	                driver.switchTo().defaultContent();
	            } catch (Exception ignored) {}
	        }
	    }

	public void clickSortDropdown() {
		getSortDropdown().click();
	}

	public void selectUserRating() {
		getUserRating().click();
	}

	public void clickFirstHotel() {
		getFirstHotel().click();
	}

	public void clickSave() {
		getSaveBtn().click();
	}

	public boolean isHotelSaved() {
		return getSavedBtn().isDisplayed();
	}

}

package com.ixigo.testingPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddHotelToWishListPage {

	WebDriver driver;
	WebDriverWait wait;

	// LOCATORS
	@FindBy(xpath = "//p[contains(text(),'Hotels')]")
	private WebElement hotelPage;

	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name' and @data-testid='location-input-web']")
	private WebElement destinationField;

	@FindBy(xpath = "//div[@role='listitem']/descendant::p[.='FabHotel Gateway']")
	private WebElement selectSuggestion;

	@FindBy(xpath = "//button[@data-testid='search-hotels']")
	private WebElement searchBtn;

	@FindBy(css = "//div[contains(@class,'rounded') or contains(@class,'shadow')]//svg")
	WebElement popupBtn;

	@FindBy(xpath = "//p[contains(text(),'Sort by')]")
	private WebElement sortDropdown;

	@FindBy(xpath = "//p[text()='User Rating']/ancestor::div[@role='listitem']")
	private WebElement userRating;

	@FindBy(xpath = "(//div[@data-testid='hotel-card'])[1]")
	private WebElement firstHotel;

	@FindBy(xpath = "//button[text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//button[text()='Saved']")
	private WebElement savedBtn;

	public AddHotelToWishListPage(WebDriver driver2) {
		
		this.driver = driver2;
	}

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
		return searchBtn;
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
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body"))).sendKeys(Keys.ESCAPE);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@aria-label='Close modal' and @data-testid='bpg-home-modal-close']"))).click();
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
			} catch (Exception ignored) {
			}
		}
	}

	// ***********
	public void closeThePopupIfPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement closeBtn = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='bpg-home-modal-close']")));
			closeBtn.click();

		} catch (Exception e) {

		}
	}

	public void clickSortDropdown() {
		getSortDropdown().click();
	}

	public void selectUserRating() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement rating = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='User Rating']/ancestor::div[@role='listitem']")));

		rating.click();
		//getUserRating().click();
	}

	public void clickFirstHotel() {
		// getFirstHotel().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement hotel = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-testid='hotel-card'])[1]")));

		hotel.click();
	}

	public void clickSave() {
		
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    // 1️⃣ First click
		    WebElement saveBtn = wait.until(
		        ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Save')]"))
		    );
		    js.executeScript("arguments[0].click();", saveBtn);

		    // 2️⃣ Login
		    wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.cssSelector("[placeholder='Enter Mobile Number']")))
		        .sendKeys("9701241798", Keys.ENTER);

		    // ⏳ wait for OTP + login to complete
		    WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(180));

		    // 3️⃣ Re-locate NEW Save button after login
		    WebElement newSaveBtn = longWait.until(
		        ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Save')]"))
		    );

		    js.executeScript("arguments[0].click();", newSaveBtn);

//		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//		WebElement saveBtn = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Save')]")));
//
//		saveBtn.click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
//		// login
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[placeholder='Enter Mobile Number']")))
//				.sendKeys("9701241798", Keys.ENTER);
//		WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(220));
//		WebElement saveButton = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save']")));
//		saveButton.click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
	}

	public boolean isHotelSaved() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        // Check if Save button is still clickable
	        WebElement btn = wait.until(
	            ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//button[contains(.,'Save')]")
	            )
	        );

	        return btn.isDisplayed();  // if visible → action completed

	    } catch (Exception e) {
	        return false;
	    }
	}

}

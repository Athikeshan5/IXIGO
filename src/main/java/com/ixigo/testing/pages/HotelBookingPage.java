package com.ixigo.testing.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class HotelBookingPage {

	// ─── Fields ────────────────────────────────────────────────────────────────

	private WebDriver driver;
	private WebDriverWait wait;

	// ─── Locators ──────────────────────────────────────────────────────────────

	@FindBy(xpath="(//button[.='Log in/Sign up']/..//div)[1]")
	private WebElement login;
	
	@FindBy(xpath="//label[.='Enter Mobile Number']")
	private WebElement phonenumber;
	
	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name' and @data-testid='location-input-web']")
	private WebElement destinationField;

	@FindBy(xpath = "//input[@placeholder='Rooms & Guests']")
	private WebElement roomsGuestsField;

	@FindBy(xpath = "//button[@data-testid='search-hotels']")
	private WebElement searchButton;

	private final By checkInField   = By.xpath("//input[@data-testid='checkin-input']");
	private final By nextArrow      = By.xpath("//button[text()='›']");

	private final By roomsIncBtn    = By.xpath("//p[@data-testid='room-increment']");
	private final By adultsIncBtn   = By.xpath("//p[@data-testid='adult-increment']");
	private final By childrenIncBtn = By.xpath("//p[@data-testid='counter-increment-children']");

	// ─── Constructor ───────────────────────────────────────────────────────────

	public HotelBookingPage(WebDriver driver) {
		this.driver = driver;
		 this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
	}

	// ─── Action Methods ────────────────────────────────────────────────────────

	/**
	 * Enters the destination city and selects the first suggestion.
	 */
	public void enterDestination(String city) {
		AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();
		allUtilityFunctions.waitForVisibility(driver, destinationField, 20);
		destinationField.sendKeys(city);
		destinationField.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[contains(@data-testid,'FabHotel Gateway')]"))).click();
	}

	/**
	 * Opens the calendar, navigates to the given month and clicks the given date.
	 *
	 * @param month e.g. "April"
	 * @param date  e.g. "25"
	 */
	public void clickCheckInDate(String month, String date) {
		wait.until(ExpectedConditions.elementToBeClickable(checkInField)).click();

		for (int i = 0; i < 12; i++) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//abbr[contains(@aria-label,'" + month + " " + date + ", 2026')]"))).click();
				return;
			} catch (Exception ignored) {
				// Date not visible yet — go to next month
			}
			wait.until(ExpectedConditions.elementToBeClickable(nextArrow)).click();
		}
		throw new RuntimeException("Date not found: " + month + " " + date);
	}

	/**
	 * Opens the Rooms & Guests panel and sets the required counts.
	 * Default starting values assumed: 1 Room, 2 Adults, 0 Children.
	 *
	 * @param roomCount   total rooms  (String from Excel)
	 * @param adultCount  total adults (String from Excel)
	 * @param childCount  total children (String from Excel)
	 */
	public void booking(String roomCount, String adultCount, String childCount) {
		wait.until(ExpectedConditions.elementToBeClickable(roomsGuestsField)).click();

		int rooms    = Integer.parseInt(roomCount);
		int adults   = Integer.parseInt(adultCount);
		int children = Integer.parseInt(childCount);

		// Rooms: default is 1, so click + (rooms - 1) times
		for (int i = 1; i < rooms; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(roomsIncBtn)).click();
		}

		// Adults: default is 2, so click + (adults - 2) times if adults > 2
		for (int i = 2; i < adults; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(adultsIncBtn)).click();
		}

		// Children: default is 0, so click + children times
		for (int i = 0; i < children; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(childrenIncBtn)).click();
		}
	}

	/**
	 * Clicks the Search Hotels button.
	 */
	public void clickSearch() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	}
	public void closePopupIfPresent() {
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body"))).sendKeys(Keys.ESCAPE);
	}
	public WebElement getLogin() {
		return login;
	}
	public void clicklogin(WebDriver driver) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[.='Log in/Sign up']/..//div"))).click();;
	}
	public WebElement getPhonenumber() {
		return phonenumber;
	}
	public void price(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[placeholder='Enter Mobile Number']")
        )).sendKeys("8608980857", Keys.ENTER);
    }
}
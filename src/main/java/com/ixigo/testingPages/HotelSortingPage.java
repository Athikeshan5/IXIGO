package com.ixigo.testingPages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelSortingPage {

	WebDriver driver;
	WebDriverWait wait;

	public HotelSortingPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name' and @data-testid='location-input-web']")
	private WebElement destinationField;
	
	@FindBy(xpath="//div[@data-testid='search-destinations']//div[@role='button']")
	private WebElement destinationSuggestion;

	@FindBy(xpath = "//p[contains(text(),'Sort by')]")
	private WebElement sortDropdown;

	@FindBy(xpath = "//p[normalize-space()='Price']/following-sibling::p[normalize-space()='Low to High']/ancestor::div[@role='listitem']")
	private WebElement lowToHighPrice;

	// Hotel Price List
	@FindBy(xpath = "//div[@data-testid='hotel-card']//div[contains(text(),'₹')]")
	private List<WebElement> hotelPrices;

	// 🔹 Getter methods
	
public WebElement getDestination() {
	return destinationField;
}
	
	public WebElement getDestinationSuggestion() {
		return destinationSuggestion;
	}

	public WebElement getSortDropdown() {
		return sortDropdown;
	}

	public WebElement getLowToHighPrice() {
		return lowToHighPrice;
	}

	public List<WebElement> getHotelPrices() {
		return hotelPrices;
	}
	
	//Business Logic
	
	public void enterDestination(String destination) {
		getDestination().sendKeys(destination);
		getDestination().click();

	}
	
	//**
	public void selectFirstSuggestion() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for suggestion list to appear
	    List<WebElement> suggestions = wait.until(
	        ExpectedConditions.visibilityOfAllElementsLocatedBy(
	            By.xpath("//div[@data-testid='search-destinations']//div[@role='button']")
	        )
	    );

	    // Click first suggestion
	    if (!suggestions.isEmpty()) {
	        suggestions.get(0).click();
	    } else {
	        throw new RuntimeException("No suggestions found!");
	    }
	}

	// ✅ FIX 1: Re-locate Sort Dropdown (avoid stale)
	public void clickSortDropdown() {

		By sortLocator = By.xpath("//p[contains(text(),'Sort by')]");

		WebElement sort = wait.until(ExpectedConditions.elementToBeClickable(sortLocator));

		sort.click();
	}

	
	public void selectLowToHigh() {
		

		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//		    // 1️⃣ Open Sort dropdown
//		    wait.until(
//		        ExpectedConditions.presenceOfElementLocated(
//		            By.xpath("//p[contains(text(),'Sort by')]")
//		        )
//		    ).click();

		    WebElement price = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Low to High']/ancestor::div[@role='listitem']")));

            price.click();
		
//		
	}

	// ✅ FIX 3: Wait using fresh locator (NOT WebElement list)
	public void waitForHotelsToLoad() {
		By priceLocator = By.xpath(
		        "//div[@data-testid='hotel-card']//div[contains(text(),'₹')]"
		    );

//		    // ✅ Step 1: Wait for old results to go stale (DOM refresh)
//		    wait.until(ExpectedConditions.stalenessOf(
//		        driver.findElement(priceLocator)
//		    ));

		    // ✅ Step 2: Wait for new results to appear
		    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(priceLocator));

		    // ✅ Step 3: Extra buffer for price DOM to fully settle
		    try {
		        Thread.sleep(2000);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		    }

	}

	// ✅ FIX 4: Re-fetch prices every time (NO stale)
	public List<Integer> getAllHotelPrices() {

		By priceLocator = By.xpath("//div[@data-testid='hotel-card']//div[contains(text(),'₹')]");
		 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(priceLocator));

		List<WebElement> elements = driver.findElements(priceLocator);

		List<Integer> prices = new ArrayList<>();

		for (WebElement price : elements) {

			try {
				String text = price.getText().replaceAll("[^0-9]", "");

				if (!text.isEmpty()) {
					prices.add(Integer.parseInt(text));
				}

			} catch (StaleElementReferenceException e) {
				// retry once if stale
				return getAllHotelPrices();
			}
		}

		return prices;
	}

	// ✅ Pairwise validation (your logic)
	public boolean isSortedPairwise() {

		List<Integer> prices = getAllHotelPrices();

		for (int i = 0; i < prices.size() - 1; i++) {

			if (prices.get(i) > prices.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public void userrating() {
		By rating=By.xpath("(//p[.='User Rating']/..//div)[1]");
		wait.until(ExpectedConditions.presenceOfElementLocated(rating)).click();
	}

}

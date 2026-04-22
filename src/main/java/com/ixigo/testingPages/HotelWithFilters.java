package com.ixigo.testingPages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.util.List;

public class HotelWithFilters {
	WebDriver driver;
	WebDriverWait wait;

	public HotelWithFilters(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	
	//LOCATORS
	

	// Star rating container
	private By starRatingSection = By.xpath("//p[contains(text(),'Star Rating')]/following-sibling::div");

	// Individual star rating options
	private By starOptions = By.xpath(
			"//p[contains(text(),'Star Rating')]/following-sibling::div//div[contains(@class,'cursor-pointer')]");

	// User rating container
	private By userRatingSection = By.xpath("//p[contains(text(),'User Rating')]");

	// User rating options (like Exceptional: 9+)
	private By userRatingOptions = By.xpath("//p[contains(text(),'User Rating')]/following::div[@role='listitem']");

	// Price
	// Price Section
	private By priceSection = By.xpath("//p[contains(text(),'Price')]");

	// Min slider (left)
	private By minPriceSlider = By.xpath("(//input[@type='range'])[1]");

	// Max slider (right)
	private By maxPriceSlider = By.xpath("(//input[@type='range'])[2]");

	//METHODS

	//Star Rating
	public void selectStarRating(String starRating) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(starRatingSection));

		List<WebElement> stars = driver.findElements(starOptions);

		for (WebElement star : stars) {
			String text = star.getText().trim();

			if (text.contains(starRating)) {
				wait.until(ExpectedConditions.elementToBeClickable(star)).click();
				System.out.println("Selected Star Rating: " + starRating);
				break;
			}
		}
	}

	//User Rating
	public void selectUserRating(String userRating) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(userRatingSection));

		List<WebElement> ratings = driver.findElements(userRatingOptions);

		for (WebElement rating : ratings) {
			String text = rating.getText().trim();

			if (text.toLowerCase().contains(userRating.toLowerCase())) {
				wait.until(ExpectedConditions.elementToBeClickable(rating)).click();
				System.out.println("Selected User Rating: " + userRating);
				break;
			}
		}
	}

	//Price Filter
	public void applyPriceFilter(String priceRange) {

		String[] prices = priceRange.split("-");
		int minPrice = Integer.parseInt(prices[0]);
		int maxPrice = Integer.parseInt(prices[1]);

		wait.until(ExpectedConditions.visibilityOfElementLocated(priceSection));

		WebElement minSlider = wait.until(ExpectedConditions.presenceOfElementLocated(minPriceSlider));
		WebElement maxSlider = wait.until(ExpectedConditions.presenceOfElementLocated(maxPriceSlider));

		int sliderMin = Integer.parseInt(minSlider.getAttribute("min"));
		int sliderMax = Integer.parseInt(minSlider.getAttribute("max"));

		int uiMinPrice = 400;
		int uiMaxPrice = 46000;

		int minValue = (minPrice - uiMinPrice) * (sliderMax - sliderMin) / (uiMaxPrice - uiMinPrice);
		int maxValue = (maxPrice - uiMinPrice) * (sliderMax - sliderMin) / (uiMaxPrice - uiMinPrice);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].value='" + minValue + "';", minSlider);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'))", minSlider);

		js.executeScript("arguments[0].value='" + maxValue + "';", maxSlider);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'))", maxSlider);

		System.out.println("Applied Price Filter: " + priceRange);
	}
}

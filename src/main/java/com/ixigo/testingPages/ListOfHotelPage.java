package com.ixigo.testingPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.BaseClass;

public class ListOfHotelPage {
	public BaseClass b;
	WebDriver driver;
	public ListOfHotelPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
	}

	public void hotel(String hotelname) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
          wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body"))).sendKeys(Keys.ESCAPE);
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[.='Book Now'])[2]"))).click();
		 
	}
	
}

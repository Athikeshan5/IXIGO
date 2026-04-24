package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.BaseClass;

public class ListOfHotelPage {
	

	public void hotel(String hotelname,WebDriver driver) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
          wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body"))).sendKeys(Keys.ESCAPE);
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[.='Book Now'])[2]"))).click();
		 
	}
	
}

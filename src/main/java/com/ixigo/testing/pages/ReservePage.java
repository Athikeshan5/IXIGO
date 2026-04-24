package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;

public class ReservePage {
	


	public void reserve(WebDriver driver) {
		AllUtilityFunctions allutil=new AllUtilityFunctions();
		allutil.switchToWindow(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@data-testid='reserve-recommended-room'])[1]"))).click();
		 
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//            By.cssSelector("[placeholder='Enter Mobile Number']")
//        )).sendKeys("9701241798", Keys.ENTER);
//        
//        WebElement reserveBtnAfterLogin = wait.until(
//    	        ExpectedConditions.elementToBeClickable(
//    	            By.xpath("(//button[@data-testid='reserve-recommended-room'])[1]")
//    	        )
//    	    );
        
        
        
//        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(220));
//        WebElement res = waits.until(
//        	    ExpectedConditions.presenceOfElementLocated(
//        	        By.xpath("(//button[@data-testid='reserve-recommended-room'])[1]")
//        	    )
//        	);
	}
	
	public String verify(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		String ver=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@data-testid='reserve-recommended-room'])[1]"))).getText();
		 return ver;
	}
}

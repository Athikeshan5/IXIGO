
package com.ixigo.testing.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class TakalTicketBookingPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

 

    private By dateIconBy = By.xpath("//button[@aria-label='Open calendar']");

    public void bookticket(WebDriver driver, String date) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Step 1: Open calendar
        wait.until(ExpectedConditions.elementToBeClickable(dateIconBy)).click();

        // Step 2: Click specific date
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@class,'react-calendar__tile react-calendar__month-view__days__day')]" +
                         "/abbr[@aria-label='June " + date + ", 2026']")
            )).click();
    }

    public void seattype(WebDriver driver) {
        
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click Available or Waitlist seat
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[starts-with(text(),'Available') or contains(text(),'Waitlist')]")
        )).click();
    }

    public void tatkalfilter(WebDriver driver) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Filter tatkal
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@class='flex items-center gap-5']/p[.='Tatkal Only']")
        )).click();
    	
    }
    public void clickbook(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(),'Book')]/../..//button")
        )).click();
    }

    

    public void passenger(WebDriver driver, String name, String age) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(80));


        waits.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[.='Add New passenger']"))).click();

        waits.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='name']"))).sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[type='number']"))).sendKeys(age);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[.='Save Passenger']"))).click();


    }

    public String verifybook(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h5[.='Contact Details']")
        )).getText();
    }
}
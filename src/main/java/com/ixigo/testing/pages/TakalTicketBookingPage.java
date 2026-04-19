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

public class TakalTicketBookingPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

 

    private By dateIconBy = By.xpath("//button[@aria-label='Open calendar']");

    public void bookticket(WebDriver driver, String date) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Step 1: Open calendar
        wait.until(ExpectedConditions.elementToBeClickable(dateIconBy)).click();

        // Step 2: Click specific date
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//abbr[contains(@aria-label,'April 21, 2026')]")
        )).click();
    }

    public void seattype(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Filter tatkal
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("(//div[@class='flex items-center gap-5']/child::span)[2]")
        )).click();

        // Click Available or Waitlist seat
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[starts-with(text(),'Available') or contains(text(),'Waitlist')]")
        )).click();
    }

    public void clickbook(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(),'Book')]/../..//button")
        )).click();
    }

    public void price(WebDriver driver, String num) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[placeholder='Enter Mobile Number']")
        )).sendKeys(num, Keys.ENTER);
    }

    public void passenger(WebDriver driver, String name, String age) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(40));

        waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[.='Add New Passenger']")));
        waits.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='name']"))).sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[type='number']"))).sendKeys(age);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[.='Save Passenger']"))).click();

        WebElement web = waits.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[.='Proceed to Pay']")
        ));

        allUtilityFunctions.scrollToElement(driver, web);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", web);
    }

    public String verifybook(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h5[.='Contact Details']")
        )).getText();
    }
}
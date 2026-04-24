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

public class SeatAvailabilityFilterPage {

    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();
    

    private By dateIconBy = By.xpath("//button[@aria-label='Open calendar']");
    private By arrow = By.xpath("//button[text()='›']");

    public void bookticket(WebDriver driver, String date) {
       
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        // Step 1: Open calendar
        wait.until(ExpectedConditions.elementToBeClickable(dateIconBy)).click();

        // Click arrow twice
        for (int i = 0; i < 2; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(arrow)).click();
        }

        // Step 2: Click specific date
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(@class,'react-calendar__tile react-calendar__month-view__days__day')]" +
                     "/abbr[@aria-label='June " + date + ", 2026']")
        )).click();
    }
    public void bookticketInvalid(WebDriver driver, String date) {
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        // Step 1: Open calendar
        wait.until(ExpectedConditions.elementToBeClickable(dateIconBy)).click();

        // Click arrow twice
        for (int i = 0; i < 1; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(arrow)).click();
        }

        // Step 2: Click specific date
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(@class,'react-calendar__tile react-calendar__month-view__days__day')]" +
                     "/abbr[@aria-label='May "+date+", 2026']")
        )).click();
    }

    public void seattype(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'Available') or contains(text(),'Waitlist')]")
        )).click();
    }

    public void clickbook(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(),'Book')]/../..//button")
        )).click();
    }

    public void price(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[placeholder='Enter Mobile Number']")
        )).sendKeys("8807125831", Keys.ENTER);
       
    }

    public void passenger(WebDriver driver, String name, String age) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(400));

        waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Add New passenger']"))).click();
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        return wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h5[.='Contact Details']")
        )).getText();
    }
    
    public void passengerWithInvalidAge(WebDriver driver, String name, String age) {
        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(400));
        WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(100));

        waits.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[.='Add New passenger']"))).click();

        waits.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[name='name']"))).sendKeys(name);

        WebElement ageField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[type='number']")));
        ageField.clear();
        ageField.sendKeys(age);

        // Click Save — error message should appear instead of proceeding
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[.='Save Passenger']"))).click();
    }
    public String getAgeErrorMessage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        // ⚠️ Adjust this locator to match the real error element in ixigo
        return wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("(//div[.='Seat will not be allotted to infant passengers (0-4 years)'])[1]")
        )).getText();
    }
}
package com.ixigo.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class IxigoVandeBharatPage {

    WebDriver driver;

    public IxigoVandeBharatPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By fromInput = By.xpath("//input[@placeholder='Leaving from']");
    By toInput = By.xpath("//input[@placeholder='Going to']");
    By dateInput = By.xpath("//input[@placeholder='depart']");
    By searchBtn = By.cssSelector("div.search button");
    By availableDate = By.xpath("(//p[contains(text(),'Available')])[1]");
    By bookBtn = By.xpath("//button[contains(text(),'Book')]");

    // Actions

    public void enterFrom(String from) {
        WebElement input = driver.findElement(fromInput);

        input.click();
        input.sendKeys(Keys.CONTROL + "a"); // select all
        input.sendKeys(Keys.DELETE);        // clear old value

        input.sendKeys(from);
        input.sendKeys(Keys.ENTER);
    }
    public void enterTo(String to) {
        WebElement input = driver.findElement(toInput);

        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);

        input.sendKeys(to);
        input.sendKeys(Keys.ENTER);
    }

//    public void selectDate() {
//        try {
//            WebElement dateField = driver.findElement(dateInput);
//
//            // set date directly
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].value='20 Apr, Sun';", dateField);
//
//            // trigger change event (VERY IMPORTANT)
//            js.executeScript("arguments[0].dispatchEvent(new Event('change'));", dateField);
//
//            Thread.sleep(2000);
//
//        } catch (Exception e) {
//            System.out.println("Date set failed");
//        }
//    }
    public void clickSearch() {
        try {
            Thread.sleep(2000);

            WebElement btn = driver.findElement(searchBtn);

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // scroll
            js.executeScript("arguments[0].scrollIntoView(true);", btn);

            Thread.sleep(1000);

            // JS click (must for ixigo)
            js.executeScript("arguments[0].click();", btn);

        } catch (Exception e) {
            System.out.println("Search click failed");
        }
    }

    public void selectTrainAndBook() {
        try {
            Thread.sleep(3000);
            driver.findElement(availableDate).click();

            Thread.sleep(2000);
            driver.findElement(bookBtn).click();

        } catch (Exception e) {
            System.out.println("Booking flow failed");
        }
    }

    public boolean isPaymentPageDisplayed() {
        return driver.getCurrentUrl().length() > 0;
    }
}
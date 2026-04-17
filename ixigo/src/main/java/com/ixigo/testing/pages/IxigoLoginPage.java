//package com.ixigo.testing.pages;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//import java.time.Duration;
//
//public class IxigoLoginPage {
//
//    WebDriver driver;
//    WebDriverWait wait;
//
//    public IxigoLoginPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//    }
//
//    // Locators
//    By loginBtn = By.xpath("//button[contains(text(),'Log in/Sign up')]");
//    By mobileField = By.xpath("//input[@placeholder='Enter Mobile Number']");
//    By continueBtn = By.xpath("//button[contains(text(),'Continue')]");
//    By profileIcon = By.xpath("//div[contains(@class,'cursor-pointer')]"); 
//    
//    // Click Login
//    public void clickLogin() {
//        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
//    }
//
//    // Enter Mobile Number
//    public void enterMobile(String number) {
//        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileField));
//        field.clear();
//        field.sendKeys(number);
//    }
//
//    // Click Continue
//    public void clickContinue() {
//        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
//    }
//
//    // Wait for OTP manually
//    public void waitForOTP() {
//        try {
//            System.out.println("Waiting 60 seconds for OTP entry...");
//            Thread.sleep(60000); 
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    
//}
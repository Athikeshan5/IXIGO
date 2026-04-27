package com.ixigo.testing.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PassengerPage {


    @FindBy(xpath = "//input[@placeholder='Name']")
    private WebElement name;
    @FindBy(xpath = "//input[@placeholder='Age']")  
    private WebElement age;
    @FindBy(xpath = "//button[.='Female']")         
    private WebElement gender;
    @FindBy(xpath="//a[@class='btn  dark filled primary md rounded-md inactive button']")
    private WebElement continuepay;

    public void enterPassengerDetails(WebDriver driver, String nameValue, String ageValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.switchTo().defaultContent();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("abrs-backdrop")));
        } catch (Exception e) {
            js.executeScript(
                "let el=document.querySelector('.abrs-backdrop'); if(el) el.remove();");
        }

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='Name']")));
        nameField.click();
        nameField.clear();
        nameField.sendKeys(nameValue);
        System.out.println("Name entered: " + nameValue);

        String cleanedAge = ageValue.contains(".") ? ageValue.split("\\.")[0] : ageValue;
        WebElement ageField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='Age']")));
        ageField.click();
        ageField.clear();
        ageField.sendKeys(cleanedAge);
        System.out.println("Age entered: " + cleanedAge);

        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[.='Female']"))).click();
        System.out.println("Gender selected: Female");
    }

    public void clickContinue(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waitFor(1500);

        try {
            WebElement pol = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class,'row card-body  light  ')])[3]")));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", pol);
            waitFor(500);
            js.executeScript("arguments[0].click();", pol);
            System.out.println("Policy div clicked");
            waitFor(800);
        } catch (Exception e) {
            System.out.println("Policy div not found: " + e.getMessage());
        }

        try {
            WebElement assured = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//input[@type='checkbox'])[1]")));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", assured);

            if (!assured.isSelected()) {
                js.executeScript("arguments[0].click();", assured);
                System.out.println("Assured checkbox clicked");
            } else {
                System.out.println("Checkbox already selected, skipping");
            }
            waitFor(500);
        } catch (Exception e) {
            System.out.println("Assured checkbox not found: " + e.getMessage());
        }
//
//        try {
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
//                "//a[contains(@class,'filled') and contains(@class,'primary')" +
//                " and not(contains(@class,'inactive'))]")));
//            System.out.println("Continue to Pay button is ACTIVE");
//        } catch (Exception e) {
//            System.out.println("WARNING: Button may still be inactive — trying anyway");
//        }

//        String[] xpaths = {
//            "//a[contains(@class,'filled') and contains(@class,'primary')" +
//                " and not(contains(@class,'inactive'))]",
//            "//a[contains(.,'Continue to Pay') and not(contains(@class,'inactive'))]",
//            "//button[contains(.,'Continue to Pay') and not(contains(@class,'inactive'))]",
//            "//*[contains(.,'Continue to Pay')]"
//        };
//
//        boolean clicked = false;
//        for (String xpath : xpaths) {
//            try {
//                WebElement continueBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
//                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
//
//                js.executeScript("arguments[0].scrollIntoView({block:'center'});", continueBtn);
//                waitFor(800);
//
//                continueBtn = driver.findElement(By.xpath(xpath));
//                js.executeScript("arguments[0].click();", continueBtn);
//                System.out.println("Continue to Pay clicked via: " + xpath);
//                clicked = true;
//                break;
//            } catch (Exception e) {
//                System.out.println("XPath failed: " + xpath + " → " + e.getMessage());
//            }
//        }
//
//        if (!clicked)
//            throw new RuntimeException("Continue to Pay button NOT found or not clickable");
//
//        try {
//            new WebDriverWait(driver, Duration.ofSeconds(60))
//                .until(ExpectedConditions.not(
//                    ExpectedConditions.urlContains("passengerinfo")));
//            System.out.println("Payment page reached. URL: " + driver.getCurrentUrl());
//        } catch (Exception e) {
//            System.out.println("Still on passengerinfo — OTP may be needed. URL: "
//                + driver.getCurrentUrl());
//        }
    }

    private void waitFor(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
    public void clickContinueToPay(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continuepay));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        js.executeScript("arguments[0].click();", btn);
        System.out.println("Continue to Pay clicked.");
    }

    public boolean isPaymentPageDisplayed(WebDriver driver) {
        String url = driver.getCurrentUrl().toLowerCase();
        return url.contains("payment") || url.contains("pay")
            || url.contains("checkout") || url.contains("verify");
    }
}
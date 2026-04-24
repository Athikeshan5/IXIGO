package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeatSelectionPage {

    // ✅ NO driver/wait fields — passed as parameters instead

    @FindBy(xpath = "(//span[contains(.,'₹800')])[11]")
    private List<WebElement> availableSeats;

    @FindBy(xpath = "//div[@id='place-4158598-3979430527']")
    private WebElement boarding;

    @FindBy(xpath = "//div[@id='place-4158640-3979430527']")
    private WebElement dropping;

    @FindBy(xpath = "//button[.='Proceed']")
    private WebElement continueBtn;

    public void selectSeat(WebDriver driver, String busName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[contains(.,'Select Seats')])[3]")));
            jsClick(driver, btn);
            System.out.println("Bus found and seats clicked: " + busName);
        } catch (TimeoutException e) {
            System.out.println("Bus not available: " + busName);
        }
    }

    public void selectSeatNumber(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(availableSeats));
            if (!availableSeats.isEmpty()) {
                jsClick(driver, availableSeats.get(0));
                System.out.println("Seat selected successfully");
            }
        } catch (TimeoutException e) {
            System.out.println("Seat layout not loaded in time");
        }
    }

    public void selectBoardingPoint(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(boarding));
            jsClick(driver, boarding);
            System.out.println("Boarding point selected");
        } catch (TimeoutException e) {
            try {
                WebElement fallback = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[starts-with(@id,'place-')])[1]")));
                jsClick(driver, fallback);
            } catch (Exception ex) {
                System.out.println("Boarding fallback failed: " + ex.getMessage());
            }
        }
    }

    public void selectDroppingPoint(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropping));
            jsClick(driver, dropping);
            System.out.println("Dropping point selected");
        } catch (TimeoutException e) {
            try {
                WebElement fallback = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[starts-with(@id,'place-')])[2]")));
                jsClick(driver, fallback);
            } catch (Exception ex) {
                System.out.println("Dropping fallback failed: " + ex.getMessage());
            }
        }
    }

    public void clickContinue(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        jsClick(driver, continueBtn);
        waitForManualOtpAndProceed(driver);
    }

    private void jsClick(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(400);
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void waitForManualOtpAndProceed(WebDriver driver) {
        String sessionId = getSessionId(driver);
        driver.switchTo().defaultContent();
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        } catch (Exception ignored) {}
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var b=document.createElement('div');b.id='otp-banner';" +
                "b.style.cssText='position:fixed;top:0;left:0;width:100%;" +
                "background:orange;color:white;font-size:20px;font-weight:bold;" +
                "text-align:center;padding:12px;z-index:999999;';" +
                "b.innerHTML='⚠️ ENTER OTP NOW - SESSION: " + sessionId + "';" +
                "document.body.appendChild(b);"
            );
        } catch (Exception ignored) {}
        try {
            new WebDriverWait(driver, Duration.ofSeconds(100))
                .until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("passengerinfo")));
        } catch (Exception e) {
            System.out.println("[" + sessionId + "] Timeout waiting for OTP.");
        }
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var b=document.getElementById('otp-banner'); if(b) b.remove();");
        } catch (Exception ignored) {}
    }

    private String getSessionId(WebDriver driver) {
        try {
            return ((RemoteWebDriver) driver).getSessionId().toString().substring(0, 8);
        } catch (Exception e) {
            return "unknown";
        }
    }
}
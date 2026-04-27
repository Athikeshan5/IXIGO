package com.ixigo.testing.utilities;

import java.io.*;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class AllUtilityFunctions {

    BaseClass b = new BaseClass();
    WebDriver driver;
    WebDriverWait wait;

    public AllUtilityFunctions() {}

    public AllUtilityFunctions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // CORE
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator) { waitForClickable(locator).click(); }

    public void jsClick(By locator) {
        WebElement el = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void sendKeys(By locator, String value) {
        WebElement el = waitForVisibility(locator);
        el.clear();
        el.sendKeys(value);
    }

    public void pressEnter(By locator) {
        waitForVisibility(locator).sendKeys(Keys.ENTER);
    }

    public String getText(By locator) { return waitForVisibility(locator).getText(); }

    public boolean isDisplayed(By locator) {
        try { return waitForVisibility(locator).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public void scrollIntoView(By locator) {
        WebElement el = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", el);
    }

    public void sleep(int seconds) {
        try { Thread.sleep(seconds * 1000L); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // SHARED METHODS 

    public void openUrl(WebDriver driver, String url) { driver.get(url); }
    public void maximizeBrowser(WebDriver driver) { driver.manage().window().maximize(); }

    public void implicitWait(WebDriver driver, long sec) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
    }

    public String getPropertyValue(String key) {
        try {
            FileInputStream fs = new FileInputStream(
                "./src/test/resources/Reader/commonData.properties");
            Properties prop = new Properties();
            prop.load(fs);
            return prop.getProperty(key);
        } catch (Exception e) { return ""; }
    }

    // EXTENT REPORT (Thread-safe)

    private static ExtentReports extent;
    private static final Object lock = new Object();

    public ExtentReports getReport() {
        synchronized (lock) {
            if (extent == null) {
                ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/extent.html");
                extent = new ExtentReports();
                extent.attachReporter(reporter);
            }
            return extent;
        }
    }

    public void createTest(String name) { BaseClass.test.set(getReport().createTest(name)); }
    public void pass(String msg)        { BaseClass.test.get().pass(msg); }
    public void fail(String msg)        { BaseClass.test.get().fail(msg); }

    // SCREENSHOT (auto-creates ScreenShot/ folder)

    public String takeScreenshot(WebDriver driver, String name) {
        File dir = new File("ScreenShot");
        if (!dir.exists()) dir.mkdirs();                  // auto-create folder

        String path = "ScreenShot/" + name + ".png";
        try {
            // only take screenshot if driver session is still alive
            if (driver == null) return path;
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File(path));
        } catch (Exception e) {
            System.out.println("Screenshot skipped: " + e.getMessage());
        }
        return path;
    }

    public void captureFailure(WebDriver driver, String testName) {
        try {
            if (driver == null) return;
            String name = testName.replaceAll(" ", "_");
            String relativePath = takeScreenshot(driver, name);
            String absolutePath = new File(relativePath).getAbsolutePath();
            BaseClass.test.get().fail("Test Failed: " + name);
            BaseClass.test.get().addScreenCaptureFromPath(absolutePath);
        } catch (Exception e) {
            System.out.println("captureFailure skipped: " + e.getMessage());
        }
    }

    public void log(String msg) { System.out.println(msg); }

    // TEAM COMPATIBILITY OVERLOADS

    public WebElement waitForVisibility(WebDriver driver, WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
            .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebDriver driver, WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
            .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", element);
    }
}
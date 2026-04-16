package com.ixigo.testing.utilities;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class AllUtilityFunctions {

    //  BASIC METHODS 

    public static void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public static void maximizeBrowser(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public static void minimizeBrowser(WebDriver driver) {
        driver.manage().window().minimize();
    }

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void closeBrowser(WebDriver driver) {
        driver.close();
    }

    public static void quitBrowser(WebDriver driver) {
        driver.quit();
    }

    //  NAVIGATION 

    public static void navigateTo(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public static void navigateTo(WebDriver driver, URL url) {
        driver.navigate().to(url);
    }

    public static void navigateBack(WebDriver driver) {
        driver.navigate().back();
    }

    public static void navigateForward(WebDriver driver) {
        driver.navigate().forward();
    }

    public static String getTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public static  String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    // ELEMENT ACTIONS 

    public static void click(WebElement ele) {
        ele.click();
    }

    public static void sendKeys(WebElement ele, String value) {
        ele.clear();
        ele.sendKeys(value);
    }

    public static String getText(WebElement ele) {
        return ele.getText();
    }

    public static boolean isDisplayed(WebElement ele) {
        return ele.isDisplayed();
    }

    // WAIT METHODS 

    public static void implicitWait(WebDriver driver, long sec) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
    }

    public static void waitForVisibility(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOf(ele));
    }

    public static void waitForClickable(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.elementToBeClickable(ele));
    }

    public static void waitForTitle(WebDriver driver, String title, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.titleContains(title));
    }

    //  ACTION METHODS 

    public static void doubleClick(WebDriver driver, WebElement ele) {
        new Actions(driver).doubleClick(ele).perform();
    }

    public static void rightClick(WebDriver driver, WebElement ele) {
        new Actions(driver).contextClick(ele).perform();
    }

    public static void moveToElement(WebDriver driver, WebElement ele) {
        new Actions(driver).moveToElement(ele).perform();
    }

    public static void dragAndDrop(WebDriver driver, WebElement src, WebElement dest) {
        new Actions(driver).dragAndDrop(src, dest).perform();
    }

    public static void scrollToElement(WebDriver driver, WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    //  ALERT

    public static void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public static void dismissAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public static String getAlertText(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public static void sendAlertText(WebDriver driver, String msg) {
        driver.switchTo().alert().sendKeys(msg);
    }

    // FRAME 

    public static void switchFrameByIndex(WebDriver driver, int index) {
        driver.switchTo().frame(index);
    }

    public static void switchFrameByName(WebDriver driver, String name) {
        driver.switchTo().frame(name);
    }

    public static void switchFrameByElement(WebDriver driver, WebElement ele) {
        driver.switchTo().frame(ele);
    }

    public static void switchToDefault(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    //  WINDOW 

    public static void switchToWindowByTitle(WebDriver driver, String title) {
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    //  SCREENSHOT 

    public static String takeScreenshot(WebDriver driver, String name) {
        String path = "ScreenShot/" + name + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    //  EXCEL 

    public static String getExcelData(String path, String sheet, int row, int col) {
        try {
            FileInputStream fis = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            String data = wb.getSheet(sheet).getRow(row).getCell(col).toString();
            wb.close();
            return data;
        } catch (Exception e) {
            return "";
        }
    }

    //  PROPERTY

    public static String getPropertyValue(String key) {
        try {
            FileInputStream fs = new FileInputStream("./src/test/resources/Reader/commonData.properties");
            Properties prop = new Properties();
            prop.load(fs);
            return prop.getProperty(key);
        } catch (Exception e) {
            return "";
        }
    }

    //  JAVA UTILITY 

    public static int getRandomNumber() {
        return new Random().nextInt(1000);
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    //  EXTENT REPORT 

    public static ExtentReports extent;
    public static ExtentTest test;

    public static ExtentReports getReport() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/extent.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static void createTest(String name) {
        test = getReport().createTest(name);
    }

    public static void pass(String msg) {
        test.pass(msg);
    }

    public static void fail(String msg) {
        test.fail(msg);
    }

    // failed screenshot add on report

    public static void captureFailure(WebDriver driver, String testName) {
        try {
            String name = testName.replaceAll(" ", "_");
            String path = takeScreenshot(driver, name);
            test.fail("Test Failed: " + name);
            test.addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // print statement in console

    public static void log(String msg) {
        System.out.println(msg);
    }
    
    //popup handling
    public static void handlePopup(WebDriver driver, String frameName, By closeLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));

            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(closeLocator));

            closeBtn.click();
            driver.switchTo().defaultContent();

        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
    }
}
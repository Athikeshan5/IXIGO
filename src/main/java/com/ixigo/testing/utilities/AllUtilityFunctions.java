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

	BaseClass b=new BaseClass();
    //  BASIC METHODS 

    public  void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public  void maximizeBrowser(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public  void minimizeBrowser(WebDriver driver) {
        driver.manage().window().minimize();
    }

    public  void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public  void closeBrowser(WebDriver driver) {
        driver.close();
    }

    public  void quitBrowser(WebDriver driver) {
        driver.quit();
    }

    //  NAVIGATION 

    public  void navigateTo(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public  void navigateTo(WebDriver driver, URL url) {
        driver.navigate().to(url);
    }

    public  void navigateBack(WebDriver driver) {
        driver.navigate().back();
    }

    public  void navigateForward(WebDriver driver) {
        driver.navigate().forward();
    }

    public  String getTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public   String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    // ELEMENT ACTIONS 

    public  void click(WebElement ele) {
        ele.click();
    }

    public  void sendKeys(WebElement ele, String value) {
        ele.clear();
        ele.sendKeys(value);
    }

    public  String getText(WebElement ele) {
        return ele.getText();
    }

    public boolean isDisplayed(WebElement ele) {
        return ele.isDisplayed();
    }

    // WAIT METHODS 

    public  void implicitWait(WebDriver driver, long sec) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
    }

    public  void waitForVisibility(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOf(ele));
    }

    public  void waitForClickable(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.elementToBeClickable(ele));
    }

    public  void waitForTitle(WebDriver driver, String title, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.titleContains(title));
    }

    //  ACTION METHODS 

    public  void doubleClick(WebDriver driver, WebElement ele) {
        new Actions(driver).doubleClick(ele).perform();
    }

    public  void rightClick(WebDriver driver, WebElement ele) {
        new Actions(driver).contextClick(ele).perform();
    }

    public void moveToElement(WebDriver driver, WebElement ele) {
        new Actions(driver).moveToElement(ele).perform();
    }

    public  void dragAndDrop(WebDriver driver, WebElement src, WebElement dest) {
        new Actions(driver).dragAndDrop(src, dest).perform();
    }

    public  void scrollToElement(WebDriver driver, WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    //  ALERT

    public  void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public  void dismissAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public  String getAlertText(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public  void sendAlertText(WebDriver driver, String msg) {
        driver.switchTo().alert().sendKeys(msg);
    }

    // FRAME 

    public  void switchFrameByIndex(WebDriver driver, int index) {
        driver.switchTo().frame(index);
    }

    public  void switchFrameByName(WebDriver driver, String name) {
        driver.switchTo().frame(name);
    }

    public  void switchFrameByElement(WebDriver driver, WebElement ele) {
        driver.switchTo().frame(ele);
    }

    public  void switchToDefault(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    //  WINDOW 

    public  void switchToWindowByTitle(WebDriver driver) {
    	String parent=driver.getWindowHandle();
        for (String win : driver.getWindowHandles()) {
           
            if (parent.contains(win)) {
                continue;
            }
            driver.switchTo().window(win);
        }
    }

    //  SCREENSHOT 

    public  String takeScreenshot(WebDriver driver, String name) {
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

    public  String getExcelData(String path, String sheet, int row, int col) {
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

    public  String getPropertyValue(String key) {
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

    public  int getRandomNumber() {
        return new Random().nextInt(1000);
    }

    public  String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    //  EXTENT REPORT 

    private static ExtentReports extent;
   
    private static final Object lock = new Object();
    public  ExtentReports getReport() {
    	// synchronized — only one thread creates it
        synchronized (lock) {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/extent.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
    }
    public  void createTest(String name) {
    	BaseClass.test.set(getReport().createTest(name));
    }

    public  void pass(String msg) {
    	BaseClass.test.get().pass(msg);
    }

    public  void fail(String msg) {
    	BaseClass.test.get().fail(msg);
    }

    // failed screenshot add on report

    public  void captureFailure(WebDriver driver, String testName) {
        try {
            // 1. Sanitize the name
            String name = testName.replaceAll(" ", "_");
            
            // 2. Get the relative path from your takeScreenshot method
            String relativePath = takeScreenshot(driver, name);
            
            // 3. Convert to Absolute Path (This is the fix)
            File f = new File(relativePath);
            String absolutePath = f.getAbsolutePath();
            
            // 4. Log the failure and attach the ABSOLUTE path
           BaseClass. test.get().fail("Test Failed: " + name);
           BaseClass. test.get().addScreenCaptureFromPath(absolutePath);
            System.out.println("Screenshot attached to report from: " + absolutePath);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // print statement in console

    public  void log(String msg) {
        System.out.println(msg);
    }
    
    //popup handling
    public  void handlePopup(WebDriver driver, String frameName, By closeLocator) {
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
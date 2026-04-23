package com.ixigo.testing.utilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.ExtentTest;

/**
 * AllUtilityFunctions — reusable Selenium + reporting helpers.
 *
 * IMPORTANT RULES for parallel execution:
 *   1. Never store state in instance fields — this class is shared across threads.
 *   2. All report calls go through BaseClass.test.get() (the ThreadLocal ExtentTest).
 *   3. ExtentReports instance comes from ReportManager — nowhere else.
 *   4. Cookie operations go through CookieManager — nowhere else.
 */
public class AllUtilityFunctions {

    // ── BROWSER ──────────────────────────────────────────────────────────

    public void openUrl(WebDriver driver, String url)        { driver.get(url); }
    public void maximizeBrowser(WebDriver driver)            { driver.manage().window().maximize(); }
    public void minimizeBrowser(WebDriver driver)            { driver.manage().window().minimize(); }
    public void refresh(WebDriver driver)                    { driver.navigate().refresh(); }
    public void closeBrowser(WebDriver driver)               { driver.close(); }
    public void quitBrowser(WebDriver driver)                { driver.quit(); }

    // ── NAVIGATION ───────────────────────────────────────────────────────

    public void navigateBack(WebDriver driver)               { driver.navigate().back(); }
    public void navigateForward(WebDriver driver)            { driver.navigate().forward(); }
    public String getTitle(WebDriver driver)                 { return driver.getTitle(); }
    public String getCurrentUrl(WebDriver driver)            { return driver.getCurrentUrl(); }

    // ── ELEMENT ACTIONS ──────────────────────────────────────────────────

    public void click(WebElement ele)                        { ele.click(); }
    public void sendKeys(WebElement ele, String value)       { ele.clear(); ele.sendKeys(value); }
    public String getText(WebElement ele)                    { return ele.getText(); }
    public boolean isDisplayed(WebElement ele)               { return ele.isDisplayed(); }

    // ── WAITS ────────────────────────────────────────────────────────────

    public void implicitWait(WebDriver driver, long sec) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
    }

    public void waitForVisibility(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
            .until(ExpectedConditions.visibilityOf(ele));
    }

    public void waitForClickable(WebDriver driver, WebElement ele, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
            .until(ExpectedConditions.elementToBeClickable(ele));
    }

    public void waitForTitle(WebDriver driver, String title, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
            .until(ExpectedConditions.titleContains(title));
    }

    // ── ACTIONS ──────────────────────────────────────────────────────────

    public void doubleClick(WebDriver driver, WebElement ele) {
        new Actions(driver).doubleClick(ele).perform();
    }

    public void rightClick(WebDriver driver, WebElement ele) {
        new Actions(driver).contextClick(ele).perform();
    }

    public void moveToElement(WebDriver driver, WebElement ele) {
        new Actions(driver).moveToElement(ele).perform();
    }

    public void dragAndDrop(WebDriver driver, WebElement src, WebElement dest) {
        new Actions(driver).dragAndDrop(src, dest).perform();
    }

    public void scrollToElement(WebDriver driver, WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    // ── ALERTS ───────────────────────────────────────────────────────────

    public void acceptAlert(WebDriver driver)                { driver.switchTo().alert().accept(); }
    public void dismissAlert(WebDriver driver)               { driver.switchTo().alert().dismiss(); }
    public String getAlertText(WebDriver driver)             { return driver.switchTo().alert().getText(); }
    public void sendAlertText(WebDriver driver, String msg)  { driver.switchTo().alert().sendKeys(msg); }

    // ── FRAMES ───────────────────────────────────────────────────────────

    public void switchFrameByIndex(WebDriver driver, int i)       { driver.switchTo().frame(i); }
    public void switchFrameByName(WebDriver driver, String name)  { driver.switchTo().frame(name); }
    public void switchFrameByElement(WebDriver driver, WebElement e) { driver.switchTo().frame(e); }
    public void switchToDefault(WebDriver driver)                  { driver.switchTo().defaultContent(); }

    // ── WINDOWS ──────────────────────────────────────────────────────────

    public void switchToWindowByTitle(WebDriver driver, String title) {
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
            if (driver.getTitle().contains(title)) break;
        }
    }

    // ── SCREENSHOT ───────────────────────────────────────────────────────

    /**
     * Takes a screenshot and saves it under ScreenShot/<name>.png.
     * Returns the ABSOLUTE path so ExtentReports can find it
     * regardless of which directory the thread's JVM is in.
     */
    public String takeScreenshot(WebDriver driver, String name) {

        String safeName = name.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        String filename = safeName + "_T" + Thread.currentThread().getId();

        String dir = System.getProperty("user.dir") + "/ScreenShot/";
        new File(dir).mkdirs();

        String path = dir + filename + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File(path));
            System.out.println("[Screenshot] Saved: " + path);
        } catch (Exception e) {
            System.err.println("[Screenshot] Failed: " + e.getMessage());
        }

        return path;
    }

    // ── EXCEL ────────────────────────────────────────────────────────────

    public String getExcelData(String path, String sheet, int row, int col) {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            return wb.getSheet(sheet).getRow(row).getCell(col).toString();
        } catch (Exception e) {
            System.err.println("[Excel] Read failed at row=" + row + " col=" + col + ": " + e.getMessage());
            return "";
        }
    }

    // ── PROPERTIES ───────────────────────────────────────────────────────

    public String getPropertyValue(String key) {
        try (FileInputStream fs = new FileInputStream(
                "./src/test/resources/Reader/commonData.properties")) {
            Properties prop = new Properties();
            prop.load(fs);
            return prop.getProperty(key);
        } catch (Exception e) {
            return "";
        }
    }

    // ── JAVA UTILITIES ───────────────────────────────────────────────────

    public int getRandomNumber()  { return new Random().nextInt(1000); }
    public String getCurrentDate(){ return new SimpleDateFormat("dd-MM-yyyy").format(new Date()); }

    // ── EXTENT REPORT HELPERS ─────────────────────────────────────────────
    //
    // DESIGN: Report state (ExtentTest node) is stored in BaseClass.test (ThreadLocal).
    //         These helpers just read that ThreadLocal — they are stateless themselves.
    //         ReportManager holds the single ExtentReports instance.

    /**
     * Create a new test node in the report for the current scenario.
     * Called from Hook.@Before — once per scenario per thread.
     */
    public void createTest(String name) {
        // ReportManager.getReport() returns the single shared ExtentReports instance
        ExtentTest node = ReportManager.getReport().createTest(name);
        BaseClass.test.set(node);                               // store in THIS thread's ThreadLocal
    }

    /**
     * Log a PASS step on the current thread's ExtentTest node.
     */
    public void pass(String msg) {
        ExtentTest node = BaseClass.test.get();
        if (node != null) node.pass(msg);
    }

    /**
     * Log a FAIL step on the current thread's ExtentTest node.
     */
    public void fail(String msg) {
        ExtentTest node = BaseClass.test.get();
        if (node != null) node.fail(msg);
    }

    /**
     * Capture a screenshot on failure and attach it to the report.
     * Uses absolute path to avoid path-resolution issues across threads.
     */
    public void captureFailure(WebDriver driver, String testName) {
        try {
            String absolutePath = takeScreenshot(driver, testName); // already returns absolute path
            ExtentTest node = BaseClass.test.get();
            if (node != null) {
                node.fail("Test Failed: " + testName);
                node.addScreenCaptureFromPath(absolutePath);    // absolute path always works
            }
            System.out.println("[Report] Screenshot attached: " + absolutePath);
        } catch (Exception e) {
            System.err.println("[Report] captureFailure error: " + e.getMessage());
        }
    }

    
}
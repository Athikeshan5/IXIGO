package com.ixigo.testing.utilities;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;

    // Launch browser
    public void initializeDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Open URL
    public void openUrl(String url) {
        driver.get(url);

        try {
            Thread.sleep(4000); // basic wait (can improve later)
        } catch (Exception e) {
        }
    }

    // Screenshot method (for Extent Report)
    public String captureScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            // create Reports folder if not exists
            File folder = new File("Reports/");
            if (!folder.exists()) {
                folder.mkdir();
            }

            String path = "Reports/" + name + ".png";
            File dest = new File(path);

            FileUtils.copyFile(src, dest);

            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Close browser
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
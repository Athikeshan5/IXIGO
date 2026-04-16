package com.ixigo.testing.utilities;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
            Thread.sleep(4000); // wait for page load 
        } catch (Exception e) {
        }
    }

    // Close browser
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
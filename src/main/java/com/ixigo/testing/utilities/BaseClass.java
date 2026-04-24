package com.ixigo.testing.utilities;

import java.util.concurrent.ConcurrentHashMap;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentTest;

public class BaseClass {

    private static final ThreadLocal<WebDriver>  driver    = new ThreadLocal<>();
    public  static final ThreadLocal<ExtentTest> test      = new ThreadLocal<>();

    // Keyed by scenario ID — survives after @After, available for listener
    public static final ConcurrentHashMap<String, WebDriver> driverStore
            = new ConcurrentHashMap<>();

    public void setDriver(WebDriver d) {
        driver.set(d);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void unload() {
        driver.remove();
        test.remove();
    }
}
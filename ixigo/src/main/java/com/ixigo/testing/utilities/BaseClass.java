package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentTest;

public class BaseClass {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void unload() {
        driver.remove();
    }
}
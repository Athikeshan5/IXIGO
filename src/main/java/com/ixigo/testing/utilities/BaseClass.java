package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

public class BaseClass {
	
	//parallel execution 
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    // Set driver
    public  void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Get driver
    public  WebDriver getDriver() {
        return driver.get();
    }

    // Remove driver 
    public  void unload() {
        driver.remove();
    }
}

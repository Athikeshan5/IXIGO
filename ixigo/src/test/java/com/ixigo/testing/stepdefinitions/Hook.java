package com.ixigo.testing.stepdefinitions;

import com.aventstack.extentreports.*;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.ExtentManager;

import io.cucumber.java.*;

public class Hook extends BaseClass {

    static ExtentReports extent = ExtentManager.getInstance();
    static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // BEFORE SCENARIO
    @Before
    public void setUp(Scenario scenario) {
        initializeDriver();

        // Create test in Extent Report 
        ExtentTest extentTest = extent.createTest(scenario.getName());
        test.set(extentTest);
    }

    // AFTER SCENARIO
    @After
    public void tearDownScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            try {
                String path = captureScreenshot("fail_" + System.currentTimeMillis());
                if (path != null) {
                    test.get().fail("Failed").addScreenCaptureFromPath(path);
                }
            } catch (Exception e) {
                System.out.println("Screenshot failed");
            }
        } else {
            test.get().pass("Passed");
        }

        extent.flush();

        if (driver != null) {
            driver.quit(); // ALWAYS CLOSE CLEANLY
        }
    }
}
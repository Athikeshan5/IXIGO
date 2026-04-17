package com.ixigo.testing.stepdefinition;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

public class Hook extends AllUtilityFunctions{

    public BaseClass b;

    public Hook(BaseClass b) {
        this.b = b;
    }

    @Before
    public void loginapp(Scenario scenario) throws IOException {

        //  Create test in Extent Report (IMPORTANT)
        createTest(scenario.getName());

        String browser = AllUtilityFunctions.getPropertyValue("browser");

        //  Launch browser
        if (browser.equalsIgnoreCase("chrome")) {
            b.driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            b.driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            b.driver = new FirefoxDriver();
        }

        //  Initialize pages
        Pages.allPages(b.driver);

        //  Open application
        AllUtilityFunctions.openUrl(b.driver, AllUtilityFunctions.getPropertyValue("url"));

        //  Maximize browser
        AllUtilityFunctions.maximizeBrowser(b.driver);

        //  Handle popup safely
        AllUtilityFunctions.handlePopup(b.driver, "wiz-iframe-intent", By.id("closeButton"));

        
    }

    

	@After
    public void tearDown(Scenario scenario) {

        String name = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

        if (scenario.isFailed()) {

            //  Failure → screenshot + report
            AllUtilityFunctions.captureFailure(b.driver, name);

        } else {
            //  Pass log
            AllUtilityFunctions.pass("Test Passed: " + name);
        }

        //  Close browser
        //b.driver.quit();

        //  Save report
        AllUtilityFunctions.getReport().flush();
    }
}
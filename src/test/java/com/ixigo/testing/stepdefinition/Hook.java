package com.ixigo.testing.stepdefinition;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;
import com.ixigo.testing.utilities.ReportManager;
public class Hook extends AllUtilityFunctions {

    public BaseClass b;
    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

    public Hook(BaseClass b) {
        this.b = b;
    }

    @Before
    public void loginapp(Scenario scenario) {

        // ✅ FIX: createTest is thread-safe — stores in ThreadLocal<ExtentTest>
        createTest(scenario.getName());

        String browser = allUtilityFunctions.getPropertyValue("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            b.setDriver(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            b.setDriver(new EdgeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            b.setDriver(new FirefoxDriver());
        }

        Pages.allPages(b.getDriver());

        allUtilityFunctions.openUrl(b.getDriver(), allUtilityFunctions.getPropertyValue("url"));
        allUtilityFunctions.maximizeBrowser(b.getDriver());
    }

    @After
    public void tearDown(Scenario scenario) {

        String name = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

        if (scenario.isFailed()) {
            allUtilityFunctions.captureFailure(b.getDriver(), name);
        } else {
            allUtilityFunctions.pass("Test Passed: " + name);
        }

        b.getDriver().quit();
        b.unload(); 

         
        //         It's called once at suite end in ReportManager.java (@AfterSuite)
        //         Calling flush() from multiple threads caused report corruption.
    }
}
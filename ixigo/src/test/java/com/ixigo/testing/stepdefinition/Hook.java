package com.ixigo.testing.stepdefinition;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.CookieManager;
import com.ixigo.testing.utilities.Pages;


public class Hook extends AllUtilityFunctions {

    public BaseClass b;
    AllUtilityFunctions util = new AllUtilityFunctions();

    // Mobile number read from commonData.properties (mobileNumber=XXXXXXXXXX)
    private final String MOBILE = util.getPropertyValue("mobileNumber");

    public Hook(BaseClass b) {
        this.b = b;
    }

    @Before
    public void setUp(Scenario scenario) {

        // Create a named test entry in Extent Report for this scenario
        createTest(scenario.getName());

        // Auto-create output directories
        new File("ScreenShot").mkdirs();
        new File("Reports").mkdirs();

        // Launch browser
        String browser = util.getPropertyValue("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            b.setDriver(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            b.setDriver(new EdgeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            b.setDriver(new FirefoxDriver());
        } else {
            throw new RuntimeException("Invalid browser in commonData.properties: " + browser);
        }

        WebDriver driver = b.getDriver();

        // Set page load timeout — prevents HttpTimeoutException during OTP waits
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        // Initialize page objects for this thread
        Pages.allPages(driver);

        // Navigate to ixigo trains page
        String url = util.getPropertyValue("url");
        try {
            util.openUrl(driver, url);
        } catch (Exception e) {
            System.out.println("URL load timeout — retrying: " + e.getMessage());
            try {
                Thread.sleep(3000);
                util.openUrl(driver, url);
            } catch (Exception e2) {
                System.out.println("Retry failed: " + e2.getMessage());
            }
        }

        // Maximize browser
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("Maximize skipped: " + e.getMessage());
        }

        // Dismiss wiz-iframe popup (blocks clicks if not dismissed)
        dismissWizPopup(driver);

        // LOGIN via cookies or manual OTP (one-time per machine)
        // Food module doesn't need login — skip for it
        String scenarioName = scenario.getName().toLowerCase();
        if (!scenarioName.contains("food")) {
            CookieManager cookieManager = new CookieManager(driver);
            cookieManager.loginWithCookiesOrManual(MOBILE);

            // After cookie login, navigate back to the correct starting URL
            try {
                util.openUrl(driver, url);
                Thread.sleep(2000);
                dismissWizPopup(driver);
            } catch (Exception e) {
                System.out.println("Post-login navigation: " + e.getMessage());
            }
        }
    }

    /**
     * Dismisses the full-screen wiz-iframe-intent popup that ixigo shows on load.
     * Must switch into the iframe, click closeButton, then switch back.
     */
    private void dismissWizPopup(WebDriver driver) {
        try {
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(8));
            w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("wiz-iframe-intent"));
            WebElement closeBtn = w.until(
                ExpectedConditions.elementToBeClickable(By.id("closeButton")));
            closeBtn.click();
            driver.switchTo().defaultContent();
            System.out.println("wiz-iframe popup dismissed");
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.out.println("No popup present");
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        String testName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

        // Log result to Extent Report
        try {
            if (scenario.isFailed()) {
                util.captureFailure(b.getDriver(), testName);
            } else {
                util.pass("Test Passed: " + testName);
            }
        } catch (Exception e) {
            System.out.println("Reporting skipped: " + e.getMessage());
        }

        // Quit browser and clean up ThreadLocal
        try {
            if (b.getDriver() != null) b.getDriver().quit();
        } catch (Exception e) {
            System.out.println("Driver quit skipped: " + e.getMessage());
        }

        b.unload();
        // Extent Report flushed by ReportManager.onFinish() — NOT here (parallel-safe)
    }
}

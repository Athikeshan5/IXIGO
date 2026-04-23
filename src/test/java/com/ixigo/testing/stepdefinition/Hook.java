package com.ixigo.testing.stepdefinition;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import com.ixigo.testing.utilities.*;

public class Hook {

    public BaseClass b;
    AllUtilityFunctions util = new AllUtilityFunctions();

    public Hook(BaseClass b) {
        this.b = b;
    }

    @Before
    public void setup(Scenario scenario) throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        b.setDriver(new ChromeDriver(options));

        // ✅ Store driver by scenario ID — listener reads this AFTER @After
        BaseClass.driverStore.put(scenario.getId(), b.getDriver());
        System.out.println("[Hook] Driver stored: " + scenario.getId());

        util.maximizeBrowser(b.getDriver());

        String url = util.getPropertyValue("url");
        util.openUrl(b.getDriver(), url);

        SessionManager.manageSession(b.getDriver());
        Pages.allPages(b.getDriver());

        WebDriverWait wait = new WebDriverWait(b.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")))
            .sendKeys(Keys.ESCAPE);
    }

    @After
    public void tearDown(Scenario scenario) {

        String name = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

        if (scenario.isFailed()) {
            util.fail("Test Failed: " + name);
        } else {
            util.pass("Test Passed: " + name);
        }

        // ✅ DO NOT quit here — listener fires after @After and needs the driver
        // ✅ DO NOT remove from driverStore — listener removes it after screenshot
        // Just clear the ThreadLocal
        b.unload();
    }
}
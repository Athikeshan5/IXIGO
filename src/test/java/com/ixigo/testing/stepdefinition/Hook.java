package com.ixigo.testing.stepdefinition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class Hook extends AllUtilityFunctions {

    private static final String COOKIE_FILE = "target/cookies.json";
    private static final Object LOCK = new Object();
    private static volatile boolean isLoggedIn = false;

    public BaseClass b;
    AllUtilityFunctions util = new AllUtilityFunctions();

    public Hook(BaseClass b) {
        this.b = b;
    }

    @Before
    public void setUp(Scenario scenario) throws Exception {

        ChromeDriver driver = new ChromeDriver(buildOptions());
        b.setDriver(driver);

        driver.get(util.getPropertyValue("url"));

        // Wait for page load (Cloudflare / UI ready)
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[normalize-space()='Log in/Sign up']")));

        System.out.println(">>> Site loaded: " + scenario.getName());

        // 🔥 STEP 1: One-time login (only one thread allowed)
        synchronized (LOCK) {

            if (!new File(COOKIE_FILE).exists() && !isLoggedIn) {

                System.out.println(">>> FIRST THREAD: Performing login...");

                Pages.allPages(driver);
                Pages.HBP.get().clicklogin(driver);
                Pages.HBP.get().price(driver);

                WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(180));

                System.out.println(">>> Waiting for OTP...");
                longWait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//h5[contains(text(),'Verify Your Mobile Number')]")));

                Thread.sleep(3000);

                // Create folder if not exists
                new File("target").mkdirs();

                // Save cookies
                try (Writer w = new FileWriter(COOKIE_FILE)) {
                    new Gson().toJson(driver.manage().getCookies(), w);
                }

                isLoggedIn = true;

                System.out.println(">>> Cookies saved successfully!");
            }
        }

        // 🔴 STEP 2: WAIT until cookies file is available
        File cookieFile = new File(COOKIE_FILE);
        int waitTime = 0;

        while (!cookieFile.exists()) {
            Thread.sleep(1000);
            waitTime++;

            if (waitTime > 180) {
                throw new RuntimeException("❌ Cookies not generated within timeout!");
            }
        }

        System.out.println(">>> Cookies ready. Proceeding...");

        // 🔥 STEP 3: Inject cookies into browser
        try (Reader r = new FileReader(COOKIE_FILE)) {

            Set<Cookie> cookies = new Gson().fromJson(
                    r, new TypeToken<Set<Cookie>>() {}.getType());

            for (Cookie c : cookies) {
                try {
                    driver.manage().addCookie(c);
                } catch (Exception ignored) {
                }
            }
        }

        // Refresh to apply session
        driver.navigate().refresh();
        Thread.sleep(3000);

        // Initialize page objects
        Pages.allPages(driver);

        System.out.println(">>> Ready: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {

        String name = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

        if (scenario.isFailed()) {
            util.captureFailure(b.getDriver(), name);
        } else {
            util.pass("Test Passed: " + name);
        }

        b.getDriver().quit();
        b.unload();
    }

    private ChromeOptions buildOptions() {

        ChromeOptions op = new ChromeOptions();

        String uniqueProfile = "C:\\selenium-profile-" + UUID.randomUUID();

        op.addArguments("--user-data-dir=" + uniqueProfile);
        op.addArguments("--start-maximized");
        op.addArguments("--disable-blink-features=AutomationControlled");
        op.addArguments("--disable-dev-shm-usage");
        op.addArguments("--no-sandbox");
        op.addArguments("--disable-gpu");

        op.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        op.setExperimentalOption("useAutomationExtension", false);

        return op;
    }
}
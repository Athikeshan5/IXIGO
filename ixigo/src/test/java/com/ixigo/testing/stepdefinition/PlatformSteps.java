package com.ixigo.testing.stepdefinition;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.*;


public class PlatformSteps {

    public BaseClass b;

    public PlatformSteps(BaseClass b) {
        this.b = b;
    }

    @Given("User is on platform locator page")
    public void user_on_platform_page() {
        log(Status.INFO, "Given: Navigating to Platform Locator page");
        Pages.platformPage.get().navigateToPlatformLocator(b.getDriver());
        log(Status.PASS, "Given PASSED: Platform Locator page loaded");
    }

    @When("User enters train number {string}")
    public void user_enters_train(String train) {
        log(Status.INFO, "When: Entering train number: " + train);
        Pages.platformPage.get().enterTrain(b.getDriver(), train);
        log(Status.PASS, "When PASSED: Train selected — " + train);
    }

    @When("User clicks search platform")
    public void user_clicks_search_platform() {
        log(Status.INFO, "When: Clicking Search Platform button");
        Pages.platformPage.get().clickSearch(b.getDriver());
        log(Status.PASS, "When PASSED: Search Platform clicked");
    }

    @When("User completes booking flow from platform")
    public void user_completes_booking_flow() {
        log(Status.INFO, "When: Completing booking flow from Platform page");
        Pages.platformPage.get().handleBookingFlow(b.getDriver());
        log(Status.PASS, "When PASSED: Platform booking flow completed");
    }

    // @Then "User should see login popup" is in CommonSteps to avoid duplicate

    private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}

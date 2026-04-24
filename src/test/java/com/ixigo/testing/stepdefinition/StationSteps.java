package com.ixigo.testing.stepdefinition;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.*;

/**
 * StationSteps
 * Step definitions for Search by Station module.
 * Every step logs to Extent Report for detailed reporting.
 */
public class StationSteps {

    public BaseClass b;

    public StationSteps(BaseClass b) {
        this.b = b;
    }

    @Given("User is on train page")
    public void user_on_train_page() {
        log(Status.INFO, "Given: Initializing Station page on train page");
        log(Status.PASS, "Given PASSED: Station page object initialized");
    }

    @When("User navigates to station module")
    public void user_navigates_to_station_module() {
        log(Status.INFO, "When: Navigating to Search by Station module");
        Pages.stationPage.get().goToStationModule(b.getDriver());
        log(Status.PASS, "When PASSED: Station module opened");
    }

    @When("User enters station code {string}")
    public void user_enters_station_code(String station) throws InterruptedException {
        log(Status.INFO, "When: Entering station code: " + station);
        Pages.stationPage.get().enterStation(b.getDriver(), station);
        log(Status.PASS, "When PASSED: Station code entered — " + station);
    }

    @When("User clicks search")
    public void user_clicks_search() {
        log(Status.INFO, "When: Clicking Search button");
        Pages.stationPage.get().clickSearch(b.getDriver());
        log(Status.PASS, "When PASSED: Search clicked");
    }

    @When("User clicks book now")
    public void user_clicks_book_now() {
        log(Status.INFO, "When: Clicking Book Now on station result");
        Pages.stationPage.get().clickBookNow(b.getDriver());
        log(Status.PASS, "When PASSED: Book Now clicked");
    }

    @When("User selects AVL and books ticket")
    public void user_selects_avl_and_books() {
        log(Status.INFO, "When: Selecting AVL date and completing booking flow");
        Pages.stationPage.get().selectAVLAndBook(b.getDriver());
        log(Status.PASS, "When PASSED: Station booking flow completed");
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

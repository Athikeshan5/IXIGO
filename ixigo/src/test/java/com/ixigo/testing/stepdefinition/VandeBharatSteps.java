package com.ixigo.testing.stepdefinition;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.*;


public class VandeBharatSteps {

    public BaseClass b;

    public VandeBharatSteps(BaseClass b) {
        this.b = b;
    }

    @Given("User is on Vande Bharat page")
    public void open_vande_page() {
        log(Status.INFO, "Given: Navigating to Vande Bharat page");
        Pages.vandePage.get().openVandeBharatPage(b.getDriver());
        log(Status.PASS, "Given PASSED: Vande Bharat page loaded");
    }

    @When("User enters from station {string}")
    public void enter_from(String from) {
        log(Status.INFO, "When: Entering departure station: " + from);
        Pages.vandePage.get().enterFrom(b.getDriver(), from);
        log(Status.PASS, "When PASSED: From station entered — " + from);
    }

    @When("User enters to station {string}")
    public void enter_to(String to) {
        log(Status.INFO, "When: Entering destination station: " + to);
        Pages.vandePage.get().enterTo(b.getDriver(), to);
        log(Status.PASS, "When PASSED: To station entered — " + to);
    }

    @When("User selects journey date")
    public void select_date() {
        log(Status.INFO, "When: Selecting journey date (last available month, day 18)");
        Pages.vandePage.get().selectDate(b.getDriver());
        log(Status.PASS, "When PASSED: Journey date selected");
    }

    @When("User clicks search trains")
    public void click_search() {
        log(Status.INFO, "When: Clicking Search trains button");
        Pages.vandePage.get().clickSearch(b.getDriver());
        log(Status.PASS, "When PASSED: Search trains clicked");
    }

    @When("User selects AVL train and books")
    public void select_and_book() {
        log(Status.INFO, "When: Selecting AVL class and clicking BOOK");
        Pages.vandePage.get().selectAVLAndBook(b.getDriver());
        log(Status.PASS, "When PASSED: Vande Bharat booking flow completed");
    }

    // @Then "Login popup should be displayed" is in CommonSteps to avoid duplicate

    private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}

package com.ixigo.testing.stepdefinition;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.*;
import org.testng.Assert;


public class FoodSteps {

    public BaseClass b;

    public FoodSteps(BaseClass b) {
        this.b = b;
    }

    @Given("User is on food delivery page")
    public void open_food_page() {
        log(Status.INFO, "Given: Navigating to food delivery page");
        Pages.foodPage.get().openFoodPageFromHome(b.getDriver());
        log(Status.PASS, "Given PASSED: Food delivery page opened");
    }

    @When("User enters PNR number {string}")
    public void enter_pnr(String pnr) {
        log(Status.INFO, "When: Entering PNR number: " + pnr);
        Pages.foodPage.get().enterPNR(b.getDriver(), pnr);
        log(Status.PASS, "When PASSED: PNR entered — " + pnr);
    }

    @When("User clicks search food")
    public void click_search_food() {
        log(Status.INFO, "When: Clicking search food button");
        Pages.foodPage.get().clickSearch(b.getDriver());
        log(Status.PASS, "When PASSED: Search food clicked");
    }

    @Then("User should see food results page")
    public void verify_food_page() {
        log(Status.INFO, "Then: Verifying food results page");
        Pages.foodPage.get().handleErrorPopup(b.getDriver());
        boolean result = Pages.foodPage.get().isResultDisplayed(b.getDriver());
        Assert.assertTrue(result, "Food results page not displayed");
        log(Status.PASS, "Then PASSED: Food results page verified ✅");
    }

    private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}

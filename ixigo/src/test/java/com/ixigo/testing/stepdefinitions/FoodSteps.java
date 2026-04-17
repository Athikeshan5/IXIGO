package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.pages.IxigoFoodPage;
import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class FoodSteps extends BaseClass {

    IxigoFoodPage page;

    @Given("User is on food delivery page")
    public void open_food_page() {
        initializeDriver();
        openUrl("https://www.ixigo.com/order-food-in-train/");
        page = new IxigoFoodPage(driver);
    }

    @When("User enters PNR number {string}")
    public void enter_pnr(String pnr) {
        page.enterPNR(pnr);
    }

    @When("User clicks search food")
    public void click_search_food() {
        page.clickSearch();
    }

    @Then("User should see food results page")
    public void verify_food_page() {
        Assert.assertTrue(page.isResultDisplayed());
        tearDown();
    }
}
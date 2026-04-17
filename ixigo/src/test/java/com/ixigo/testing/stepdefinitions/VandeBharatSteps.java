package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.pages.IxigoVandeBharatPage;
import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class VandeBharatSteps extends BaseClass {

    IxigoVandeBharatPage page;

    @Given("User is on Vande Bharat page")
    public void open_vande_page() {
        initializeDriver();
        openUrl("https://www.ixigo.com/trains/vande-bharat-express-trains");
        page = new IxigoVandeBharatPage(driver);
    }

    @When("User enters from station {string}")
    public void enter_from(String from) {
        page.enterFrom(from);
    }

    @When("User enters to station {string}")
    public void enter_to(String to) {
        page.enterTo(to);
    }
    
    @When("User changes journey date to {string}")
    public void change_date(String date) {
        page.changeDateInURL(date);
    }

//    @When("User selects journey date")
//    public void select_date() {
//        page.selectDate();
//    }

    @When("User clicks search trains")
    public void click_search() {
        page.clickSearch();
    }

    @When("User selects available train and books")
    public void select_and_book() {
        page.selectTrainAndBook();
    }

    @Then("User should reach payment page from Vande Bharat module")
    public void verify_payment() {
        Assert.assertTrue(page.isPaymentPageDisplayed());
        tearDown();
    }
}
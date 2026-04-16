package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.pages.IxigoStationPage;
import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.testng.Assert;

public class StationSteps extends BaseClass {

    IxigoStationPage page;

    // Open Station Module URL
    @Given("User is on station search page")
    public void user_on_station_page() {
        initializeDriver();
        openUrl("https://www.ixigo.com/train-stations");

        page = new IxigoStationPage(driver);
    }

    //  Enter Station
    @When("User enters station name {string}")
    public void user_enters_station(String station) {
        page.enterStation(station);
        
    }

    @When("User clicks search button")
    public void user_clicks_search() {
        page.clickSearch();
    }

    // Booking Flow
    @When("User clicks Book Now and proceeds")
    public void user_clicks_book_now() {
        page.clickBookNow();
    }

    @When("User selects available date and books")
    public void user_selects_date_and_books() {
        page.selectDate();
        page.clickBook();
    }

    // Validation
    @Then("User should reach payment page from station module")
    public void verify_payment_station() {
        Assert.assertTrue(page.isPaymentPageDisplayed(), "Payment page not displayed");
        tearDown();
    }
}
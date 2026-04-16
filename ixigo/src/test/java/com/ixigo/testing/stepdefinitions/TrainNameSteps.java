package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.pages.IxigoTrainNamePage;
import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.testng.Assert;

public class TrainNameSteps extends BaseClass {

    IxigoTrainNamePage page;

    // Open Module URL
    @Given("User is on train name search page")
    public void user_on_train_name_page() {
        initializeDriver();
        openUrl("https://www.ixigo.com/trains/name-number");

        page = new IxigoTrainNamePage(driver);
    }

    // Enter Train Name
    @When("User enters train name {string}")
    public void user_enters_train_name(String train) {
        page.enterTrainName(train);
    }

    // Select Train
    @When("User selects train from list")
    public void user_selects_train() {
        page.selectTrain();
    }

    // Calendar Flow
    @When("User opens calendar and selects available date")
    public void user_selects_date() {
        page.openCalendar();
        page.selectAvailableDate();
    }

    // Booking
    @When("User clicks on book")
    public void user_clicks_book() {
        page.clickBook();
    }

    // Validation
    @Then("User should reach payment page")
    public void verify_payment_page() {
        Assert.assertTrue(page.isPaymentPageDisplayed(), "Payment page not displayed");
        tearDown();
    }
}
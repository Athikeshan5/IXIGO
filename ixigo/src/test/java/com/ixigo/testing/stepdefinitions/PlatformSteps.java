package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.pages.IxigoPlatformPage;
import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class PlatformSteps extends BaseClass {

    IxigoPlatformPage page;

    @Given("User is on platform locator page")
    public void user_on_platform_page() {
        initializeDriver();
        openUrl("https://www.ixigo.com/trains/platform-locator");

        page = new IxigoPlatformPage(driver);
    }

    @When("User enters train number {string}")
    public void user_enters_train(String train) {
        page.enterTrain(train);
    }

    @When("User clicks search platform")
    public void user_clicks_search_platform() {
        page.clickSearch();
    }

    @When("User clicks book from platform")
    public void user_clicks_book_platform() {
        page.clickBookFromPlatform();
        page.selectAvailabilityAndBook(); 
    }

    @Then("User should reach payment page from platform module")
    public void verify_payment_platform() {
        Assert.assertTrue(page.isPaymentPageDisplayed(), "Payment page not displayed");
        tearDown();
    }
}
package com.ixigo.testing.stepdefinition;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HotelSortingByPrice {

    public BaseClass b;

    public HotelSortingByPrice(BaseClass b) {
        this.b = b;
    }

    // ✅ STEP 1: Enter destination + select suggestion
    @Given("user enters destination {string} on the search bar")
    public void user_enters_destination_on_the_search_bar(String destination) throws InterruptedException {

        Pages.WL.get().clickHotelsTab();
        Thread.sleep(2000);

        Pages.HSP.get().enterDestination(destination);
        Pages.HSP.get().selectFirstSuggestion();
    }

    // ❌ REMOVE THIS STEP (WRONG PLACE)
    // @Given("click on search button")

    // ✅ STEP 2: Select check-in
    @When("user selects check-in date")
    public void user_selects_check_in_date() {
        Pages.HBP.get().clickCheckInDate("May", "21");
    }

    // ✅ STEP 3: Select check-out
    @When("select check-out date")
    public void select_check_out_date() {
        Pages.HBP.get().clickCheckInDate("May", "25");
    }

    // ✅ STEP 4: Guests + FINAL SEARCH (ONLY ONCE)
    @When("select number of rooms and guests")
    public void select_number_of_rooms_and_guests() {

        Pages.HBP.get().booking("2", "3", "0");

        // ✅ FINAL SEARCH HERE ONLY
        Pages.HBP.get().clickSearch();

        Pages.WL.get().closeThePopupIfPresent();
    }

    // ✅ STEP 5: Apply sorting
    @When("inventory is displayed click sortby dropdown {string}")
    public void inventory_is_displayed_click_sortby_dropdown(String option) {

        Pages.HSP.get().waitForHotelsToLoad();

        Pages.WL.get().clickSortDropdown();

        // ⚠️ Fix string mismatch
        if (option.equalsIgnoreCase("Price: Low to High") ||
            option.equalsIgnoreCase("Price Low to High")) {

            Pages.HSP.get().selectLowToHigh();
        }

        // wait after sorting
        Pages.HSP.get().waitForHotelsToLoad();
    }

    // ✅ STEP 6: Validate hotels displayed
    @Then("list of hotels in Chennai should be displayed")
    public void list_of_hotels_in_chennai_should_be_displayed() {

        int count = Pages.HSP.get().getAllHotelPrices().size();

        System.out.println("Total Hotels Displayed: " + count);

        Assert.assertTrue(count > 0, "No hotels displayed!");
    }

    // ✅ STEP 7: Validate sorting
    @Then("the price of first hotel should be less then or equal to the next one")
    public void the_price_of_first_hotel_should_be_less_then_or_equal_to_the_next_one() {

        boolean isSorted = Pages.HSP.get().isSortedPairwise();

        System.out.println("Is Sorted (Pairwise): " + isSorted);

        Assert.assertTrue(isSorted, "Prices are NOT sorted low to high!");
    }
    private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}
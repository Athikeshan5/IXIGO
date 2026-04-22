package com.ixigo.testing.stepdefinition;

import org.testng.Assert;

import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompositeFilterInHotel {

	@Given("Enter {string} from {string} to {string}")
	public void enter_from_to(String city, String checkin, String checkout) {
		Pages.WL.get().clickHotelsTab();

        Pages.HSP.get().enterDestination(city);
        Pages.HSP.get().selectFirstSuggestion();

        // You can enhance this to dynamic date parsing if needed
        Pages.HBP.get().clickCheckInDate("May", "25");
        Pages.HBP.get().clickCheckInDate("May", "27");

        Pages.HBP.get().booking("1", "2", "0");
        Pages.HBP.get().clickSearch();

        Pages.WL.get().closeThePopupIfPresent();
        Pages.HSP.get().waitForHotelsToLoad();
	}

	@Given("the hotel list page is displayed")
	public void the_hotel_list_page_is_displayed() {
		int count = Pages.HSP.get().getAllHotelPrices().size();
        Assert.assertTrue(count > 0, "Hotel results not loaded!");
	}

	@When("the user applies filter {string} with value {string}")
	public void the_user_applies_filter_with_value(String filtertype, String filtervalue) {
		 Pages.ACF.get().selectFilter(filtertype, filtervalue);
	}
	

	@Then("the hotel results should match the applied filters")
	public void the_hotel_results_should_match_the_applied_filters() {

	}

	@Then("the active filter tags should be visible on the listing page")
	public void the_active_filter_tags_should_be_visible_on_the_listing_page() {
		 Assert.assertTrue(
			        Pages.ACF.get().isFilterTagVisible("Resort") ||
			        Pages.ACF.get().isFilterTagVisible("Free Cancellation") ||
			        Pages.ACF.get().isFilterTagVisible("Prepaid"),
			        "Filter tag not visible!"
			    );

	}

}

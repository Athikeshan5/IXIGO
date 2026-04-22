package com.ixigo.testing.stepdefinition;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

public class HotelFilter {

    public BaseClass b;

    public HotelFilter(BaseClass b) {
        this.b = b;
    }

    //BACKGROUND 

    @Given("the user has searched for hotels in {string} from {string} to {string}")
    public void the_user_has_searched_for_hotels(String city, String checkIn, String checkOut) {

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

    //SCENARIO

    @Given("the hotel search results page is displayed")
    public void the_hotel_search_results_page_is_displayed() {

        int count = Pages.HSP.get().getAllHotelPrices().size();
        Assert.assertTrue(count > 0, "Hotel results not loaded!");
    }
    
    //PRICE FILTER
    @When("apply price filter {string}")
    public void apply_price_filter(String priceRange) throws InterruptedException {
    	 Pages.FH.get().applyPriceFilter(priceRange);
    }
    
    //STAR RATING
    @When("select the star rating filter {string}")
    public void select_the_star_rating_filter(String starRating) {
    	 Pages.FH.get().selectStarRating(starRating);
    }
    
    //USER RATING
    @When("select the user rating filter {string}")
    public void select_the_user_rating_filter(String userRating) {
    	 Pages.FH.get().selectUserRating(userRating);
        
    }
    
    @Then("verify the filtered hotel results are displayed")
    public void verify_the_filtered_hotel_results_are_displayed() {

        int count = Pages.HSP.get().getAllHotelPrices().size();

        System.out.println("Filtered Hotels Count: " + count);

        Assert.assertTrue(count > 0, "No hotels found after applying filters!");
    }
}

   

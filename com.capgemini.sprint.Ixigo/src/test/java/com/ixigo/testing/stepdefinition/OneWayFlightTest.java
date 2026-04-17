//package com.capgemini.sprint.stepdefinition;
//
//import org.testng.Assert;
//
//import com.capgemini.sprint.Pages.OneWayFlightPage;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class OneWayFlightTest {
//
//
//	    private final OneWayFlightPage flightPage;
//
//	    public OneWayFlightTest(Hook context) {
//	        this.flightPage = new OneWayFlightPage(context.getDriver());
//	    }
//
//	    @Given("user is on the flight booking page")
//	    public void user_is_on_the_flight_booking_page() {
//	        flightPage.navigateTo("https://www.ixigo.com/flights");
//	        System.out.println("Navigated to Ixigo flight booking page");
//	    }
//
//	    @When("user selects {string} trip type")
//	    public void user_selects_trip_type(String tripType) {
//	        flightPage.selectTripType();
//	        System.out.println("Selected trip type: " + tripType);
//	    }
//
//	    @When("user enters source as {string}")
//	    public void user_enters_source_as(String source) throws InterruptedException {
//	        flightPage.enterSource(source);
//	        System.out.println("Entered source: " + source);
//	    }
//
//	    @When("user enters destination as {string}")
//	    public void user_enters_destination_as(String destination) throws InterruptedException {
//	        flightPage.enterDestination(destination);
//	        System.out.println("Entered destination: " + destination);
//	    }
//
//	    @When("user selects travel date {string}")
//	    public void user_selects_travel_date(String date) throws InterruptedException {
//	        flightPage.selectDate(date);
//	        System.out.println("Selected travel date: " + date);
//	    }
//
//	    @When("user clicks on search button")
//	    public void user_clicks_on_button() {
//	        flightPage.clickSearch();
//	        System.out.println("Clicked search button");
//	    }
//
//	    @Then("user should see available One-Way flights")
//	    public void user_should_see_available_one_way_flights() 
//	                                    throws InterruptedException {
//	        // Wait for page to fully load
//	        Thread.sleep(5000);
//	        
//	        // Print current URL for debugging
//	        System.out.println("Current URL: " + flightPage.getCurrentUrl());
//	        
//	        Assert.assertTrue(flightPage.isFlightResultsDisplayed(), "Flight results not displayed!");
//	        System.out.println("Flight results verified successfully");
//	    }
//	}
package com.ixigo.testing.stepdefinition;





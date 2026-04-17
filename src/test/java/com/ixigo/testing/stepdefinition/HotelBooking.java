package com.ixigo.testing.stepdefinition;

import java.time.Duration;

import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HotelBooking  extends BaseClass{
	
//	@Given("open the browser")
//	public void open_the_browser() {
//		
//	        driver.manage().window().maximize();
//	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//	    
//	}
//	@Given("Navigate to url {string}")
//	public void navigate_to_url(String string) {
//		  driver.get(url);
//	        hotelPage = new HotelBookingPage(driver);
//	}
//	@Given("click the hotel module")
//	public void click_the_hotel_module() {
//	    Pages.HBP.clickHotel();
//	}
	@When("click the search destination field and enter the destination {string}")
	public void click_the_search_destination_field_and_enter_the_destination(String destination) {
		Pages.HBP.enterDestination(destination);
	}
	@When("select check-in date as {int} days from today and check-out date as {int} days from today")
	public void select_check_in_date_as_days_from_today_and_check_out_date_as_days_from_today(Integer checkIn, Integer checkOut) throws InterruptedException {
		Pages.HBP.selectDates(checkIn, checkOut);
	}
	@When("select {string} and {string}")
	public void select_and(String adults, String rooms) {
		 Pages.HBP.openGuestSection();

	        int adultCount = Integer.parseInt(adults);
	        int roomCount = Integer.parseInt(rooms);

	        Pages.HBP.increaseAdult(adultCount);
	        Pages.HBP.increaseRoom(roomCount);
	}
	@When("click the search button")
	public void click_the_search_button() {
	    Pages.HBP.clickSearch();
	}




}

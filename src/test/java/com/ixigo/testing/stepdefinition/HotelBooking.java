package com.ixigo.testing.stepdefinition;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HotelBooking  extends BaseClass{
	
@When("click the search destination field and enter the destination")
public void click_the_search_destination_field_and_enter_the_destination() {

    Pages.HBP.get().enterDestination("chennai");
}
@When("select check-in date and check-out date")
public void select_check_in_date_and_check_out_date() {

    Pages.HBP.get().clickCheckInDate("May", "21");
    Pages.HBP.get().clickCheckInDate("July","25");

}
@When("select rooms and adults")
public void select_rooms_and_adults() {

Pages.HBP.get().booking("2", "3","0");;
}

@When("click the search button")
public void click_the_search_button() {
    Pages.HBP.get().clickSearch();
}


	




}

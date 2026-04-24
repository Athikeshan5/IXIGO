package com.ixigo.testing.stepdefinition;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HotelBooking  extends BaseClass{
	
@When("click the search destination field and enter the destination")
public void click_the_search_destination_field_and_enter_the_destination() {

    Pages.HBP.get().enterDestination("FabHotel Gateway");
}
@When("select check-in date and check-out date")
public void select_check_in_date_and_check_out_date() {

    Pages.HBP.get().clickCheckInDate("May", "21");
    Pages.HBP.get().clickCheckInDate("May","25");

}
@When("select rooms and adults")
public void select_rooms_and_adults() {

Pages.HBP.get().booking("1", "1","0");;
}

@When("click the search button")
public void click_the_search_button() {
    Pages.HBP.get().clickSearch();
}
@When("click the hotel book now")
public void click_the_hotel_book_now() {
    Pages.HBP.get().closePopupIfPresent();
    Pages.LHP.get().hotel("Grand Continent Brookefield - A Sarovar Portico Alliliate Hotel",getDriver());
}
@When("click the reserve button")
public void click_the_reserve_button() {
   
    Pages.RP.get().reserve(getDriver());
    Pages.HBP.get().closePopupIfPresent();
    
}
@When("add the guest details")
public void add_the_guest_details() {
	
}
@When("click the payment")
public void click_the_payment() {
    // Write code here that turns the phrase above into concrete actions
    
}
@Then("verify the payment page")
public void verify_the_payment_page() {
    // Write code here that turns the phrase above into concrete actions
   SoftAssert sa=new SoftAssert();
   String actual=Pages.RP.get().verify(getDriver());
   sa.assertTrue(actual.contains("Reserve"));
}

	
private void log(Status status, String msg) {
    System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
    ExtentTest test = BaseClass.test.get();
    if (test != null) {
        try { test.log(status, msg); } catch (Exception ignored) {}
    }
}



}

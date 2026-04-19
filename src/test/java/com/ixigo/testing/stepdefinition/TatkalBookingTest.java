package com.ixigo.testing.stepdefinition;

import org.testng.asserts.SoftAssert;

import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TatkalBookingTest {

   
    public BaseClass b;

    public TatkalBookingTest(BaseClass b) {
        this.b = b;
    }

    @Given("go to home page click the trains module")
    public void go_to_home_page_click_the_trains_module() {
        Pages.hp.get().clickTrains();
    }

    @Given("go the trains module click the tatkal reservation module")
    public void go_the_trains_module_click_the_tatkal_reservation_module() {
        Pages.tp.get().clicktatkal();
    }

    @When("enter the stations and search {string} {string}")
    public void enter_the_stations_and_search(String string, String string2) {
        
        Pages.tkp.get().enterstation(b.getDriver(), string, string2);
        Pages.tkp.get().getDepartureDate();
        Pages.tkp.get().clicksearch(b.getDriver());
    }

    @When("click the filter {string}")
    public void click_the_filter(String string) {
     
        Pages.ttb.get().bookticket(b.getDriver(), string);
    }

    @When("go to seat section and book the ticket {string}")
    public void go_to_seat_section_and_book_the_ticket(String string) {
        Pages.ttb.get().seattype(b.getDriver());
        Pages.ttb.get().clickbook(b.getDriver());
        Pages.ttb.get().price(b.getDriver(), string);
    }

    @When("enter the passenger details {string} {string}")
    public void enter_the_passenger_details(String string, String string2) {
        Pages.ttb.get().passenger(b.getDriver(), string, string2);
    }

    @Then("verify the tatkal payment page {string}")
    public void verify_the_tatkal_payment_page(String string) {
        String ver = Pages.ttb.get().verifybook(b.getDriver());
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(ver.contains(string));
        System.out.println("tatkal booked");
        sa.assertAll();
    }
}
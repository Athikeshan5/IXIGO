package com.ixigo.testing.stepdefinition;

import org.testng.asserts.SoftAssert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class seatAvailabilityTest {

    public BaseClass b;
    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

    public seatAvailabilityTest(BaseClass b) {
        this.b = b;
    }

    @Given("click the train module from the home")
    public void click_the_train_module_from_the_home() {
        Pages.hp.get().clickTrains();
    }

    @Given("click the seat availability module")
    public void click_the_seat_availability_module() {
        Pages.tp.get().clickseat();
    }

    @When("Enter the station for ticket booking")
    public void enter_the_station_for_ticket_booking() {
       
        Pages.ts.get().enterstation(
            b.getDriver(),
            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 0),
            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 1)
        );
    }

    @When("click the check availability button")
    public void click_the_check_availability_button() {
       
        Pages.ts.get().clickseatcheck(b.getDriver());
    }

    @When("click the filter for booking")
    public void click_the_filter_for_booking() {
       
        Pages.saf.get().bookticket(b.getDriver(), "17");
    }

    @When("click the seat to book")
    public void click_the_seat_to_book() {
        Pages.saf.get().seattype(b.getDriver());
    }

    @When("click the seat person type")
    public void click_the_seat_person_type() {
        Pages.saf.get().clickbook(b.getDriver());
    }

    @When("click the ticket price to book")
    public void click_the_ticket_price_to_book() {
        Pages.saf.get().price(b.getDriver());
    }

    @When("Enter the passenger details")
    public void enter_the_passenger_details() {
        Pages.saf.get().passenger(
            b.getDriver(),
            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 3),
            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 2)
        );
    }

    @Then("verify the payment page")
    public void verify_the_payment_page() {
        String val = Pages.saf.get().verifybook(b.getDriver());
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(val.contains(
            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 4)
        ));
        System.out.println("success");
        sa.assertAll();
    }
}
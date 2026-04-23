package com.ixigo.testing.stepdefinition;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InvalidPassengerTest {
	 public BaseClass b;
	    AllUtilityFunctions  allUtilityFunctions = new AllUtilityFunctions();

	    public InvalidPassengerTest(BaseClass b) {
	        this.b = b;
	    }
	    
	    @Given("click the trains modules")
	    public void click_the_trains_modules() {
	    	 
	        Pages.hp.get().clickTrains(b.getDriver());
	    }

	    @Given("click the seatavailability module")
	    public void click_the_seatavailability_module() {
	        Pages.tp.get().clickseat();
	    }
	    
	        	    
	    @When("Enter the stations for ticket booking")
	    public void enter_the_stations_for_ticket_booking() {
	        // Row 2 in Excel holds invalid/no-service station names
	        Pages.ts.get().enterstation(
	           b .getDriver(),
	           allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 2, 0),
	            allUtilityFunctions.getExcelData("./src/test/resources/Reader/testData.xlsx", "Sheet1", 2, 1));
	    }
	    @When("click the filters for booking {string}")
	    public void click_the_filter_for_booking(String string) {
	       
	        Pages.saf.get().bookticketInvalid(b.getDriver(), string);
	    }

	    @When("click the seats to book")
	    public void click_the_seat_to_book() {
	        Pages.saf.get().seattype(b.getDriver());
	    }

	    @When("click the seats person type")
	    public void click_the_seat_person_type() {
	        Pages.saf.get().clickbook(b.getDriver());
	    }

	   
	 @When("Enter the passenger details with invalid age {string}")
	    public void enter_the_passenger_details_with_invalid_age(String age) {
	        // Name is still read from Excel; age comes from the Examples table
	        String passengerName = allUtilityFunctions.getExcelData(
	            "./src/test/resources/Reader/testData.xlsx", "Sheet1", 1, 3
	        );
	        Pages.saf.get().passengerWithInvalidAge(b.getDriver(), passengerName, age);
	    }

	    @Then("verify the invalid age error message {string}")
	    public void verify_the_invalid_age_error_message(String expectedError) {
	        String actualError = Pages.saf.get().getAgeErrorMessage(b.getDriver());
	        SoftAssert sa = new SoftAssert();
	        sa.assertTrue(actualError.contains("Seat will not be allotted to infant passengers (0-4 years)"));
	        Reporter.log("invalid age");
	        sa.assertAll();
	    }
	    private void log(Status status, String msg) {
	        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
	        ExtentTest test = BaseClass.test.get();
	        if (test != null) {
	            try { test.log(status, msg); } catch (Exception ignored) {}
	        }
	    }
}

package com.ixigo.testing.stepdefinition;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NoTrainAvailableTest {
	public BaseClass b;
    AllUtilityFunctions all = new AllUtilityFunctions();

    public NoTrainAvailableTest(BaseClass b) {
        this.b = b;
    }

    @Given("click the train module from the homes")
    public void click_the_train_module_from_the_home() {
    	
		  
        Pages.hp.get().clickTrains(b.getDriver());
    }

    @Given("click the seat availability modules")
    public void click_the_seat_availability_module() {
        Pages.tp.get().clickseat();
    }

    
	@When("Enter the invalid station for ticket booking")
    public void enter_the_invalid_station_for_ticket_booking() {
        // Row 2 in Excel holds invalid/no-service station names
        Pages.ts.get().enterstationnotrain(b.getDriver(),"Karaikkudi Jn (KKDI)","Anand Vihar Trm (ANVT)");
    }
	@When("click the check availability buttons")
    public void click_the_check_availability_buttons() {
       
        Pages.ts.get().clickseatcheck(b.getDriver());
    }
    @Then("verify the no train available error message")
    public void verify_the_no_train_available_error_message() {
        String actualMessage = Pages.ts.get().getNoTrainMessage(b.getDriver());
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(
            actualMessage.contains("No direct trains available"),
            "Expected 'no train' message but got: [" + actualMessage + "]"
        );
        System.out.println("No train available message verified: " + actualMessage);
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
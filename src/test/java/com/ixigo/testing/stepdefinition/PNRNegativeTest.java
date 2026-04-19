package com.ixigo.testing.stepdefinition;

import org.testng.asserts.SoftAssert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PNRNegativeTest {

    public BaseClass b;
    AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();

    public PNRNegativeTest(BaseClass b) {
        this.b = b;
    }

    @Given("click the pnr sub module")
    public void click_the_pnr_sub_module() {
        Pages.hp.get().clickTrains();
        Pages.tp.get().clickpnr();
    }

    @When("Enter the pnr number {string}")
    public void enter_the_pnr_number(String string) {
        
        Pages.pnrs.get().enterpnr(b.getDriver(), string);
       
    }

    @When("click the check pnr status")
    public void click_the_check_pnr_status() {
    	 Pages.pnrs.get().clickpnr(b.getDriver());
    }

    @Then("verify the pnr message page {string}")
    public void verify_the_pnr_message_page(String Strings) {
       
        boolean me = Pages.pnrv.get().validpnr(b.getDriver(), Strings);
        SoftAssert sa = new SoftAssert();
        System.out.println(allUtilityFunctions.getCurrentUrl(b.getDriver()));
        sa.assertTrue(me);
        System.out.println("verified");
        sa.assertAll();
    }
}
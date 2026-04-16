package com.ixigo.testing.stepdefinition;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PNRNegativeTest  {
	public BaseClass b;
	public PNRNegativeTest(BaseClass b) {
		this.b=b;
	}

@Given("click the pnr sub module")
public void click_the_pnr_sub_module() {
    // Write code here that turns the phrase above into concrete actions
    Pages.hp.clickTrains();
	Pages.tp.clickpnr();
}
@When("Enter the pnr number {string}")
public void enter_the_pnr_number(String string) {
    // Write code here that turns the phrase above into concrete actions
  Pages.pnrs.enterpnr(string);
  Pages.pnrs.clickpnr();
}
@When("click the check pnr status")
public void click_the_check_pnr_status() {
    // Write code here that turns the phrase above into concrete actions
    
}
@Then("verify the pnr message page")
public void verify_the_pnr_message_page() {
    // Write code here that turns the phrase above into concrete actions
   boolean me= Pages.pnrv.validpnr("https://www.ixigo.com/trains/pnr-status");
   SoftAssert sa=new SoftAssert();
   System.out.println(AllUtilityFunctions.getCurrentUrl(b.driver));
   sa.assertTrue(me);
   System.out.println("verified");
   sa.assertAll();
}

}

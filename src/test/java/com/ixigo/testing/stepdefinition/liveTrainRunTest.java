package com.ixigo.testing.stepdefinition;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class liveTrainRunTest extends AllUtilityFunctions {
	public BaseClass b;
	public liveTrainRunTest(BaseClass b) {
		this.b=b;
	}

@Given("click the  train module")
public void click_the_train_module() {
    // Write code here that turns the phrase above into concrete actions
     Pages.hp.clickTrains();
}
@Given("click the live train sub module")
public void click_the_live_train_sub_module() {
    // Write code here that turns the phrase above into concrete actions
     Pages.tp.clickliveruntrain();
}
@When("Click the search train field")
public void click_the_search_train_field() {
    // Write code here that turns the phrase above into concrete actions
	Pages.trs.searchtrainfield();
}
@When("enter the train name")
public void enter_the_train_name() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	Pages.trs.entertrainname("15621 Kyq Anvt Exp Running Status");
	
}
@When("click the check live runing train")
public void click_the_check_live_runing_train() {
    // Write code here that turns the phrase above into concrete actions
    Pages.trs.clickcheck();
}
@Then("verify the live train page")
public void verify_the_live_train_page() {
    // Write code here that turns the phrase above into concrete actions
     Pages.rs.verifyrunningStatusPage("15621 Kyq Anvt Exp Running Status");
}
}

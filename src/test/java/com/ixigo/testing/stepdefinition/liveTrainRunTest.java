package com.ixigo.testing.stepdefinition;

import java.util.List;
import java.util.Map;

import org.testng.asserts.SoftAssert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class liveTrainRunTest  {

    public BaseClass b;

    public liveTrainRunTest(BaseClass b) {
        this.b = b;
    }

    @Given("click the  train module")
    public void click_the_train_module() {
        Pages.hp.get().clickTrains();
    }

    @Given("click the live train sub module")
    public void click_the_live_train_sub_module() {
        Pages.tp.get().clickliveruntrain();
    }

    @When("Click the search train field")
    public void click_the_search_train_field() {
        
        Pages.trs.get().searchtrainfield(b.getDriver());
    }

    @When("enter the train name")
    public void enter_the_train_name(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);
        
        Pages.trs.get().entertrainname(b.getDriver(), row.get("trainname"));
    }

    @When("click the check live runing train")
    public void click_the_check_live_runing_train() {
        // ✅ FIX: pass driver
        Pages.trs.get().clickcheck(b.getDriver());
    }

    @Then("verify the live train page")
    public void verify_the_live_train_page(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);
       
        String ver = Pages.rs.get().verifyrunningStatusPage(b.getDriver());
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(ver.contains(row.get("verifyPage")));
        System.out.println("success");
        sa.assertAll();
    }
}
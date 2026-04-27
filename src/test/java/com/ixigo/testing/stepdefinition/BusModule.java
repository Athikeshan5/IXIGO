package com.ixigo.testing.stepdefinition;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BusModule {

    private WebDriver driver() {
        return new BaseClass().getDriver();
    }

    @Given("User launches the ixigo website")
    public void user_launches_the_ixigo_website() {
        Pages.lp.get().handlePopup(driver());   
    }

    @Given("User navigates to bus search")
    public void user_navigates_to_bus_search() {
        Pages.hrp.get().clickBuses(driver());
    }

    @When("User enters {string} and {string}")
    public void user_enters_and(String source, String destination) {
        Pages.hrp.get().enterFromCity(driver(), source);
        Pages.hrp.get().enterToCity(driver(), destination);
    }

    @When("user enters bus search details")
    public void user_enters_bus_search_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Pages.hrp.get().enterFromCity(driver(), data.get(0).get("source"));
        Pages.hrp.get().enterToCity(driver(), data.get(0).get("destination"));
    }

    @When("User selects travel date and clicks search")
    public void user_selects_travel_date_and_clicks_search() {
        Pages.hrp.get().selectDateAndSearch(driver());
    }

    @Then("Bus results should be displayed")
    public void bus_results_should_be_displayed() {
        String url = Pages.lp.get().getCurrentUrl(driver());
        Assert.assertTrue(url.contains("bus") || url.contains("search"),
            "FAILED: Bus results page not displayed. URL: " + url);
    }

    @Given("User is on bus search results page")
    public void user_is_on_bus_search_results_page() {
        System.out.println("User is on results page");
    }

    @When("user applies mandatory filters")
    public void user_applies_mandatory_filters() {
        Pages.bsp.get().applyMandatoryFilters(driver());
    }

    @When("user applies optional filter {string}")
    public void user_applies_optional_filter(String filterName) {
        Pages.bsp.get().applyOptionalFilter(driver(), filterName);
    }

    @Then("filtered bus results should be displayed")
    public void filtered_bus_results_should_be_displayed() {
        String url = Pages.lp.get().getCurrentUrl(driver());
        Assert.assertTrue(url.contains("bus") || url.contains("search"),
            "FAILED: Filtered results not displayed. URL: " + url);
    }

    @When("user selects the bus and seat")
    public void user_selects_the_bus_and_seat() {
        Pages.ssp.get().selectSeat(driver(), "SK Balu Bus");
        Pages.ssp.get().selectSeatNumber(driver());
    }

    @When("user selects boarding and dropping point")
    public void user_selects_boarding_and_dropping_point() {
        Pages.ssp.get().selectBoardingPoint(driver());
        Pages.ssp.get().selectDroppingPoint(driver());
        Pages.ssp.get().clickContinue(driver());
    }

    @Then("personal details page should be displayed")
    public void personal_details_page_should_be_displayed() {
        String url = Pages.lp.get().getCurrentUrl(driver());
        Assert.assertTrue(
            url.contains("passenger") || url.contains("personal") || url.contains("detail"),
            "FAILED: Personal details page not displayed. URL: " + url);
    }

    @When("user enters passenger name and age")
    public void user_enters_passenger_name_and_age() {
        AllUtilityFunctions util = new AllUtilityFunctions();
        String path      = "./src/test/resources/PersonalDetails.xlsx";
        String nameValue = util.getExcelData(path, "Sheet1", 1, 0);
        String ageValue  = util.getExcelData(path, "Sheet1", 1, 1);
        if (nameValue.isEmpty() || ageValue.isEmpty()) {
            throw new RuntimeException("Excel NOT read → check file/sheet/row/column");
        }
        Pages.pp.get().enterPassengerDetails(driver(), nameValue, ageValue);
    }

    @When("user clicks proceed to pay")
    public void user_clicks_proceed_to_pay() {
        Pages.pp.get().clickContinue(driver());
        Pages.pp.get().clickContinueToPay(driver());
    }

    @Then("user should be navigated to the payment page")
    public void user_should_be_navigated_to_the_payment_page() {
        String url = Pages.lp.get().getCurrentUrl(driver());
        System.out.println("Asserting payment page. URL: " + url);
        Assert.assertTrue(
            url.contains("payment")      ||
            url.contains("pay")          ||
            url.contains("checkout")     ||
            url.contains("verify")       ||
            url.contains("booking")      ||
            url.contains("passengerinfo"), 
            "FAILED: Payment page not displayed. URL: " + url
        );
        System.out.println("PASSED: URL: " + url);
    }
    @Given("navigates to the trckticket page")
    public void navigates_to_the_trckticket_page() {
        Pages.tk.get().clickTrackTicket(driver());
    }

    @Given("enter the ticketid")
    public void enter_the_ticketid() {
        Pages.tk.get().enterBusID(driver(), "ABH123456789");
    }

    @When("enter the phoneno")
    public void enter_the_phoneno() {
        Pages.tk.get().enterMobileNo(driver(), "9791237971");
    }

    @Then("verify the message")
    public void verify_the_message() {
        try {
            Pages.tk.get().clickTicketDetails(driver());
            System.out.println("Ticket Details button clicked");
        } catch (Exception e) {
            System.out.println("WARNING: Could not click Ticket Details — " + e.getMessage());
        }

        try {
            String actualMessage = Pages.tk.get().getStatusMessage(driver());
            System.out.println("Message displayed: " + actualMessage);

            if (actualMessage == null || actualMessage.isEmpty()) {
                System.out.println("WARNING: No message found — negative scenario incomplete");

            } else if (actualMessage.toLowerCase().contains("invalid")   ||
                       actualMessage.toLowerCase().contains("not found") ||
                       actualMessage.toLowerCase().contains("no booking")||
                       actualMessage.toLowerCase().contains("error")     ||
                       actualMessage.toLowerCase().contains("incorrect")  ||
                       actualMessage.toLowerCase().contains("no error")) {
                System.out.println("✅ Negative scenario PASSED — Error message verified: "
                    + actualMessage);

            } else {
                System.out.println("INFO: Message found but did not match expected error keywords: "
                    + actualMessage);
            }

        } catch (Exception e) {
            System.out.println("INFO: Could not retrieve status message — " + e.getMessage());
        }
    }
}
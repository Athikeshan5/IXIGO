package com.ixigo.testing.stepdefinition;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OneWayFlightTest extends AllUtilityFunctions {

    private final BaseClass b;

    public OneWayFlightTest(BaseClass b) {
        this.b = b;
    }

    // ═══════════════════════════════════════════
    // GIVEN
    // ═══════════════════════════════════════════

    @Given("User launches the flight booking application")
    public void user_launches_the_flight_booking_application() {
        log("✅ Ixigo home page is open");
    }

    @Given("user is on the flight booking page")
    public void user_is_on_the_flight_booking_page() {
        log("User is on the flight booking page");
    }

    // ═══════════════════════════════════════════
    // WHEN — STAGE 1: SEARCH
    // ═══════════════════════════════════════════

    @When("user selects {string} trip type")
    public void user_selects_trip_type(String tripType)
                            throws InterruptedException {
        // ✅ Selects One Way on HOME page
        Pages.OFP.selectTripType(tripType);
    }

    @When("User enters flight search details")
    public void enter_flight_search_details(DataTable dataTable)
                            throws InterruptedException {
        List<Map<String, String>> data = dataTable.asMaps(
            String.class, String.class
        );
        Pages.OFP.enterSource(
            data.get(0).get("source")
        );
        Pages.OFP.enterDestination(
            data.get(0).get("destination")
        );
        Pages.OFP.selectDepartureDate(
            data.get(0).get("travelDate")
        );
    }

    @When("User selects traveller details {string} and {string}")
    public void user_selects_traveller_details(
            String travellers, String cabinClass)
            throws InterruptedException {
        // ✅ Selects travellers on HOME page
        Pages.OFP.selectTravellerDetails(
            travellers, cabinClass
        );
    }

    @When("User selects special fare {string}")
    public void user_selects_special_fare(String fareType)
                            throws InterruptedException {
        // ✅ Selects fare on HOME page
        Pages.OFP.selectSpecialFare(fareType);
    }

    @When("user clicks on search button")
    public void user_clicks_on_search_button() {
        // ✅ Clicks Search → navigates to RESULTS page
        Pages.OFP.clickSearch();
        log("Navigating to flight results page...");
    }

    // ═══════════════════════════════════════════
    // THEN — STAGE 1: VERIFY RESULTS
    // ═══════════════════════════════════════════

    @Then("user should see available flights")
    public void user_should_see_available_flights()
                            throws InterruptedException {
        // ✅ Now on RESULTS page — verify
        Assert.assertTrue(
            Pages.OFP.isFlightResultsDisplayed(),
            "Flight results not displayed!"
        );
        log("✅ Flight results page verified");
    }

    // ═══════════════════════════════════════════
    // WHEN — STAGE 2: FILTER
    // ═══════════════════════════════════════════

    @When("User applies filters for airline {string}")
    public void user_applies_filters_for_airline(String airline)
                            throws InterruptedException {
        // ✅ Already on RESULTS page — apply filter
        Pages.FFP.applyAirlineFilter(airline);
    }

    // ═══════════════════════════════════════════
    // THEN — STAGE 2: VERIFY FILTER
    // ═══════════════════════════════════════════

    @Then("Filtered flight results should be displayed correctly for {string}")
    public void filtered_results_displayed(String airline)
                            throws InterruptedException {
        // ✅ Verify filtered results on RESULTS page
        Assert.assertTrue(
            Pages.FFP.isFilteredResultsDisplayed(airline),
            "No filtered results for: " + airline
        );
        int count = Pages.FFP.getFilteredFlightCount(airline);
        Assert.assertTrue(count > 0,
            "Flight count 0 for: " + airline
        );
        log("✅ Filter verified: " + airline
            + " | Count: " + count);
    }

    // ═══════════════════════════════════════════
    // WHEN — STAGE 3: SELECT FLIGHT
    // ═══════════════════════════════════════════

    @When("User selects a flight")
    public void user_selects_a_flight()
                            throws InterruptedException {
        // ✅ On RESULTS page — click first flight card
        // NO URL navigation — continues from filter results
        Pages.FDP.loadFlightResultsPage(getCurrentDate());
        Pages.FDP.selectFirstFlight();
    }

    // ═══════════════════════════════════════════
    // THEN — STAGE 3: VERIFY DETAILS
    // ═══════════════════════════════════════════

    @Then("User should be able to view complete flight details")
    public void user_should_view_complete_flight_details(
                            DataTable dataTable)
                            throws InterruptedException {

        Thread.sleep(2000);

        List<Map<String, String>> rows = dataTable.asMaps(
            String.class, String.class
        );

        for (Map<String, String> row : rows) {
            String field    = row.get("Field");
            String expected = row.get("Value");

            log("Verifying: " + field
                + " | Expected: " + expected);

            switch (field) {

                case "Airline":
                    String airline =
                        Pages.FDP.getAirlineName();
                    log("Actual: " + airline);
                    Assert.assertEquals(airline, expected,
                        "Airline mismatch!");
                    break;

                case "FlightNumber":
                    String no =
                        Pages.FDP.getFlightNumber();
                    log("Actual: " + no);
                    Assert.assertEquals(no, expected,
                        "FlightNumber mismatch!");
                    break;

                case "Departure":
                    String dep =
                        Pages.FDP.getDepartureTime();
                    log("Actual: " + dep);
                    Assert.assertEquals(dep, expected,
                        "Departure mismatch!");
                    break;

                case "DepartureCity":
                    String depCity =
                        Pages.FDP.getDepartureCity();
                    log("Actual: " + depCity);
                    Assert.assertEquals(depCity, expected,
                        "DepartureCity mismatch!");
                    break;

                case "Arrival":
                    String arr =
                        Pages.FDP.getArrivalTime();
                    log("Actual: " + arr);
                    Assert.assertEquals(arr, expected,
                        "Arrival mismatch!");
                    break;

                case "ArrivalCity":
                    String arrCity =
                        Pages.FDP.getArrivalCity();
                    log("Actual: " + arrCity);
                    Assert.assertEquals(arrCity, expected,
                        "ArrivalCity mismatch!");
                    break;

                case "Duration":
                    String dur =
                        Pages.FDP.getDuration();
                    log("Actual: " + dur);
                    Assert.assertEquals(dur, expected,
                        "Duration mismatch!");
                    break;

                case "Type":
                    String type =
                        Pages.FDP.getFlightType();
                    log("Actual: " + type);
                    Assert.assertEquals(type, expected,
                        "Type mismatch!");
                    break;

                case "Price":
                    String price =
                        Pages.FDP.getPrice();
                    log("Actual: " + price);
                    Assert.assertEquals(price, expected,
                        "Price mismatch!");
                    break;

                default:
                    throw new IllegalArgumentException(
                        "Unknown field: " + field
                    );
            }
        }
        log("✅ All flight details verified!");
    }
}
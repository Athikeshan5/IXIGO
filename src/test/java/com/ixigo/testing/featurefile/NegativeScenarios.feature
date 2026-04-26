@NegativeTests
Feature: Negative Testing for ixigo Flight Booking

  Background:
    Given User launch the flight booking application
    And User navigates to login page
    When User login with mobile number "8807125831"

  @NegativeSameCity
  Scenario: Search with same source and destination
    When user select "Round Trip" trip type
    And User enter flight search details
      | source  | destination | departureDate | returnDate  |
      | Chennai | Chennai     | 25-06-2026    | 30-06-2026  |
    And User select traveller details "1 Adult" and "Economy"
    And user click on search button
    Then User should see validation error message for "Source and destination cannot be same"

  @NegativeNoPassengerSelected
  Scenario: Continue without selecting passenger after flight selection
    When user select "Round Trip" trip type
    And User enter flight search details
      | source  | destination | departureDate | returnDate  |
      | Chennai | Delhi       | 25-06-2026    | 30-06-2026  |
    And User select traveller details "1 Adult" and "Economy"
    And user click on search button
    Then user can see available flights for both onward and return journey
    When User select onward flight
    And User select return flight
    And User clicks continue without selecting passenger
    Then User should see validation message
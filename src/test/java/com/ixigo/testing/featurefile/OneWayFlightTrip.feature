@OneWay
Feature: One Way Flight Booking Functionality

Background:
  Given User launch the flight booking application
  And User navigates to login page
  When User login with mobile number "8807125831"

Scenario: Full end to end one way flight booking journey
  When user select "One Way" trip type
  And User enter flight search details for one way
    | source  | destination | departureDate |
    | Chennai | Delhi       | 25-06-2026    |
  And User select traveller details "1 Adult" and "Economy"
  And User select special fare "Regular"
  And user click on search button
  Then user can see available flights
  When User applies filter for airline "IndiGo"
  Then Filtered flight result should be displayed correctly for "IndiGo"
  When User select flight
  And User selects saved traveller "Angel" with last name "A"
  And User selects seat preference
  And User selects meal preference
  And User confirms the add ons and continues
  Then Payment page should be displayed
  And User should see available payment options

Feature: ixigo Flight Tracker Module

  Background:
    Given the user is on the ixigo homepage
    When the user navigates to Flights module
    And the user clicks on Flight Tracker option

  Scenario Outline: Track flight using Excel data
    When the user reads flight data from Excel row "<RowNum>"
    And the user selects airline from Excel data
    And the user enters flight number from Excel data
    And the user submits the flight search
    Then the flight status should be displayed correctly

    Examples:
      | RowNum |
      | 2      |
      | 3      |
      | 4      |
      | 5      |
      | 6      |
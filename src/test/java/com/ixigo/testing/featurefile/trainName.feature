Feature: Train Search by Name or Number

  # This module uses Excel-driven parametrization via Apache POI.
  # Train names are read from: src/test/resources/Reader/TrainTestData.xlsx
  # Each row in the Excel sheet becomes one test run.

  Scenario Outline: Search train and proceed to booking
    Given User is on train name search page
    When User enters train name "<train>"
    And User selects train from list
    And User selects available date
    And User clicks on book
    Then User should see login popup

    # Data is read from Excel file using Apache POI in TrainNameSteps.java
    # ExcelDataProvider reads TrainTestData.xlsx at runtime
    Examples:
      | train            |
      | Pallavan Express |
      | Chennai Express  |


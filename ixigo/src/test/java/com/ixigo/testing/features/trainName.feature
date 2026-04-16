Feature: Train Search by Name or Number

  Scenario Outline: Search train and proceed to booking
    Given User is on train name search page
    When User enters train name "<train>"
    And User selects train from list
    And User opens calendar and selects available date
    And User clicks on book
    Then User should reach payment page

    Examples:
      | train            |
      | Pallavan Express |
      | 12605            |
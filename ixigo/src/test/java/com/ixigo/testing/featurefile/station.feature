Feature: Ixigo Station Booking Flow

  Scenario: Book train using station module and verify login popup

    Given User is on train page
    When User navigates to station module
    And User enters station code "ADI"
    And User clicks search
    And User clicks book now
    And User selects AVL and books ticket
    Then User should see login popup
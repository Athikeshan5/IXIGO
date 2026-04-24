 Feature: Book the available train seat
 
 # ❌ NEGATIVE SCENARIO 2: No train available for specific station
  Scenario: To verify error message when no train available for specific station
    Given click the train module from the homes
    And click the seat availability modules
    When Enter the invalid station for ticket booking
    And click the check availability buttons
    Then verify the no train available error message
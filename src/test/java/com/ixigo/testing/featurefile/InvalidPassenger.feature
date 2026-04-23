 Feature: Book the available train seat
  # ❌ NEGATIVE SCENARIO 1: Invalid passenger age
 
  Scenario Outline: To verify error message for invalid passenger age
    
    Given click the trains modules 
    And click the seatavailability module
    When Enter the stations for ticket booking
    And click the check availability button
    And click the filters for booking "5"
    And click the seats to book
    And click the seats person type
    And Enter the passenger details with invalid age "<age>"
    Then verify the invalid age error message "<expectedError>"

    Examples:
      | age | expectedError                        |
      | 0   |Seat will not be allotted to infant passengers (0-4 years)|
     
     
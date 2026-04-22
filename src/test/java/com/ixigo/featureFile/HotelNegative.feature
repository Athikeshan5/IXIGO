Feature: Hotel Search Error Handling for Invalid and Empty Inputs
 
Background:
    Given the user is on the ixigo hotels home page "https://www.ixigo.com/hotels"

  Scenario Outline: To verify error message for invalid hotel search inputs
    Given click the Hotels module from the home page
    When click the destination input field
    And enter the invalid destination as "<destination>"
    And enter the check-in date as "<checkInDate>"
    And enter the check-out date as "<checkOutDate>"
    And click the Search Hotels button
    Then verify the error message is displayed as "<expectedError>"
      | verifyPage   |
      | Error Prompt |

    Examples:
      | destination | checkInDate | checkOutDate | expectedError                              |
      |             | 25 May 2025 | 27 May 2025  | Please enter a destination                 |
      | XYZABC999   | 25 May 2025 | 27 May 2025  | No hotels found for this destination       |
      | Chennai     |             | 27 May 2025  | Please select a check-in date              |
      | Chennai     | 25 May 2025 |              | Please select a check-out date             |
      | Chennai     | 27 May 2025 | 25 May 2025  | Check-out date must be after check-in date |
      | @@@###!!!   | 25 May 2025 | 27 May 2025  | Invalid destination entered                |
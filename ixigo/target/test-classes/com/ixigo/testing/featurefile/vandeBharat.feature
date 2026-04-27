Feature: Vande Bharat Train Booking

Scenario: Validate booking flow till login popup
Given User is on Vande Bharat page
When User enters from station "ADI"
And User enters to station "MMCT"
And User selects journey date
And User clicks search trains
And User selects AVL train and books
Then Login popup should be displayed

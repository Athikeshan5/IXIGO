Feature: Vande Bharat Train Booking

Scenario: Search and book Vande Bharat train
  Given User is on Vande Bharat page
 When User enters from station "MS"
And User enters to station "NCJ"
And User clicks search trains
And User changes journey date to "15062026"
  And User selects available train and books
  Then User should reach payment page from Vande Bharat module
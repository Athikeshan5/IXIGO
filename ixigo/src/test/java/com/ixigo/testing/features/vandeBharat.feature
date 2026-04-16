Feature: Vande Bharat Train Booking

Scenario: Search and book Vande Bharat train
  Given User is on Vande Bharat page
  When User enters from station "MAS"
  And User enters to station "MCTM"
  #And User selects journey date
  And User clicks search trains
  And User selects available train and books
  Then User should reach payment page from Vande Bharat module
Feature: Train Search by Station

 Scenario Outline: Search trains using station and proceed to booking
  Given User is on station search page
  When User enters station name "<station>"
  And User clicks search button
  And User clicks Book Now and proceeds
  And User selects available date and books
  Then User should reach payment page from station module
    Examples:
      | station  |
      | Thrisur (TCR) |
      | Dadar (DR) |
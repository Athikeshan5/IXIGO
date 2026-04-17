Feature: Flight Booking Functionality

  Background:
    Given User launches the flight booking application

  Scenario Outline: Search flights (One-Way and Round-Trip)
    When User selects "<tripType>" trip
    And User enters flight search details
    
    #dataTable
    
      | source      | destination | travelDate |
      | <source>    | <destination> | <date>   |
      
    And User selects traveller details "<travellerCount>" and "<cabinClass>"
    And User selects special fare "<specialFare>"
    And User clicks on Search button
    Then Flight search results should be displayed

    Examples:
      | tripType   | source   | destination | date       | travellerCount | cabinClass | specialFare |
      | OneWay     | Chennai  | Delhi       | 25-05-2026 | 1 Adult        | Economy    | Regular     |
     # | OneWay   |   Chennai  | Mumbai      | 30-05-2026 | 2 Adults       | Business   | Student     |

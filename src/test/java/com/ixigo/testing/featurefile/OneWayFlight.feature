Feature: Flight Booking Functionality for OneWay Flight

  Background:
    Given User launches the flight booking application

  # ═══════════════════════════════════════════════════
  # END TO END SCENARIO
  # Search → Filter → View Flight Details
  # ═══════════════════════════════════════════════════

  Scenario: End to End One Way Flight Booking

    # ── STEP 1: Search Flight ──────────────────
    When user selects "One Way" trip type
    And User enters flight search details
      | source  | destination | travelDate |
      | Chennai | Delhi       | 25-06-2026 |
    And User selects traveller details "1 Adult" and "Economy"
    And User selects special fare "Regular"
    And user clicks on search button
    Then user should see available flights

    # ── STEP 2: Apply Filter ───────────────────
    When User applies filters for airline "IndiGo"
    Then Filtered flight results should be displayed correctly for "IndiGo"

    # ── STEP 3: Select Flight & View Details ───
    When User selects a flight
    Then User should be able to view complete flight details
      | Field         | Value    |
      | Airline       | IndiGo   |
      | FlightNumber  | 6E6045   |
      | Departure     | 07:00    |
      | DepartureCity | BOM      |
      | Arrival       | 09:10    |
      | ArrivalCity   | DEL      |
      | Duration      | 2h 10m   |
      | Type          | Non-Stop |
      | Price         | 22,058   |
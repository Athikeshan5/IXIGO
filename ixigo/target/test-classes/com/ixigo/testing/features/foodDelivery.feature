Feature: Food Delivery in Train

Scenario: Search food using PNR
  Given User is on food delivery page
  When User enters PNR number "1234567890"
  And User clicks search food
  Then User should see food results page
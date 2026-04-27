Feature: Platform Locator

Scenario: Search train platform and proceed to booking

  Given User is on platform locator page
  When User enters train number "12605"
  And User clicks search platform
  And User completes booking flow from platform
  Then User should see login popup
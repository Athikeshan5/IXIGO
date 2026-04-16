Feature: Platform Locator

Scenario: Search train platform and proceed to booking
  Given User is on platform locator page
  When User enters train number "12345"
  And User clicks search platform
  And User clicks book from platform
  Then User should reach payment page from platform module
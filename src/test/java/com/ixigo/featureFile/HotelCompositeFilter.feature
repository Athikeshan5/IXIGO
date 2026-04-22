Feature: Hotel Filter Functionality

Scenario Outline: User applies multiple hotel filters
  Given Enter "Mumbai" from "25 Apr" to "27 Apr"
  And the hotel list page is displayed
  When the user applies filter "<Filter Type>" with value "<Filter Value>"
  Then the active filter tags should be visible on the listing page

Examples:
  | Filter Type         | Filter Value        |
  | Accommodation Type  | Resort              |
  | Most Popular        | Free Cancellation   |
  | Payment Mode        | Prepaid             |
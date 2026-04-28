Feature: Bus Booking functionality in ixigo

Background:
  Given User launches the ixigo website

@search
  Scenario Outline: User searches for buses between cities
    Given User launches the ixigo website
    And User navigates to bus search
    When User enters "<source>" and "<destination>"
    And User selects travel date and clicks search
    Then Bus results should be displayed
   
    Examples:
      | source  | destination |
      | Chennai | Salem       | 
      | Chennai | Coimbatore  |  
 
        
@mandatoryfilters
  Scenario: User applies mandatory filters on bus results
    Given User launches the ixigo website
    And User navigates to bus search
    And User enters "Chennai" and "Salem"
    And User selects travel date and clicks search
    When user applies mandatory filters
     And user applies optional filter "price"
    Then filtered bus results should be displayed


@Selectseats
  Scenario: User selects a bus and chooses a seat
    Given User launches the ixigo website
    And User navigates to bus search
    When user enters bus search details
      | source  | destination |
      | Chennai | Salem       |
      | Chennai | Coimbatore  |
    And User selects travel date and clicks search
    And user applies mandatory filters
    When user selects the bus and seat
    And user selects boarding and dropping point
    Then personal details page should be displayed

@PersonalDetails
  Scenario: User fills passenger details and proceeds to payment
    Given User launches the ixigo website
    And User navigates to bus search
    And User enters "Chennai" and "Salem"
    And User selects travel date and clicks search
    And user applies mandatory filters
    And user selects the bus and seat
    And user selects boarding and dropping point
    When user enters passenger name and age
    And user clicks proceed to pay
    Then user should be navigated to the payment page
    
    
@trackticket
Scenario:  track the ticket with invalid details
  Given User launches the ixigo website
  And User navigates to bus search
  And navigates to the trckticket page
  And enter the ticketid
  When enter the phoneno
  Then verify the message

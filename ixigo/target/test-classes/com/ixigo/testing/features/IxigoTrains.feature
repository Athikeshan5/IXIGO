#Feature: Ixigo Train Information and Services

 # Background:
  #  Given User is on Ixigo trains homepage

  # ================= SEARCH BY TRAIN NAME =================
  #Scenario Outline: Search train by name or number
   # When User searches train by name "<train>"
    #And User selects first train from results
    #And User navigates to availability and clicks book
    #Then User should be on payment page

    #Examples:
     # | train            |
      #| Pallavan Express |
      #| 12605            |

  # ================= STATION SEARCH =================
  #Scenario: Search trains by station
   # When User searches station "Chennai"
    #Then User should be on payment page

  # ================= PLATFORM LOCATOR =================
  #Scenario: Check platform locator
   # When User searches platform for train "12605"
    #Then User should be on payment page

  # ================= VANDE BHARAT =================
  #Scenario: Vande Bharat flow
   # When User opens Vande Bharat module
    #And User enters from city "Chennai" and to city "Bangalore"
    #And User clicks on search button
    #Then User should be on payment page

  # ================= FOOD DELIVERY (NEGATIVE) =================
  #Scenario: Invalid PNR food delivery
   # When User enters PNR "1234567890" for food delivery
    #Then Autocomplete suggestions should be displayed

  # ================= DEFECT CASE =================
  #Scenario: Enter key validation
   # When User searches train by name "12605"
    #Then Train search should work on pressing enter
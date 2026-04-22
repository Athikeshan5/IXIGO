Feature: Hotel Filter and Sort on Search Results

 
 Scenario Outline: To verify hotel filter by price range and star rating
    Given the user has searched for hotels in "Chennai" from "25 Apr" to "27 Apr"
    And the hotel search results page is displayed
    When apply price filter "<priceRange>"
    And select the star rating filter "<starRating>"
    And select the user rating filter "<userRating>"
    Then verify the filtered hotel results are displayed
    
   Examples:
    | priceRange | starRating | userRating  |
    | 1000-2000  | 5 Star     | Exceptional |
    | 2000-2500  | 4 Star     | Excellent   |

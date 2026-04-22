Feature: Hotel search with sorting functionality 
Scenario: Sorts hotel results by price low to high
#Given open the browser
#And Navigate to url "https://www.ixigo.com/hotels"
#Given User is on the Hotel page
Given user enters destination "Chennai" on the search bar
When user selects check-in date 
And select check-out date
And select number of rooms and guests
When inventory is displayed click sortby dropdown "Price: Low to High"
Then list of hotels in Chennai should be displayed 
And the price of first hotel should be less then or equal to the next one

 
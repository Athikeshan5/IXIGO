Feature: Hotel search 
Scenario: Search hotel with valid location
#Given open the browser
#And Navigate to url "https://www.ixigo.com/hotels"
#Given User is on the Hotel page
When user enters destination "Chennai" on the search bar
And click on search button
When user selects check-in date 
And select check-out date
And select number of rooms and guests
Then list of hotels in Chennai should be displayed 

 
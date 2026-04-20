Feature: Search hotel by destination and add to Wishlist
Scenario: To verify hotel is saved to the wishlist after selecting from the Destination
When click and enter the destination 
|destination|
|chennai|
And click on the search button
Then verify that the user is navigated to hotels page
When click on Sort By dropdown 
And select the sort option
|sortOption|
|User Rating|
When click on the first hotel from the sorted results
And switch to the child page
And click the Save to Wishlist on the hotel page
Then verify the hotel added to Wishlist successfully
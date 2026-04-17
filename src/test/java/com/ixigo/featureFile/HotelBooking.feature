Feature: Complete Hotel Booking Journey

Scenario: To verify hotel search and booking flow

#Given open the browser
#And Navigate to url "https://www.ixigo.com/"
#Given click the hotel module  
When click the search destination field and enter the destination "Taj Coromandel chennai"  
And select check-in date as 2 days from today and check-out date as 5 days from today 
And select "4 Adults" and "2 Room" 
And click the search button 


#When click on the hotel
#And verify the expected hotel is shown
#And click on Reserve rooms 

## When click the first hotel from the search results  
##And verify hotel details like name, amenities, photos and rooms  

#Then redirected to login page
#When logs in with phone number "8746484747"
#Then verify booking details page  
#And verify hotel name, room type, check-in and check-out dates  
#And the total price should be displayed 
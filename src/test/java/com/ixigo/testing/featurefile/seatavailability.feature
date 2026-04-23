Feature:book the available train seat
Scenario:To verify the seat booked in a train

Given click the train module from the home 
And click the seat availability module 
When Enter the station for ticket booking
And click the check availability button 
And click the filter for booking "20"
And click the seat to book 
And click the seat person type  
And Enter the passenger details 
Then verify the payment page 

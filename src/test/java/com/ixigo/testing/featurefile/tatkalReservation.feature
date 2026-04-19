Feature:book the tatkal ticket 
Scenario:To verify the tatkal ticket booking

Given go to home page click the trains module 
And go the trains module click the tatkal reservation module 
When enter the stations and search "New Delhi (NDLS)" "C Shivaji Mah T (CSMT)" 
And click the filter "20"
And go to seat section and book the ticket "8608980857"
And enter the passenger details "guru" "21"
Then verify the tatkal payment page "Contact Details"

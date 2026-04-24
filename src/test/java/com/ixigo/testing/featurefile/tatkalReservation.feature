Feature:book the tatkal ticket 
Scenario:To verify the tatkal ticket booking

Given go to home page click the trains module 
And go the trains module click the tatkal reservation module 
When enter the stations and search "Pune Jn (PUNE)" "Mgr Chennai Ctl (MAS)" 
And click the seat to books 
And click the seat person types
And enter the passenger details "guru" "21"
Then verify the tatkal payment page "Contact Details"

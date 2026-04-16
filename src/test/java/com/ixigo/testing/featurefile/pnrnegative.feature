Feature: PNR entry error handling
Scenario Outline:Verify invalid PNR entry error handling

Given  click the pnr sub module 
When Enter the pnr number "<pnr>"
And click the check pnr status
Then verify the pnr message page

Examples:
|pnr|
|1234567890|
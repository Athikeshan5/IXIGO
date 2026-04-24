Feature: PNR entry error handling
Scenario Outline:Verify invalid PNR entry error handling

Given  click the pnr sub module 
When Enter the pnr number "<pnr>"
And click the check pnr status
Then verify the pnr message page "<urls>"

Examples:
|pnr|urls|
|4754897023|https://www.ixigo.com/trains/pnr-status|

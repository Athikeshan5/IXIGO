package com.ixigo.testing.pages;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//
//
public class OneWayFlightPage {
//
//
//	    private final WebDriver driver;
//	    private final WebDriverWait wait;
//
//	    // --- Locators ---
//	    private final By onewayBtn        = By.xpath("//button[normalize-space()='One Way']");
//	    private final By fromSpan         = By.xpath("//span[text()='From']");
//	    private final By fromInput        = By.xpath("//label[text()='From']/..//input");
//	    private final By toSpan           = By.xpath("//span[text()='To']");
//	    private final By toInput          = By.xpath("//label[text()='To']/..//input");
//	    private final By searchBtn        = By.xpath("//button[normalize-space()='Search']");
//	    private final By departureDateBtn = By.cssSelector("[data-testid='departureDate']");
//	    private final By calendarNextBtn  = By.xpath("//button[@aria-label='Next Month' or @aria-label='next']");
//
//	    public OneWayFlightPage(WebDriver driver) {
//	        this.driver = driver;
//	        this.wait   = new WebDriverWait(driver,Duration.ofSeconds(15));
//	    }
//
//	    public void navigateTo(String url) {
//	        driver.get(url);
//	    }
//
//	    public void selectTripType() {
//	        wait.until(ExpectedConditions.elementToBeClickable(onewayBtn)).click();
//	    }
//
//	    public void enterSource(String source) throws InterruptedException {
//	        wait.until(ExpectedConditions.elementToBeClickable(fromSpan)).click();
//	        Thread.sleep(800);
//	        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(fromInput));
//	        input.clear();
//	        input.sendKeys(source);
//	        selectDropdownOption(source);
//	    }
//
//	    public void enterDestination(String destination) throws InterruptedException {
//	        wait.until(ExpectedConditions.elementToBeClickable(toSpan)).click();
//	        Thread.sleep(800);
//	        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(toInput));
//	        input.clear();
//	        input.sendKeys(destination);
//	        selectDropdownOption(destination);
//	    }
//
//	    /**
//	     * Selects a date from the calendar by navigating months dynamically.
//	     * @param dateStr format: dd-MM-yyyy (e.g., "25-05-2026")
//	     */
//	    public void selectDate(String dateStr) throws InterruptedException {
//
//	        // Parse the date - format must be dd-MM-yyyy
//	        LocalDate targetDate = LocalDate.parse(dateStr, 
//	                               DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//	        String targetDay   = String.valueOf(targetDate.getDayOfMonth());
//	        String targetMonth = targetDate.format(DateTimeFormatter.ofPattern("MMMM")); 
//	        String targetYear  = String.valueOf(targetDate.getYear());
//
//	        // Step 1 - Click date field to open calendar
//	        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.cssSelector("[data-testid='departureDate']")
//	        ));
//	        jsClick(dateField);
//	        Thread.sleep(2000);
//
//	        // Step 2 - Navigate to correct month
//	        for (int i = 0; i < 12; i++) {
//
//	            // Check if current month and year is visible
//	            String calendarText = driver.findElement(By.tagName("body")).getText();
//
//	            if (calendarText.contains(targetMonth) && 
//	                calendarText.contains(targetYear)) {
//	                System.out.println("Found month: " + targetMonth + " " + targetYear);
//	                break;
//	            }
//
//	            // Click Next button
//	            List<WebElement> nextButtons = driver.findElements(
//	                By.xpath("//button[@aria-label='Next' or " +
//	                         "@aria-label='next month' or " +
//	                         "contains(@class,'next') or " +
//	                         "contains(@class,'Next')]")
//	            );
//
//	            if (!nextButtons.isEmpty()) {
//	                jsClick(nextButtons.get(0));
//	                Thread.sleep(800);
//	            } else {
//	                System.out.println("Next button not found!");
//	                break;
//	            }
//	        }
//
//	        // Step 3 - Click the correct day
//	        Thread.sleep(1000);
//
//	        // Find all day elements and click the exact match
//	        List<WebElement> allDays = driver.findElements(
//	            By.xpath("//*[normalize-space(text())='" + targetDay + "']" +
//	                     "[not(contains(@class,'disabled'))]" +
//	                     "[not(contains(@class,'past'))]")
//	        );
//
//	        System.out.println("Day elements found: " + allDays.size());
//
//	        if (!allDays.isEmpty()) {
//	            jsClick(allDays.get(0));
//	            System.out.println("Clicked date: " + targetDay);
//	        } else {
//	            System.out.println("Day not found! Taking screenshot...");
//	        }
//
//	        Thread.sleep(500);
//	    }
//	       
//	    public void clickSearch() {
//	        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
//	    }
//
//	    public boolean isFlightResultsDisplayed() {
//	        try {
//	            // Wait longer - flight results take time to load
//	            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
//
//	            // Try multiple possible XPaths for Ixigo results page
//	            By[] resultLocators = {
//	                By.xpath("//*[contains(@class,'flight-listing')]"),
//	                By.xpath("//*[contains(@class,'search-result')]"),
//	                By.xpath("//*[contains(@class,'flight-card')]"),
//	                By.xpath("//*[contains(@class,'result-item')]"),
//	                By.xpath("//*[contains(text(),'flights found')]"),
//	                By.xpath("//*[contains(text(),'Flights found')]"),
//	                By.xpath("//*[contains(@class,'airlineName')]"),
//	                By.xpath("//*[contains(@class,'price')]"),
//	                // URL check - Ixigo changes URL after search
//	                By.xpath("//div[contains(@class,'listing')]")
//	            };
//
//	            // Try each locator
//	            for (By locator : resultLocators) {
//	                List<WebElement> elements = driver.findElements(locator);
//	                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
//	                    System.out.println("Results found with: " + locator);
//	                    return true;
//	                }
//	            }
//
//	            // Final check - verify URL changed to results page
//	            String currentUrl = driver.getCurrentUrl();
//	            System.out.println("Current URL: " + currentUrl);
//
//	            if (currentUrl.contains("/flight/") || 
//	                currentUrl.contains("results") ||
//	                currentUrl.contains("search")) {
//	                System.out.println("URL confirms results page loaded!");
//	                return true;
//	            }
//
//	            return false;
//
//	        } catch (Exception e) {
//	            System.out.println("Error checking results: " + e.getMessage());
//	            return false;
//	        }
//	    }
//	    public String getCurrentUrl() {
//	        return driver.getCurrentUrl();
//	    }
//
//	    // --- Helpers ---
//
//	    private void selectDropdownOption(String text) throws InterruptedException {
//	        Thread.sleep(2500);
//	        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//span[contains(.,'" + text + "')]")
//	        ));
//	        jsClick(option);
//	    }
//
//	    private void jsClick(WebElement element) {
//	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//	    }
}






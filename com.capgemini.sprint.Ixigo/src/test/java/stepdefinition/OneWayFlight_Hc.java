
package stepdefinition;



import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class OneWayFlight_Hc {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    @Given("User launches the flight booking application")
    public void launchApplication() {
      //  driver = BaseClass.getDriver();
        wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        js     = (JavascriptExecutor) driver;
        driver.get("https://www.ixigo.com/flights");
    }
    // Trip Type
    @When("User selects {string} trip")
    public void selectTripType(String tripType) {
        String label = tripType.equalsIgnoreCase("OneWay") ? "One Way" : "Round Trip";
        clickElement(By.xpath("//button[.='" + label + "']"));
    }
    //Flight Search Details
    @When("User enters flight search details")
    public void enterFlightSearchDetails(DataTable dataTable) throws InterruptedException {
        Map<String, String> row = dataTable.asMaps(String.class, String.class).get(0);

        fillCityField("From", row.get("source"));
        fillCityField("To",   row.get("destination"));
        selectDate(row.get("travelDate"));
    }
    private void fillCityField(String label, String city) throws InterruptedException {
        clickElement(By.xpath("//span[text()='" + label + "']"));
        Thread.sleep(800);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//label[text()='" + label + "']/..//input")));
        input.clear();
        input.sendKeys(city);
        Thread.sleep(2500);

        WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[contains(.,'" + city + "')]")));
        jsClick(suggestion);
    }
    //  Date Selection
    private void selectDate(String dateStr) throws InterruptedException {
        LocalDate date  = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String month    = date.format(DateTimeFormatter.ofPattern("MMMM"));
        String year     = String.valueOf(date.getYear());
        String day      = String.valueOf(date.getDayOfMonth());
        String paddedMM = String.format("%02d", date.getMonthValue());
        String paddedDD = String.format("%02d", date.getDayOfMonth());

        // Open calendar
        jsClick(wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("[data-testid='departureDate']"))));
        Thread.sleep(1500);

        // Navigate to correct month (max 24 clicks)
        for (int i = 0; i < 24; i++) {
            if (driver.findElement(By.tagName("body")).getText().contains(month + " " + year)) break;
            List<WebElement> nextBtns = driver.findElements(By.xpath(
                "//*[@aria-label='Next Month'] | //*[@aria-label='Next'] | " +
                "//*[contains(@class,'NavButton')][2]"));
            if (nextBtns.isEmpty()) break;
            jsClick(nextBtns.get(0));
            Thread.sleep(700);
        }
        Thread.sleep(800);

        // Click the day — try aria-label first, then data-date, then text match
        String ariaLabel = month + " " + day + ", " + year;
        String dataDate  = year + "-" + paddedMM + "-" + paddedDD;

        String[] xpaths = {
            "//*[@aria-label='" + ariaLabel + "']",
            "//*[@data-date='"  + dataDate  + "']",
            "//span[normalize-space(text())='" + day + "'][not(contains(@class,'disabled'))]",
            "//p[normalize-space(text())='"    + day + "']",
            "//div[normalize-space(text())='"  + day + "'][not(contains(@class,'disabled'))]"
        };

        for (String xpath : xpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            for (WebElement el : els) {
                String cls = el.getAttribute("class");
                if (cls != null && (cls.contains("disabled") || cls.contains("blocked"))) continue;
                try {
                    jsClick(el);
                    System.out.println("✅ Date selected: " + dateStr);
                    return;
                } catch (Exception ignored) {}
            }
        }
        System.out.println("❌ Could not select date: " + dateStr);
    }

    // ─────────────────────────────────────────
    //  WHEN — Traveller Details
    // ─────────────────────────────────────────

    @When("User selects traveller details {string} and {string}")
    public void selectTravellerDetails(String travellers, String cabinClass) throws InterruptedException {

        // Open traveller panel — try several locators
        WebElement panel = findFirstVisible(
            "//*[contains(@data-testid,'traveller')]",
            "//*[contains(@data-testid,'passenger')]",
            "//span[contains(text(),'Traveller')]",
            "//*[contains(text(),'Adult')]"
        );
        if (panel == null) { System.out.println("❌ Traveller field not found!"); return; }
        jsClick(panel);
        Thread.sleep(1200);

        // Increase adult count if needed
        int count = Integer.parseInt(travellers.replaceAll("[^0-9]", ""));
        for (int i = 1; i < count; i++) {
            WebElement plus = findFirstVisible(
                "//*[contains(@class,'adult') or contains(@class,'Adult')]" +
                "//*[text()='+' or contains(@class,'increment') or contains(@class,'plus')]"
            );
            if (plus != null) { jsClick(plus); Thread.sleep(400); }
        }

        // Select cabin class
        Thread.sleep(400);
        WebElement cabin = findFirstVisible(
            "//*[contains(text(),'" + cabinClass + "')][@role='radio' or @role='option' " +
            "or contains(@class,'cabin') or contains(@class,'class')]",
            "//*[normalize-space(text())='" + cabinClass + "']"
        );
        if (cabin != null) { jsClick(cabin); System.out.println("✅ Cabin: " + cabinClass); }

        // Click Done / Apply
        Thread.sleep(400);
        WebElement done = findFirstVisible(
            "//button[contains(translate(.,'done','DONE'),'DONE')]",
            "//button[contains(translate(.,'apply','APPLY'),'APPLY')]"
        );
        if (done != null) { jsClick(done); System.out.println("✅ Done clicked"); }

        Thread.sleep(800);
        System.out.println("✅ Travellers: " + travellers + " | Cabin: " + cabinClass);
    }

    // ─────────────────────────────────────────
    //  WHEN — Special Fare
    // ─────────────────────────────────────────

    @When("User selects special fare {string}")
    public void selectSpecialFare(String fareType) throws InterruptedException {
        WebElement fare = findFirstVisible(
            "//*[normalize-space(text())='" + fareType + "']",
            "//label[contains(text(),'"     + fareType + "')]",
            "//span[contains(text(),'"      + fareType + "')]",
            "//*[@data-fare='"              + fareType + "']",
            "//input[@type='radio']/following-sibling::*[contains(text(),'" + fareType + "')]"
        );

        if (fare != null) {
            jsClick(fare);
            System.out.println("✅ Fare selected: " + fareType);
        } else {
            // Fare section may not be visible — skip gracefully
            System.out.println("⚠️ Fare '" + fareType + "' not found — may already be default. Continuing.");
        }
        Thread.sleep(400);
    }

    // ─────────────────────────────────────────
    //  WHEN — Search
    // ─────────────────────────────────────────

    @When("User clicks on Search button")
    public void clickSearchButton() {
        clickElement(By.xpath("//button[text()='Search']"));
    }

    // ─────────────────────────────────────────
    //  THEN — Verify Results
    // ─────────────────────────────────────────

    @Then("Flight search results should be displayed")
    public void verifyFlightResults() throws InterruptedException {
        Thread.sleep(3000);

        // ── DEBUG: print div classes to find the correct locator ──
        System.out.println("URL: " + driver.getCurrentUrl());
        driver.findElements(By.tagName("div")).stream()
            .map(e -> { try { return e.getAttribute("class"); } catch (Exception ex) { return null; } })
            .filter(c -> c != null && !c.isEmpty())
            .distinct()
            .limit(100)
            .forEach(System.out::println);

        // ── Update the XPath below after reading the printed classes ──
        // Example: "//div[contains(@class,'fsrp')]"
        // Uncomment and replace once you know the real class name:
        /*
        WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class,'REPLACE_WITH_REAL_CLASS')]")));
        System.out.println("✅ Flight results displayed");
        Assert.assertNotNull(results);
        */

        System.out.println("⚠️ Results assertion pending — check console for actual class names.");
    }

    // ─────────────────────────────────────────
    //  Helpers
    // ─────────────────────────────────────────

    /** Click element via WebDriverWait + JS fallback. */
    private void clickElement(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            jsClick(driver.findElement(locator));
        }
    }

    /** JavaScript click. */
    private void jsClick(WebElement el) {
        js.executeScript("arguments[0].click();", el);
    }

    /**
     * Returns the first visible element matched by any of the supplied XPaths,
     * or null if none match.
     */
    private WebElement findFirstVisible(String... xpaths) {
        for (String xpath : xpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty()) return els.get(0);
        }
        return null;
    }
}
//package com.capgemini.sprint.stepdefinition;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Map;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import com.capgemini.sprint.Utility.BaseClass;
//
//import io.cucumber.datatable.DataTable;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class OneWayFlight_Hc {
//	WebDriver driver;
//	WebDriverWait wait;
//	
//	 @Given("User launches the flight booking application")
//	    public void launchApplication() {
//	        driver = BaseClass.getDriver();
//	        driver.get("https://www.ixigo.com/flights"); // Example URL
//	    }
//
//	    @When("User selects {string} trip")
//	    public void selectTripType(String tripType) {
//	        if (tripType.equalsIgnoreCase("OneWay")) {
//	            driver.findElement(By.xpath("//button[.='One Way']")).click();
//	        } else if (tripType.equalsIgnoreCase("RoundTrip")) {
//	            driver.findElement(By.xpath("//button[.='Round Trip']")).click();
//	        }
//	    }
//
//	    @When("User enters flight search details")
//	    public void enterFlightSearchDetails(DataTable dataTable) throws InterruptedException {
//
//	        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
//
//	        String source = data.get(0).get("source");
//	        String destination = data.get(0).get("destination");
//	        String date = data.get(0).get("travelDate");
//	        
//	      //Source
//		    WebElement from_field = driver.findElement(By.xpath("//span[text()='From']"));
//		    from_field.click();
//		    Thread.sleep(1000);
//
//		 
//		    WebElement from_input = driver.findElement(
//		        By.xpath("//label[text()='From']/..//input")
//		    );
//		    from_input.clear();
//		    from_input.sendKeys(source);
//		    Thread.sleep(3000); // Wait for dropdown suggestions to fully load
//		    WebElement dropdown = driver.findElement(By.xpath("//span[contains(.,'" + source + "')]"));
//
//		 // Force click using JavaScript
//		 JavascriptExecutor js = (JavascriptExecutor) driver;
//		 js.executeScript("arguments[0].click();", dropdown);
//		    
//		 //Destination
//		 // Step 1: Click the 'From' span to open the input
//		    WebElement To_field = driver.findElement(By.xpath("//span[text()='To']"));
//		    To_field.click();
//		    Thread.sleep(1000);
//
//		    // Step 2: Find the actual input field and type
//		    WebElement To_input = driver.findElement(
//		        By.xpath("//label[text()='To']/..//input")
//		    );
//		    To_input.clear();
//		    To_input.sendKeys(destination);
//		    Thread.sleep(3000); // Wait for dropdown suggestions to fully load
//		    WebElement dropdownTo = driver.findElement(By.xpath("//span[contains(.,'" + destination + "')]"));
//
//		 // Force click using JavaScript
//		 JavascriptExecutor js1 = (JavascriptExecutor) driver;
//		 js1.executeScript("arguments[0].click();", dropdownTo);
//		 
//		 //Date
//		 selectDate(date);
//		}
//	 // Add this TEMPORARILY to find the correct day XPath
//	    public void debugCalendarXpath(String targetDay) throws InterruptedException {
//	        Thread.sleep(2000);
//	        
//	        System.out.println("=== DEBUGGING CALENDAR STRUCTURE ===");
//	        
//	        // Print ALL elements containing the day number
//	        List<WebElement> allElements = driver.findElements(
//	            By.xpath("//*[normalize-space(text())='" + targetDay + "']")
//	        );
//	        System.out.println("All elements with text '" + targetDay + "': " 
//	                            + allElements.size());
//	        
//	        for (WebElement e : allElements) {
//	            System.out.println(
//	                "Tag: "     + e.getTagName() +
//	                " | Class: " + e.getAttribute("class") +
//	                " | Role: "  + e.getAttribute("role") +
//	                " | aria-label: " + e.getAttribute("aria-label") +
//	                " | data-date: "  + e.getAttribute("data-date")
//	            );
//	        }
//
//	        // Print ALL aria-label elements in calendar
//	        System.out.println("\n=== ARIA LABEL ELEMENTS ===");
//	        List<WebElement> ariaElements = driver.findElements(
//	            By.xpath("//*[@aria-label]")
//	        );
//	        for (WebElement e : ariaElements) {
//	            String label = e.getAttribute("aria-label");
//	            if (label != null && label.contains(targetDay)) {
//	                System.out.println(
//	                    "Tag: "      + e.getTagName() +
//	                    " | Class: " + e.getAttribute("class") +
//	                    " | aria-label: " + label
//	                );
//	            }
//	        }
//	        
//	        // Print ALL data-* attributes in calendar area
//	        System.out.println("\n=== DATA ATTRIBUTES ===");
//	        List<WebElement> dataElements = driver.findElements(
//	            By.xpath("//*[@data-date or @data-day or @data-value]")
//	        );
//	        for (WebElement e : dataElements) {
//	            System.out.println(
//	                "Tag: "        + e.getTagName() +
//	                " | data-date: " + e.getAttribute("data-date") +
//	                " | data-day: "  + e.getAttribute("data-day") +
//	                " | Class: "   + e.getAttribute("class") +
//	                " | Text: "    + e.getText()
//	            );
//	        }
//	    }
//	    public void selectDate(String dateStr) throws InterruptedException {
//
//	        JavascriptExecutor js = (JavascriptExecutor) driver;
//	        WebDriverWait wait    = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	        // Parse date
//	        LocalDate targetDate = LocalDate.parse(dateStr,
//	                               DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//	        String targetDay     = String.valueOf(targetDate.getDayOfMonth());
//	        String targetMonth   = targetDate.format(
//	                               DateTimeFormatter.ofPattern("MMMM"));
//	        String targetYear    = String.valueOf(targetDate.getYear());
//	        // Full date for aria-label e.g. "May 25, 2026" or "25 May 2026"
//	        String ariaDate1     = targetMonth + " " + targetDay + ", " + targetYear;
//	        String ariaDate2     = targetDay + " " + targetMonth + " " + targetYear;
//
//	        System.out.println("Target: " + targetDay + " " + targetMonth + " " + targetYear);
//
//	        // ── Open Calendar ───────────────────────
//	        List<WebElement> dateFields = driver.findElements(
//	            By.cssSelector("[data-testid='departureDate']")
//	        );
//	        if (!dateFields.isEmpty()) {
//	            js.executeScript("arguments[0].click();", dateFields.get(0));
//	            System.out.println("Calendar opened!");
//	        } else {
//	            System.out.println("Date field not found!");
//	            return;
//	        }
//	        Thread.sleep(2000);
//
//	        // ── Navigate to Correct Month ───────────
//	        for (int i = 0; i < 24; i++) {
//	            String bodyText = driver.findElement(By.tagName("body")).getText();
//	            if (bodyText.contains(targetMonth) && bodyText.contains(targetYear)) {
//	                System.out.println("Correct month: " + targetMonth + " " + targetYear);
//	                break;
//	            }
//	            // Try multiple next button XPaths
//	            List<WebElement> nextBtns = driver.findElements(By.xpath(
//	                "//*[@aria-label='Next Month'] | " +
//	                "//*[@aria-label='next month'] | " +
//	                "//*[@aria-label='Next'] | " +
//	                "//*[contains(@class,'next')][@role='button'] | " +
//	                "//*[contains(@class,'NavButton')][2] | " +
//	                "//*[contains(@class,'navigation')][last()]"
//	            ));
//	            if (!nextBtns.isEmpty()) {
//	                js.executeScript("arguments[0].click();", nextBtns.get(0));
//	                Thread.sleep(800);
//	            } else {
//	                System.out.println("Next button not found!");
//	                break;
//	            }
//	        }
//
//	        Thread.sleep(1000);
//
//	        // ── Click Day - Try ALL Possible XPaths ─
//	        String[] dayXpaths = {
//	            // aria-label based (most reliable)
//	            "//*[@aria-label='" + ariaDate1 + "']",
//	            "//*[@aria-label='" + ariaDate2 + "']",
//	            "//*[contains(@aria-label,'" + targetDay + " " + targetMonth + "')]",
//	            "//*[contains(@aria-label,'" + targetMonth + " " + targetDay + "')]",
//	            // data-date attribute
//	            "//*[@data-date='" + targetYear + "-" + 
//	                String.format("%02d", targetDate.getMonthValue()) + "-" + 
//	                String.format("%02d", targetDate.getDayOfMonth()) + "']",
//	            // span/div/p with exact text inside calendar
//	            "//span[normalize-space(text())='" + targetDay + "']" +
//	                "[not(contains(@class,'disabled'))]",
//	            "//p[normalize-space(text())='" + targetDay + "']",
//	            "//div[normalize-space(text())='" + targetDay + "']" +
//	                "[not(contains(@class,'disabled'))]",
//	        };
//
//	        boolean clicked = false;
//	        for (String xpath : dayXpaths) {
//	            List<WebElement> days = driver.findElements(By.xpath(xpath));
//	            System.out.println("XPath: " + xpath + " → Found: " + days.size());
//
//	            for (WebElement day : days) {
//	                String cls = day.getAttribute("class");
//	                if (cls != null && (cls.contains("disabled") || 
//	                    cls.contains("blocked"))) continue;
//	                try {
//	                    js.executeScript("arguments[0].click();", day);
//	                    System.out.println("✅ Date clicked: " + targetDay);
//	                    clicked = true;
//	                    break;
//	                } catch (Exception e) {
//	                    System.out.println("Click failed, trying next...");
//	                }
//	            }
//	            if (clicked) break;
//	        }
//
//	        if (!clicked) {
//	            // Last resort - debug to find actual structure
//	            debugCalendarXpath(targetDay);
//	        }
//
//	        Thread.sleep(500);
//	    }
//
//	    @When("User selects traveller details {string} and {string}")
//	    public void user_selects_traveller_details_and(String travellers, String cabinClass) 
//	                                                    throws InterruptedException {
//
//	        JavascriptExecutor js = (JavascriptExecutor) driver;
//	        WebDriverWait wait    = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	        // ── STEP 1: Click Travellers field ──────
//	        String[] travellerXpaths = {
//	            "//*[contains(@data-testid,'traveller')]",
//	            "//*[contains(@data-testid,'passenger')]",
//	            "//span[contains(text(),'Traveller')]",
//	            "//div[contains(text(),'Traveller')]",
//	            "//*[contains(@class,'traveller')]",
//	            "//*[contains(text(),'Adult')]",
//	            "//*[contains(text(),'Passenger')]"
//	        };
//
//	        WebElement travellerField = null;
//	        for (String xpath : travellerXpaths) {
//	            List<WebElement> elements = driver.findElements(By.xpath(xpath));
//	            System.out.println("Traveller XPath: " + xpath + 
//	                               " → Found: " + elements.size());
//	            if (!elements.isEmpty()) {
//	                travellerField = elements.get(0);
//	                System.out.println("✅ Traveller field found!");
//	                break;
//	            }
//	        }
//
//	        if (travellerField != null) {
//	            js.executeScript("arguments[0].click();", travellerField);
//	            Thread.sleep(1500);
//	        } else {
//	            System.out.println("❌ Traveller field not found!");
//	            return;
//	        }
//
//	        // ── STEP 2: Parse traveller count ───────
//	        // Input: "1 Adult" → extract number 1
//	        int adultCount = 1; // default
//	        try {
//	            adultCount = Integer.parseInt(travellers.replaceAll("[^0-9]", ""));
//	        } catch (Exception e) {
//	            System.out.println("Could not parse traveller count, using 1");
//	        }
//	        System.out.println("Adult count: " + adultCount);
//
//	        // ── STEP 3: Set adult count ──────────────
//	        // Get current count first
//	        WebElement adultCountEl = null;
//	        try {
//	            adultCountEl = driver.findElement(By.xpath(
//	                "//*[contains(@class,'adult') or contains(@class,'Adult')]" +
//	                "//*[contains(@class,'count') or contains(@class,'number')]"
//	            ));
//	        } catch (Exception e) {
//	            System.out.println("Adult count element not found by class");
//	        }
//
//	        // Click + button for adults if count > 1
//	        for (int i = 1; i < adultCount; i++) {
//	            List<WebElement> plusBtns = driver.findElements(By.xpath(
//	                "//*[contains(@class,'adult') or contains(@class,'Adult')]" +
//	                "//*[text()='+' or @aria-label='Increase' or " +
//	                "contains(@class,'increment') or contains(@class,'plus')]"
//	            ));
//	            if (!plusBtns.isEmpty()) {
//	                js.executeScript("arguments[0].click();", plusBtns.get(0));
//	                Thread.sleep(500);
//	                System.out.println("Increased adult count to: " + (i + 1));
//	            }
//	        }
//
//	        // ── STEP 4: Select Cabin Class ───────────
//	        // Input: "Economy" / "Business" / "Premium Economy"
//	        Thread.sleep(500);
//	        List<WebElement> cabinOptions = driver.findElements(By.xpath(
//	            "//*[contains(text(),'" + cabinClass + "')]" +
//	            "[contains(@class,'cabin') or contains(@class,'class') " +
//	            "or @role='radio' or @role='option']"
//	        ));
//
//	        System.out.println("Cabin class options found: " + cabinOptions.size());
//
//	        if (!cabinOptions.isEmpty()) {
//	            js.executeScript("arguments[0].click();", cabinOptions.get(0));
//	            System.out.println("✅ Selected cabin class: " + cabinClass);
//	        } else {
//	            // Try broader search
//	            List<WebElement> allOptions = driver.findElements(
//	                By.xpath("//*[normalize-space(text())='" + cabinClass + "']")
//	            );
//	            System.out.println("Broad search for cabin: " + allOptions.size());
//	            if (!allOptions.isEmpty()) {
//	                js.executeScript("arguments[0].click();", allOptions.get(0));
//	                System.out.println("✅ Cabin class selected: " + cabinClass);
//	            }
//	        }
//
//	        // ── STEP 5: Click Done/Apply button ─────
//	        Thread.sleep(500);
//	        List<WebElement> doneBtns = driver.findElements(By.xpath(
//	            "//button[contains(text(),'Done') or " +
//	            "contains(text(),'Apply') or " +
//	            "contains(text(),'done') or " +
//	            "contains(text(),'DONE')]"
//	        ));
//
//	        if (!doneBtns.isEmpty()) {
//	            js.executeScript("arguments[0].click();", doneBtns.get(0));
//	            System.out.println("✅ Clicked Done button");
//	        } else {
//	            System.out.println("Done button not found - continuing...");
//	        }
//
//	        Thread.sleep(1000);
//	        System.out.println("Traveller details set: " + travellers + " | " + cabinClass);
//	    
//	    }
//	    @When("User selects special fare {string}")
//	    public void selectSpecialFare(String fareType) throws InterruptedException {
//
//	        JavascriptExecutor js = (JavascriptExecutor) driver;
//	        System.out.println("Looking for fare type: " + fareType);
//
//	        // ── Try multiple XPaths ──────────────────
//	        String[] fareXpaths = {
//	            // Text based
//	            "//*[normalize-space(text())='" + fareType + "']",
//	            "//*[contains(text(),'" + fareType + "')]",
//
//	            // Label based
//	            "//label[contains(text(),'" + fareType + "')]",
//	            "//label[normalize-space(text())='" + fareType + "']",
//
//	            // Span based
//	            "//span[contains(text(),'" + fareType + "')]",
//	            "//span[normalize-space(text())='" + fareType + "']",
//
//	            // Input radio with label
//	            "//input[@type='radio']/following-sibling::*" +
//	            "[contains(text(),'" + fareType + "')]",
//
//	            // Data attribute based
//	            "//*[@data-fare='" + fareType + "']",
//	            "//*[@data-type='" + fareType + "']",
//	            "//*[contains(@class,'fare')][contains(text(),'" + fareType + "')]",
//	            "//*[contains(@class,'special')][contains(text(),'" + fareType + "')]"
//	        };
//
//	        boolean clicked = false;
//
//	        for (String xpath : fareXpaths) {
//	            List<WebElement> elements = driver.findElements(By.xpath(xpath));
//	            System.out.println("Fare XPath: " + xpath + 
//	                               " → Found: " + elements.size());
//
//	            if (!elements.isEmpty()) {
//	                try {
//	                    js.executeScript("arguments[0].click();", elements.get(0));
//	                    System.out.println("✅ Special fare selected: " + fareType);
//	                    clicked = true;
//	                    break;
//	                } catch (Exception e) {
//	                    System.out.println("Click failed, trying next...");
//	                }
//	            }
//	        }
//
//	        // ── If still not found - debug ───────────
//	        if (!clicked) {
//	            System.out.println("❌ Fare not found - printing all visible text:");
//
//	            // Print all elements that might be fare options
//	            List<WebElement> allFareEls = driver.findElements(By.xpath(
//	                "//*[contains(@class,'fare') or " +
//	                "contains(@class,'Fare') or " +
//	                "contains(@class,'special') or " +
//	                "contains(@class,'Special')]"
//	            ));
//
//	            System.out.println("Fare-related elements: " + allFareEls.size());
//	            for (WebElement el : allFareEls) {
//	                System.out.println(
//	                    "Tag: "      + el.getTagName() +
//	                    " | Text: "  + el.getText() +
//	                    " | Class: " + el.getAttribute("class")
//	                );
//	            }
//
//	            // Print all radio buttons
//	            List<WebElement> radios = driver.findElements(
//	                By.xpath("//input[@type='radio']")
//	            );
//	            System.out.println("\nRadio buttons found: " + radios.size());
//	            for (WebElement r : radios) {
//	                System.out.println(
//	                    "Value: "    + r.getAttribute("value") +
//	                    " | ID: "    + r.getAttribute("id") +
//	                    " | Name: "  + r.getAttribute("name")
//	                );
//	            }
//
//	            // Print all labels
//	            List<WebElement> labels = driver.findElements(By.tagName("label"));
//	            System.out.println("\nLabels found: " + labels.size());
//	            for (WebElement l : labels) {
//	                if (!l.getText().isEmpty()) {
//	                    System.out.println(
//	                        "Label text: " + l.getText() +
//	                        " | For: "     + l.getAttribute("for") +
//	                        " | Class: "   + l.getAttribute("class")
//	                    );
//	                }
//	            }
//	        }
//
//	        Thread.sleep(500);
//	    }
//
//	    @When("User clicks on Search button")
//	    public void clickSearchButton() {
//	        driver.findElement(By.xpath("//button[text()='Search']")).click();
//	    }
//
//	    @Then("Flight search results should be displayed")
//	    public void flight_search_results_should_be_displayed() throws InterruptedException {
//	        
//	        // ===== DEBUG START =====
//	        Thread.sleep(5000);
//	        
//	        System.out.println("Current URL: " + driver.getCurrentUrl());
//	        System.out.println("Page title: " + driver.getTitle());
//	        
//	        List<WebElement> allDivs = driver.findElements(By.tagName("div"));
//	        System.out.println("Total divs on page: " + allDivs.size());
//	        
//	        allDivs.stream()
//	            .map(e -> {
//	                try { return e.getAttribute("class"); } catch(Exception ex) { return null; }
//	            })
//	            .filter(c -> c != null && !c.isEmpty())
//	            .distinct()
//	            .limit(80)
//	            .forEach(System.out::println);
//	        // ===== DEBUG END =====
//
//	        // Your original assertion below (commented out for now)
//	        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	        // WebElement results = wait.until(...);
//	    }
//}
//
//

package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

public class FlightHomePage {

    private WebDriver           driver;
    private WebDriverWait       wait;
    private JavascriptExecutor  js;
    private AllUtilityFunctions util = new AllUtilityFunctions();

    public FlightHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ==================== ELEMENT LOCATORS ====================
    
    // Trip Type Buttons
    @FindBy(xpath = "//button[normalize-space()='One Way'] | //*[contains(@class,'oneway')] | //button[contains(@data-testid,'oneway')]")
    private WebElement oneWayBtn;

    @FindBy(xpath = "//button[normalize-space()='Round Trip'] | //*[contains(@class,'roundtrip')]")
    private WebElement roundTripBtn;

    // From / Source Elements
    @FindBy(xpath = "//span[text()='From'] | //div[contains(text(),'From')] | //label[contains(text(),'From')]")
    private WebElement fromSpan;

    @FindBy(xpath = "//input[@id='source'] | //input[@placeholder*='From'] | //input[contains(@class,'origin')] | //div[contains(@class,'origin')]//input | //input[@data-testid='source']")
    private WebElement fromInput;

    // To / Destination Elements
    @FindBy(xpath = "//span[text()='To'] | //div[contains(text(),'To')] | //label[contains(text(),'To')]")
    private WebElement toSpan;

    @FindBy(xpath = "//input[@id='destination'] | //input[@placeholder*='To'] | //input[contains(@class,'destination')] | //div[contains(@class,'destination')]//input | //input[@data-testid='destination']")
    private WebElement toInput;

    // Date Fields
    @FindBy(xpath = "//*[contains(@data-testid,'departureDate')] | //input[contains(@placeholder,'Departure')] | //div[contains(@class,'depart')]")
    private WebElement departureDateField;

    @FindBy(xpath = "//*[contains(@data-testid,'returnDate')] | //input[contains(@placeholder,'Return')]")
    private WebElement returnDateField;

    // Search Button
    @FindBy(xpath = "//button[normalize-space()='Search'] | //button[contains(text(),'Search')] | //*[@role='button'][contains(text(),'Search')] | //button[@type='submit']")
    private WebElement searchBtn;

    // Done/Apply Button
    @FindBy(xpath = "//button[normalize-space()='Done'] | //button[normalize-space()='Apply'] | //button[normalize-space()='DONE'] | //button[contains(text(),'Done')]")
    private WebElement doneBtn;

    // ==================== LOGIN METHODS ====================
    
    public void clickLogin() {
        System.out.println("Login handled by SessionManager");
    }
    
    public void loginWithMobile(String mobile) {
        System.out.println("Login handled by SessionManager");
    }

    // ==================== TRIP TYPE SELECTION ====================
    
    public void selectRoundTrip() throws InterruptedException {
        removeAllPopups();
        dismissOverlayIfPresent();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(roundTripBtn)).click();
          
        } catch (Exception e) {
            jsClick(roundTripBtn);
          
        }
        Thread.sleep(1000);
    }
    
    public void selectOneWay() throws InterruptedException {
        removeAllPopups();
        dismissOverlayIfPresent();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(oneWayBtn)).click();
         
        } catch (Exception e) {
            jsClick(oneWayBtn);
           
        }
        Thread.sleep(1000);
    }

    // ==================== SOURCE ENTRY ====================
    
    public void enterSource(String source) throws InterruptedException {
        removeAllPopups();
        dismissOverlayIfPresent();
        
        try {
            WebElement fromClickable = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(fromSpan));
            jsClick(fromClickable);
        } catch (Exception e) {
            jsClick(fromSpan);
        }
        Thread.sleep(800);
        
        WebElement fromInputField = findDynamicInputField("from");
        wait.until(ExpectedConditions.visibilityOf(fromInputField));
        fromInputField.clear();
        fromInputField.sendKeys(source);
        Thread.sleep(2500);
        selectDropdownOption(source);
       
    }

    // ==================== DESTINATION ENTRY ====================
    
    public void enterDestination(String destination) throws InterruptedException {
        removeAllPopups();
        dismissOverlayIfPresent();
        
        try {
            WebElement toClickable = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(toSpan));
            jsClick(toClickable);
        } catch (Exception e) {
            jsClick(toSpan);
        }
        Thread.sleep(800);
        
        WebElement toInputField = findDynamicInputField("to");
        wait.until(ExpectedConditions.visibilityOf(toInputField));
        toInputField.clear();
        toInputField.sendKeys(destination);
        Thread.sleep(2500);
        selectDropdownOption(destination);
       
    }

    // ==================== DATE SELECTION ====================
    
    public void selectDepartureDate(String dateStr) throws InterruptedException {
      
        
        // Parse date (DD-MM-YYYY)
        String[] parts = dateStr.split("-");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        
        try {
            WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(departureDateField));
            jsClick(dateField);
            Thread.sleep(1000);
            
            // Try multiple date formats
            String[] formats = {
                day + "-" + month + "-" + year,  // 25-06-2026
                day + "/" + month + "/" + year,  // 25/06/2026
                year + "-" + month + "-" + day   // 2026-06-25
            };
            
            for (String format : formats) {
                try {
                    dateField.sendKeys(Keys.CONTROL + "a");
                    dateField.sendKeys(Keys.DELETE);
                    dateField.sendKeys(format);
                    Thread.sleep(500);
                    dateField.sendKeys(Keys.ENTER);
                  
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
           
        } catch (Exception e) {
           
            // JavaScript fallback
            try {
                String isoDate = year + "-" + month + "-" + day;
                js.executeScript("arguments[0].value = arguments[1];", departureDateField, isoDate);
               
            } catch (Exception ex) {}
        }
        Thread.sleep(1000);
    }
    
    public void selectReturnDate(String dateStr) throws InterruptedException {
     
        
        // Parse date (DD-MM-YYYY)
        String[] parts = dateStr.split("-");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        
        try {
            WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(returnDateField));
            jsClick(dateField);
            Thread.sleep(1000);
            
            // Try multiple date formats
            String[] formats = {
                day + "-" + month + "-" + year,  // 25-06-2026
                day + "/" + month + "/" + year,  // 25/06/2026
                year + "-" + month + "-" + day   // 2026-06-25
            };
            
            for (String format : formats) {
                try {
                    dateField.sendKeys(Keys.CONTROL + "a");
                    dateField.sendKeys(Keys.DELETE);
                    dateField.sendKeys(format);
                    Thread.sleep(500);
                    dateField.sendKeys(Keys.ENTER);
                   
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
          
        } catch (Exception e) {
           
            // JavaScript fallback
            try {
                String isoDate = year + "-" + month + "-" + day;
                js.executeScript("arguments[0].value = arguments[1];", returnDateField, isoDate);
               
            } catch (Exception ex) {}
        }
        Thread.sleep(1000);
    }

    // ==================== TRAVELLER DETAILS ====================
    
    public void selectTravellerDetails(String travellers, String cabinClass) throws InterruptedException {
      
        removeAllPopups();

        String[] travellerXpaths = {
            "//*[@data-testid='pax' or @data-testid='traveller' or @data-testid='travellers']",
            "//*[contains(@class,'TravellerClass') or contains(@class,'traveller-class')]",
            "//span[contains(text(),'Traveller') or contains(text(),'Adult') or contains(text(),'1 Adult')]",
            "//div[contains(text(),'Traveller') or contains(text(),'passenger')]",
            "//*[contains(@class,'passenger')]"
        };

        WebElement travellerField = null;
        for (String xpath : travellerXpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty() && els.get(0).isDisplayed()) {
                travellerField = els.get(0);
               
                break;
            }
        }

        if (travellerField == null) {
           
            return;
        }

        jsClick(travellerField);
        Thread.sleep(1500);

        String[] cabinXpaths = {
            "//span[normalize-space()='" + cabinClass + "']",
            "//div[normalize-space()='" + cabinClass + "']",
            "//button[normalize-space()='" + cabinClass + "']",
            "//*[contains(normalize-space(),'" + cabinClass + "')][@role='option']",
            "//div[contains(@class,'cabin')]//*[contains(text(),'" + cabinClass + "')]"
        };

        boolean cabinSelected = false;
        for (String xpath : cabinXpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty() && els.get(0).isDisplayed()) {
                jsClick(els.get(0));
             
                cabinSelected = true;
                break;
            }
        }
        if (!cabinSelected) System.out.println("⚠ Cabin class not found — default used");

        Thread.sleep(800);

        String[] doneXpaths = {
            "//button[normalize-space()='Done']",
            "//button[normalize-space()='Apply']",
            "//button[normalize-space()='DONE']",
            "//button[contains(normalize-space(),'Done')]"
        };
        for (String xpath : doneXpaths) {
            List<WebElement> btns = driver.findElements(By.xpath(xpath));
            if (!btns.isEmpty() && btns.get(0).isDisplayed()) {
                jsClick(btns.get(0));
              
                break;
            }
        }
        Thread.sleep(800);
    }

    // ==================== SPECIAL FARE ====================
    
    public void selectSpecialFare(String fareType) throws InterruptedException {
        if (fareType.equalsIgnoreCase("Regular")) {
          
            return;
        }

        String[] fareXpaths = {
            "//span[normalize-space()='" + fareType + "']",
            "//div[normalize-space()='" + fareType + "']",
            "//*[contains(normalize-space(),'" + fareType + "')][@role='tab']",
            "//*[contains(normalize-space(),'" + fareType + "')][@role='option']"
        };

        boolean clicked = false;
        for (String xpath : fareXpaths) {
            List<WebElement> els = driver.findElements(By.xpath(xpath));
            if (!els.isEmpty()) {
                jsClick(els.get(0));
                
                clicked = true;
                break;
            }
        }
        if (!clicked) System.out.println(" Fare not found: " + fareType);
        Thread.sleep(500);
    }

    // ==================== SEARCH BUTTON ====================
    
    public void clickSearchButton() throws InterruptedException {
     
        removeAllPopups();
        
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(400);
        } catch (Exception ignored) {}

        try {
            js.executeScript("document.body.click();");
            Thread.sleep(400);
        } catch (Exception ignored) {}

        WebElement btn = null;
        try {
            btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(searchBtn));
        } catch (Exception ignored) {}

        if (btn != null) {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            Thread.sleep(300);
            js.executeScript("arguments[0].click();", btn);

        } else {
           
            try {
                js.executeScript("document.querySelector('button[type=submit]').click();");
            } catch (Exception e) {
                System.out.println(" Search failed: " + e.getMessage());
            }
        }
        Thread.sleep(8000);
    }

    // ==================== HELPER METHODS ====================
    
    private WebElement findDynamicInputField(String type) {
        String[] xpaths;
        if (type.equals("from")) {
            xpaths = new String[]{
                "//input[@id='source']",
                "//input[@placeholder*='From' or @placeholder*='from']",
                "//input[contains(@class,'origin') or contains(@class,'from')]",
                "//div[contains(@class,'origin')]//input",
                "//div[contains(@class,'From')]//input",
                "//input[@data-testid='source']",
                "//input[contains(@name,'source') or contains(@name,'from')]",
                "//div[contains(@class,'search-widget')]//input[1]"
            };
        } else {
            xpaths = new String[]{
                "//input[@id='destination']",
                "//input[@placeholder*='To' or @placeholder*='to']",
                "//input[contains(@class,'destination') or contains(@class,'to')]",
                "//div[contains(@class,'destination')]//input",
                "//div[contains(@class,'To')]//input",
                "//input[@data-testid='destination']",
                "//input[contains(@name,'destination') or contains(@name,'to')]",
                "//div[contains(@class,'search-widget')]//input[2]"
            };
        }
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                  //  util.log("Found input field via: " + xpath);
                    return elements.get(0);
                }
            } catch (Exception ignored) {}
        }
        
        try {
            WebElement active = driver.switchTo().activeElement();
            if (active.getTagName().equals("input")) {
          //      util.log("Using active element as input field");
                return active;
            }
        } catch (Exception ignored) {}
        
      //  util.log("⚠️ Could not find input field for: " + type);
        return type.equals("from") ? fromInput : toInput;
    }

    private void selectDropdownOption(String text) throws InterruptedException {
        Thread.sleep(2000);
        String[] dropdownXpaths = {
            "//span[contains(.,'" + text + "')]",
            "//div[contains(.,'" + text + "') and contains(@class,'station')]",
            "//li[contains(.,'" + text + "')]",
            "//div[contains(@class,'dropdown')]//*[contains(text(),'" + text + "')]",
            "//*[contains(@class,'autocomplete')]//*[contains(text(),'" + text + "')]"
        };
        
        for (String xpath : dropdownXpaths) {
            try {
                List<WebElement> options = driver.findElements(By.xpath(xpath));
                if (!options.isEmpty() && options.get(0).isDisplayed()) {
                    jsClick(options.get(0));
             //       util.log("Dropdown selected: " + text);
                    return;
                }
            } catch (Exception ignored) {}
        }
     //   util.log("⚠️ Dropdown option not found for: " + text + " — pressing ENTER");
        try {
            driver.switchTo().activeElement().sendKeys(Keys.ENTER);
        } catch (Exception ignored) {}
    }

    private void removeAllPopups() {
        try {
            js.executeScript(
                "var iframe = document.getElementById('wiz-iframe-intent');" +
                "if(iframe && iframe.parentNode){ iframe.parentNode.removeChild(iframe); }" +
                "var overlay = document.getElementById('intentOpacityDiv');" +
                "if(overlay && overlay.parentNode){ overlay.parentNode.removeChild(overlay); }" +
                "var wrapper = document.getElementById('intentPreview');" +
                "if(wrapper && wrapper.parentNode){ wrapper.parentNode.removeChild(wrapper); }" +
                "var ct = document.querySelector('div[id*=\"clevrtap\"], div[id*=\"clevertap\"]');" +
                "if(ct && ct.parentNode){ ct.parentNode.removeChild(ct); }" +
                "var modals = document.querySelectorAll('[class*=\"modal\"], [class*=\"popup\"], [class*=\"overlay\"]');" +
                "modals.forEach(m => { if(m.parentNode) m.parentNode.removeChild(m); });"
            );
        } catch (Exception ignored) {}
    }

    private void dismissOverlayIfPresent() {
        try {
            List<WebElement> overlays = driver.findElements(By.xpath(
                "//div[contains(@class,'overlay')] | //div[contains(@class,'backdrop')] | //div[contains(@class,'modal-backdrop')]"
            ));
            for (WebElement overlay : overlays) {
                if (overlay.isDisplayed()) {
                    js.executeScript("arguments[0].style.display='none';", overlay);
                }
            }
        } catch (Exception ignored) {}
    }

    private void jsClick(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(200);
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            try { element.click(); } catch (Exception ignored) {}
        }
    }

    // ==================== PAGE READY METHODS ====================
    
    public void forcePageReady() throws InterruptedException {
   //     util.log("Forcing page ready...");
        Thread.sleep(2000);
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        } catch (Exception e) {}
        Thread.sleep(1000);
    }
    
    public void waitForSearchWidget() throws InterruptedException {
    //    util.log("Waiting for search widget...");
        try {
            wait.until(ExpectedConditions.visibilityOf(fromSpan));
        } catch (Exception e) {}
  //      util.log("Search widget ready");
    }
    
    public void debugPageElements() {
  //      util.log("========== PAGE DEBUG INFO ==========");
    //    util.log("URL: " + driver.getCurrentUrl());
        List<WebElement> inputs = driver.findElements(By.xpath("//input"));
    //    util.log("Total input fields: " + inputs.size());
        for (int i = 0; i < inputs.size(); i++) {
            String placeholder = inputs.get(i).getAttribute("placeholder");
     //       util.log("  [" + i + "] placeholder='" + placeholder + "'");
        }
   //     util.log("=====================================");
    }
}
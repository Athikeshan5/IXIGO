package com.ixigo.testing.utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllUtilityFunctions {

    WebDriver driver;
    WebDriverWait wait;
    WebDriverWait shortWait;

    // Constructor
    public AllUtilityFunctions(WebDriver driver) {
        this.driver = driver;
        wait      = new WebDriverWait(driver, Duration.ofSeconds(20));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

 
    // Core waits

    /** Wait until element is visible and return it. */
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait until element is clickable and return it. */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    // Basic interactions
  

    /** Click – waits for element to be clickable first. */
    public void click(By locator) {
        waitForClickable(locator).click();
    }

    /** JavaScript click – use when normal click is intercepted. */
    public void jsClick(By locator) {
        WebElement el = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    /** Clear + type into a field. */
    public void sendKeys(By locator, String value) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(value);
    }

    /** Press ENTER on an element (used for search / defect scenario). */
    public void pressEnter(By locator) {
        waitForVisibility(locator).sendKeys(Keys.ENTER);
    }

    /** Get visible text of an element. */
    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /** Returns true if element is visible within the wait timeout. */
    public boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

  
    public void waitForSuggestionAndSelect(String text) {
        // XPath tries multiple known suggestion patterns on ixigo.com
        By suggestionList = By.xpath(
            "//*[contains(@class,'suggestion') or @role='option' or " +
            "contains(@class,'autoComplete') or contains(@class,'dropdown-item') or " +
            "contains(@class,'autocomplete')]"
        );

        try {
            // Wait up to 10 s for at least one suggestion to appear
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(suggestionList, 0));

            List<WebElement> suggestions = driver.findElements(suggestionList);
            for (WebElement suggestion : suggestions) {
                String suggestionText = suggestion.getText();
                if (suggestionText != null &&
                    suggestionText.toLowerCase().contains(text.toLowerCase())) {
                    suggestion.click();
                    return;
                }
            }

            // If no exact match, click the very first suggestion
            if (!suggestions.isEmpty()) {
                suggestions.get(0).click();
            }

        } catch (Exception e) {
            // Fallback: press ENTER if suggestion list never appeared
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.ENTER).perform();
        }
    }

   
    // Scroll helpers
  

    /** Scroll element into view before interacting. */
    public void scrollIntoView(By locator) {
        WebElement el = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", el);
        sleep(1); // brief pause after scroll
    }

    
    // Miscellaneous
   

    /** Thread sleep – use sparingly; prefer explicit waits. */
    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Mouse hover. */
    public void hover(By locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibility(locator)).perform();
    }
}
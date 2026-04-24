package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class AddCompositeFilters {

    WebDriver driver;
    WebDriverWait wait;

    public AddCompositeFilters(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= LOCATORS =================

    // Generic filter section by title (Most Popular, Payment Mode, etc.)
    private By filterSection(String sectionName) {
        return By.xpath("//p[contains(text(),'" + sectionName + "')]");
    }

    // Generic options under a section
    private By filterOptions(String sectionName) {
        return By.xpath("//p[contains(text(),'" + sectionName + "')]/following::div[@role='listitem']");
    }

    // ================= METHODS =================

    // Generic method to select any filter
    public void selectFilter(String filterType, String filterValue) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(filterSection(filterType)));

        List<WebElement> options = driver.findElements(filterOptions(filterType));

        for (WebElement option : options) {
            String text = option.getText().trim();

            if (text.toLowerCase().contains(filterValue.toLowerCase())) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                System.out.println("Selected Filter -> " + filterType + " : " + filterValue);
                break;
            }
        }
    }

    // ================= VALIDATIONS =================

    // Check if filter tag is visible (top applied filters)
    public boolean isFilterTagVisible(String filterValue) {
        try {
            By tag = By.xpath("//div[contains(@class,'flex-wrap')]//span[contains(text(),'" + filterValue + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tag)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get hotel titles (for validation)
    public List<WebElement> getHotelCards() {
        return driver.findElements(By.xpath("//div[contains(@class,'hotelCard')]"));
    }
    
}

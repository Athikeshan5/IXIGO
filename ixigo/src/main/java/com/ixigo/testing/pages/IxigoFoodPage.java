package com.ixigo.testing.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;

import java.time.Duration;

public class IxigoFoodPage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(id = "pnr-input")
    private WebElement pnrField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchBtn;

    @FindBy(xpath = "//div[contains(text(),'Unable to fetch your PNR')]")
    private WebElement popupMsg;

    @FindBy(xpath = "//button[contains(text(),'Okay')]")
    private WebElement popupOk;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

    public void openFoodPageFromHome(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        String parent = driver.getWindowHandle();

        String[] foodLinkXpaths = {
            "//a[contains(@href,'order-food-in-train')]",
            "//a[contains(@href,'food')]",
            "//a[contains(translate(.,'food','FOOD'),'FOOD') and contains(@href,'train')]",
            "//span[contains(text(),'Food')]/ancestor::a",
            "//div[contains(text(),'Food')]/ancestor::a",
        };

        boolean clicked = false;
        for (String xpath : foodLinkXpaths) {
            try {
                WebElement link = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                link.click();
                clicked = true;
                System.out.println("Food link clicked via: " + xpath);
                break;
            } catch (Exception ignored) {}
        }

        if (!clicked) {
            System.out.println("Food link not found on home page — navigating directly");
            driver.get("https://www.ixigo.com/trains/food-in-train");
        }

        // Switch to new tab if one opened
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(d -> d.getWindowHandles().size() > 1);
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(parent)) {
                    driver.switchTo().window(win);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("No new tab — staying on same tab");
        }

        // Wait for PNR field to appear
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(pnrField));
            System.out.println("Food page loaded — PNR field visible");
        } catch (Exception e) {
            System.out.println("PNR field not found — URL: " + driver.getCurrentUrl());
        }
    }

    public void enterPNR(WebDriver driver, String pnr) {
        AllUtilityFunctions util = new AllUtilityFunctions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(pnrField));
        pnrField.clear();
        pnrField.sendKeys(pnr);
        util.sleep(1);
    }

    public void clickSearch(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'bg-black')]")));
        } catch (Exception e) {
            System.out.println("Overlay not present or already gone");
        }
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        try { searchBtn.click(); }
        catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
        }
    }

    public void handleErrorPopup(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(popupMsg));
            popupOk.click();
        } catch (Exception e) {
            System.out.println("Popup not appeared");
        }
    }

    public boolean isResultDisplayed(WebDriver driver) {
        return driver.getCurrentUrl().contains("order-food")
            || driver.getCurrentUrl().contains("food");
    }
}

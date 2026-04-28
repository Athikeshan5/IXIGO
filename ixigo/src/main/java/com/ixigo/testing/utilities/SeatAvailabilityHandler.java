package com.ixigo.testing.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class SeatAvailabilityHandler {

    WebDriver driver;
    WebDriverWait wait;

    public SeatAvailabilityHandler(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // LOCATORS

   
    // These are the train class selector tabs
    By classTabs = By.xpath(
        "//div[contains(@class,'train-class-item')]" +
        " | //div[contains(@class,'class-item')]" +
        " | //li[contains(@class,'class')]"
    );

 
    By greenDayTiles = By.xpath(
        "//div[contains(@class,'day-item') and contains(@class,'green')]"
    );

    // All day tiles 
    By allDayTiles = By.xpath("//div[contains(@class,'day-item')]");

    // avail-text inside a day tile 
    By availTextInTile = By.xpath(
        "//div[contains(@class,'day-item')]//div[contains(@class,'avail-text') and contains(.,'AVL')]"
    );

    // 2-month calendar link
    By calendarLink = By.xpath(
        "//a[contains(@class,'calendar-page-link')]" +
        " | //a[contains(.,'2 Months')]" +
        " | //a[contains(.,'View 2')]"
    );

 
    // button.c-btn.u-link.enabled with text "Book"
    By bookBtn = By.xpath(
        "//div[contains(@class,'book-btn')]//button" +
        " | //button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]" +
        " | //div[contains(@class,'sticky') or contains(@class,'fixed') or contains(@class,'bottom')]//button[contains(.,'Book') or contains(.,'BOOK')]" +
        " | //button[normalize-space()='BOOK']" +
        " | //button[normalize-space()='Book']"
    );

    // PUBLIC API

    public boolean isOnSeatAvailabilityPage() {
        return driver.getCurrentUrl().contains("seat-availability");
    }

    
    public void handleSeatAvailabilityAndBook() {
        if (!isOnSeatAvailabilityPage()) return;

        System.out.println("Seat-availability page detected: " + driver.getCurrentUrl());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        sleep(2000);

        // Select a class tab that shows AVL
        boolean classSelected = selectAVLClassTab(js);
        if (!classSelected) {
            System.out.println("No specific AVL class found — using first class tab");
            try {
                List<WebElement> tabs = driver.findElements(classTabs);
                if (!tabs.isEmpty() && tabs.get(0).isDisplayed()) {
                    js.executeScript("arguments[0].click();", tabs.get(0));
                    sleep(1500);
                    System.out.println("Clicked first class tab");
                }
            } catch (Exception ignored) {}
        }

        sleep(1500);

        // Pick first green (AVL) date tile
        boolean dateClicked = selectFirstAVLDate(js);

        if (!dateClicked) {
            // Opening 2-month calendar view
            try {
                WebElement cal = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(calendarLink));
                js.executeScript("arguments[0].click();", cal);
                sleep(2000);
                System.out.println("Opened calendar view — retrying date selection");
                dateClicked = selectFirstAVLDate(js);
            } catch (Exception e) {
                System.out.println("Calendar link not found: " + e.getMessage());
            }
        }

        if (!dateClicked) {
            System.out.println("No AVL date found on seat-availability page — no tickets available");
            return;
        }

        sleep(1500);

        // Click BOOK button
        System.out.println("Clicking BOOK on seat-availability page...");
        clickBookButton(js);
        sleep(1000);
    }

    // HELPERS


    private boolean selectAVLClassTab(JavascriptExecutor js) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(classTabs));
            List<WebElement> tabs = driver.findElements(classTabs);
            for (WebElement tab : tabs) {
                try {
                    String text = tab.getText();
                    // Pick class tab that shows AVL (not WL or NOT AVL)
                    if (text.contains("AVL") && !text.contains("NOT AVL") && tab.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
                        sleep(500);
                        try { tab.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", tab); }
                        System.out.println("Clicked AVL class tab: " + text.trim().replace("\n", " "));
                        sleep(1500);
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("Class tabs not found: " + e.getMessage());
        }
        return false;
    }

    private boolean selectFirstAVLDate(JavascriptExecutor js) {
        // green day tiles 
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(greenDayTiles));
            List<WebElement> greenTiles = driver.findElements(greenDayTiles);
            for (WebElement tile : greenTiles) {
                try {
                    if (tile.isDisplayed()) {
                        // Scroll to this tile
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", tile);
                        sleep(800);
                        try { tile.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", tile); }
                        System.out.println("AVL date clicked on seat-availability page");
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("Green tiles search failed: " + e.getMessage());
        }

        // any day-item containing AVL text
        try {
            List<WebElement> avlTiles = driver.findElements(availTextInTile);
            for (WebElement avlDiv : avlTiles) {
                try {
                    // Get the parent day-item div
                    WebElement parentTile = avlDiv.findElement(
                        By.xpath("./ancestor::div[contains(@class,'day-item')]")
                    );
                    if (parentTile.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", parentTile);
                        sleep(800);
                        try { parentTile.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", parentTile); }
                        System.out.println("AVL date clicked via avail-text locator");
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("avail-text search failed: " + e.getMessage());
        }

        // any day-item that contains "AVL" in text
        try {
            List<WebElement> allTiles = driver.findElements(allDayTiles);
            for (WebElement tile : allTiles) {
                try {
                    if (tile.getText().contains("AVL") && !tile.getText().contains("NOT AVL")
                            && tile.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", tile);
                        sleep(800);
                        try { tile.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", tile); }
                        System.out.println("AVL date clicked via text scan");
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

        return false;
    }

   
    private void clickBookButton(JavascriptExecutor js) {
        // known locators
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(bookBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            sleep(500);
            try { btn.click(); }
            catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
            System.out.println("BOOK clicked on seat-availability page");
            return;
        } catch (Exception e) {
            System.out.println("Primary BOOK locator failed — scanning all buttons");
        }

        // scan all buttons
        for (WebElement btn : driver.findElements(By.tagName("button"))) {
            try {
                String t = btn.getText().trim().toUpperCase();
                if ((t.equals("BOOK") || t.equals("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    sleep(500);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("BOOK clicked via button scan");
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found on seat-availability page");
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
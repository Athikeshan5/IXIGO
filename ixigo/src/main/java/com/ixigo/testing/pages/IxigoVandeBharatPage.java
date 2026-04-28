package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IxigoVandeBharatPage {

    // locators


    @FindBy(xpath = "//input[@placeholder='Enter Origin' or @data-testid='autocomplete-input']")
    private WebElement fromInput;

    @FindBy(xpath = "//div[@data-testid='search-form-destination']")
    private WebElement toContainer;


    @FindBy(xpath = "//div[@data-testid='station-input']//input")
    private WebElement toInput;

    @FindBy(xpath = "//span[@data-testid='calendar' or (contains(@class,'body-lg') and contains(@class,'border-subbrand'))]")
    private WebElement dateSpan;


    @FindBy(xpath = "//button[contains(.,'Search Trains') or contains(.,'Search trains') or contains(.,'SEARCH')]")
    private WebElement searchBtn;

    // ACTIONS 

    public void openVandeBharatPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.get("https://www.ixigo.com/trains");
        sleep(3);

        try {
            By vandeBharatLink = By.xpath(
                "//a[contains(@href,'vande-bharat')]" +
                " | //div[contains(text(),'Vande Bharat')]/ancestor::a" +
                " | //span[contains(text(),'Vande Bharat')]/ancestor::a"
            );
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(vandeBharatLink));
            link.click();
            sleep(3);
            System.out.println("Clicked Vande Bharat link. URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Vande Bharat link not found: " + e.getMessage());
            driver.get("https://www.ixigo.com/trains/vande-bharat-express-trains");
            sleep(3);
        }

        // Dismiss any popup overlay
        dismissOverlay(driver);

        // Wait for the search form to be ready
        try {
            wait.until(ExpectedConditions.visibilityOf(fromInput));
            System.out.println("Vande Bharat search form loaded — from input visible");
        } catch (Exception e) {
            System.out.println("Search form not visible after navigation: " + driver.getCurrentUrl());
        }
    }

    public void enterFrom(WebDriver driver, String from) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Try primary locator: placeholder="Enter Origin"
        WebElement el = null;
        String[] fromXpaths = {
            "//input[@placeholder='Enter Origin']",
            "//input[@data-testid='autocomplete-input']",
            "//div[contains(@class,'origin') or contains(@class,'from')]//input",
            "//label[contains(text(),'From')]/..//input",
            "//input[@type='search'][1]"
        };

        for (String xpath : fromXpaths) {
            try {
                el = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                if (el.isDisplayed()) break;
            } catch (Exception ignored) {}
        }

        if (el == null) {
            System.out.println("From input not found with any locator!");
            return;
        }

        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            sleep(1);
            el.click();
            sleep(1);
            el.clear();
            el.sendKeys(from);
            sleep(2);
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", el);
            sleep(1);
            js.executeScript("arguments[0].value='';", el);
            js.executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
                el, from
            );
            sleep(2);
        }

        // Select FIRST option from autocomplete dropdown
        selectFirstAutocompleteOption(driver);
        System.out.println("From entered: " + from);
        sleep(1);
    }

    public void enterTo(WebDriver driver, String to) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        sleep(1);

        // Click the destination container first to open input
        String[] toContainerXpaths = {
            "//div[@data-testid='search-form-destination']",
            "//div[contains(@class,'search-form-destination')]",
            "//div[@data-testid='station-input'][2]",
            "//input[@placeholder='Enter Destination']",
            "//input[@type='search'][2]",
            "//label[contains(text(),'To')]/..//input"
        };

        WebElement toEl = null;
        for (String xpath : toContainerXpaths) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                sleep(1);
                try { el.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", el); }
                sleep(1);

                // Now look for the actual text input inside
                try {
                    toEl = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@data-testid='station-input']//input | //input[@placeholder='Enter Destination'] | //input[@type='search'][2]")
                        ));
                    if (toEl.isDisplayed()) break;
                } catch (Exception ex) {
                    toEl = el; // fallback: use container itself
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (toEl == null) {
            System.out.println("To input not found!");
            return;
        }

        try {
            toEl.clear();
            toEl.sendKeys(to);
            sleep(2);
        } catch (Exception e) {
            js.executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
                toEl, to
            );
            sleep(2);
        }

        // Select FIRST option from autocomplete dropdown
        selectFirstAutocompleteOption(driver);
        System.out.println("To entered: " + to);
        sleep(1);
    }

    public void selectDate(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        sleep(1);

        // Click the date span to open calendar
        try {
            WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(dateSpan));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", datePicker);
            sleep(1);
            try { datePicker.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", datePicker); }
            sleep(2);
            System.out.println("Date picker opened");
        } catch (Exception e) {
            System.out.println("Date span not found, trying container: " + e.getMessage());
            try {
                WebElement container = driver.findElement(By.xpath("//div[@data-testid='calendar'] | //div[contains(@class,'date')]//span[contains(@class,'body-lg')]"));
                js.executeScript("arguments[0].click();", container);
                sleep(2);
            } catch (Exception ex) {
                System.out.println("Calendar open failed: " + ex.getMessage());
                return;
            }
        }

        // Wait for calendar to appear
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'calendar') or @data-testid='calendar'] | //table[contains(@class,'calendar')]")
            ));
        } catch (Exception ignored) {}

        // Select a date with green dot (available) — or just first clickable future date
        boolean dateClicked = false;

        // click a td with green dot (Available)
        String[] availableDateXpaths = {
            "//td[.//div[@style[contains(.,'66, 160, 71')]] and not(contains(@class,'disabled')) and not(contains(@class,'past'))]",
            "//td[contains(@class,'available') and not(contains(@class,'disabled'))]",
            "//div[contains(@class,'day') and contains(@class,'available') and not(contains(@class,'disabled'))]",
            "//table//td[not(contains(@class,'disabled')) and not(contains(@class,'past')) and normalize-space(text())!='']"
        };

        for (String xpath : availableDateXpaths) {
            try {
                List<WebElement> dates = driver.findElements(By.xpath(xpath));
                int startIdx = dates.size() > 3 ? 2 : 0;
                for (int i = startIdx; i < dates.size(); i++) {
                    WebElement d = dates.get(i);
                    try {
                        if (d.isDisplayed()) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", d);
                            sleep(1);
                            try { d.click(); } catch (Exception ex) { js.executeScript("arguments[0].click();", d); }
                            System.out.println("Date clicked: " + d.getText().trim());
                            dateClicked = true;
                            sleep(1);
                            break;
                        }
                    } catch (Exception ignored) {}
                }
                if (dateClicked) break;
            } catch (Exception ignored) {}
        }

        if (!dateClicked) {
            System.out.println("Could not click a date — calendar may have closed or dates unavailable");
        }
        sleep(1);
    }

    public void clickSearch(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String[] searchXpaths = {
            "//button[@data-testid='book-train-tickets']",
            "//button[contains(.,'Search Trains') or contains(.,'Search trains')]",
            "//button[@type='submit' and (contains(.,'Search') or contains(.,'SEARCH'))]",
            "//button[contains(@class,'brand') and contains(.,'Search')]",
            "//button[contains(.,'Search')]"
        };

        for (String xpath : searchXpaths) {
            try {
                WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                sleep(1);
                try { btn.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                System.out.println("Search clicked via: " + xpath);
                sleep(4);
                System.out.println("After search URL: " + driver.getCurrentUrl());
                return;
            } catch (Exception ignored) {}
        }

        System.out.println("Search button not found — trying Enter key on from input");
        try {
            driver.findElement(By.xpath("//input[@placeholder='Enter Origin' or @data-testid='autocomplete-input']"))
                .sendKeys(Keys.ENTER);
            sleep(4);
        } catch (Exception e) {
            System.out.println("Search fallback also failed: " + e.getMessage());
        }
    }


    public void selectAVLAndBook(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        sleep(4); // wait for results to load

        System.out.println("Results page URL: " + driver.getCurrentUrl());

        // ── STEP 1: Click an available date from date strip ──────────────────
        boolean dateClicked = clickAvailableDateInStrip(driver, js, wait);
        if (dateClicked) {
            sleep(3);
            System.out.println("Available date selected from strip");
        }

        // Find and click AVL class card 
        boolean classClicked = false;
        String[] avlCardXpaths = {
            "//div[contains(@class,'avail-card') and contains(@class,'bg-success-subtle')]",
            "//div[contains(@class,'border-success-subtle') and contains(@class,'bg-success-subtle')]",
            "//div[contains(@class,'_avail-card')]//div[contains(@class,'body-sm') and contains(.,'AVL')]/..",

            "//div[contains(@class,'avail') and contains(.,'AVL') and not(contains(.,'NOT AVL')) and not(contains(.,'WL'))]",
            "//*[contains(@class,'body-sm') and contains(@class,'_prediction') and contains(.,'AVL') and not(contains(.,'WL'))]/.."
        };

        for (String xpath : avlCardXpaths) {
            try {
                List<WebElement> cards = driver.findElements(By.xpath(xpath));
                for (WebElement card : cards) {
                    try {
                        if (card.isDisplayed()) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                            sleep(1);
                            try { card.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", card); }
                            System.out.println("Clicked AVL class card: " + card.getText().trim().replace("\n", " ").substring(0, Math.min(40, card.getText().length())));
                            classClicked = true;
                            sleep(2);
                            break;
                        }
                    } catch (Exception ignored) {}
                }
                if (classClicked) break;
            } catch (Exception ignored) {}
        }

        if (!classClicked) {
            System.out.println("No AVL class card found — attempting direct BOOK click");
        }

        // Click the Book button 

        sleep(2);
        clickBookButton(driver, js, wait);
        sleep(3);
        System.out.println("After BOOK click URL: " + driver.getCurrentUrl());
    }

    // PRIVATE HELPERS

    
    private void selectFirstAutocompleteOption(WebDriver driver) {
        // Wait a moment for dropdown to appear
        sleep(1);

        String[] dropdownItemXpaths = {

            "//div[contains(@class,'flex') and contains(@class,'relative') and contains(@class,'gap-10') and contains(@class,'items-center') and contains(@class,'group') and contains(@class,'list-sm')]",
            "//div[contains(@class,'no-scrollbar')]//div[@role='listitem'][1]",
            "//div[@role='listitem'][1]",
            "//ul[contains(@class,'autocomplete') or contains(@class,'suggestion')]//li[1]",
            "//div[contains(@class,'result') or contains(@class,'suggestion') or contains(@class,'option')][1]",
            "//div[contains(@class,'overflow') and contains(@class,'rounded')]//div[contains(@class,'cursor-pointer')][1]",
            "//div[contains(@class,'cursor-pointer') and contains(@class,'py-10')][1]"
        };

        for (String xpath : dropdownItemXpaths) {
            try {
                WebElement firstOption = new WebDriverWait(driver, Duration.ofSeconds(6))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                try {
                    firstOption.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
                }
                System.out.println("First autocomplete option clicked via: " + xpath);
                sleep(1);
                return;
            } catch (Exception ignored) {}
        }

        // press arrow down + enter to select first item
        try {
            WebElement active = driver.switchTo().activeElement();
            active.sendKeys(Keys.ARROW_DOWN);
            sleep(1);
            active.sendKeys(Keys.ENTER);
            System.out.println("First autocomplete option selected via ARROW_DOWN + ENTER");
        } catch (Exception e) {
            System.out.println("Autocomplete first-option fallback also failed: " + e.getMessage());
        }
    }

    private boolean clickAvailableDateInStrip(WebDriver driver, JavascriptExecutor js, WebDriverWait wait) {
        String[] dateStripXpaths = {
            "//div[contains(@class,'_cache-card') and not(contains(@class,'disabled'))]",
            "//div[contains(@class,'cache-card') and not(contains(@class,'disabled'))]",
            "//div[contains(@class,'avail-card-wrapper') or @data-key]",
            "//div[contains(@class,'overflow-auto')]//div[contains(@class,'w-80')]"
        };

        for (String xpath : dateStripXpaths) {
            try {
                List<WebElement> dateCells = driver.findElements(By.xpath(xpath));
                // Try to find one with "Available" indicator (green dot)
                for (WebElement cell : dateCells) {
                    try {
                        String cellText = cell.getText();
                        if (cellText.contains("Available") || cellText.contains("Filling Fast") || cellText.contains("Few Seats")) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
                            sleep(1);
                            try { cell.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", cell); }
                            return true;
                        }
                    } catch (Exception ignored) {}
                }
                // Fallback: click any non-disabled cell
                if (!dateCells.isEmpty()) {
                    for (int i = 0; i < Math.min(dateCells.size(), 5); i++) {
                        try {
                            WebElement cell = dateCells.get(i);
                            if (cell.isDisplayed()) {
                                js.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
                                sleep(1);
                                js.executeScript("arguments[0].click();", cell);
                                return true;
                            }
                        } catch (Exception ignored) {}
                    }
                }
            } catch (Exception ignored) {}
        }
        return false;
    }

    private void clickBookButton(WebDriver driver, JavascriptExecutor js, WebDriverWait wait) {
        String[] bookXpaths = {

            "//button[contains(@class,'border-success-outline') and contains(.,'Book')]",
            "//button[contains(@class,'text-success') and contains(.,'Book')]",

            "//div[contains(@class,'avail')]//button[contains(.,'Book')]",
            "//button[contains(@class,'body-xs') and contains(.,'Book')]",

            "//div[contains(@class,'sticky') or contains(@class,'bottom')]//button[contains(.,'Book')]",

            "//button[normalize-space()='Book']",
            "//button[starts-with(normalize-space(),'Book')]",
            "//button[contains(.,'Book') and not(contains(.,'Bookmark'))]"
        };

        for (String xpath : bookXpaths) {
            try {
                List<WebElement> btns = driver.findElements(By.xpath(xpath));
                for (WebElement btn : btns) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        sleep(1);
                        try { btn.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("BOOK clicked via: " + xpath);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }

        // Last resort: scan all buttons
        List<WebElement> allBtns = driver.findElements(By.tagName("button"));
        for (WebElement btn : allBtns) {
            try {
                String t = btn.getText().trim();
                if (t.startsWith("Book") && !t.contains("Bookmark") && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("BOOK clicked via full button scan: " + t);
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — login popup may appear directly");
    }

    private void dismissOverlay(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("wiz-iframe-intent"));
            driver.findElement(By.id("closeButton")).click();
            driver.switchTo().defaultContent();
            sleep(1);
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
    }

    private void sleep(int sec) {
        try { Thread.sleep(sec * 1000L); } catch (Exception e) { Thread.currentThread().interrupt(); }
    }
}
package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IxigoVandeBharatPage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(xpath = "//input[@placeholder='Leaving from']")
    private WebElement fromInput;

    @FindBy(xpath = "//input[@placeholder='Going to']")
    private WebElement toInput;

    @FindBy(xpath = "//div[contains(@class,'date') and not(contains(@class,'date-tile'))]")
    private WebElement dateField;

    @FindBy(xpath = "//button[contains(@class,'rd-next')]")
    private WebElement nextBtn;

    @FindBy(xpath = "//td[contains(@class,'rd-day-body')]/div[text()='18']")
    private WebElement dateLocator;

    @FindBy(css = "div.search button")
    private WebElement searchBtn;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

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
            sleep(2);
            System.out.println("Clicked Vande Bharat link. URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Vande Bharat link not found: " + e.getMessage());
        }

        try {
            wait.until(ExpectedConditions.visibilityOf(fromInput));
            System.out.println("Search form loaded successfully");
        } catch (Exception e) {
            System.out.println("Search form not visible, current URL: " + driver.getCurrentUrl());
        }
    }

    public void enterFrom(WebDriver driver, String from) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement el = wait.until(ExpectedConditions.visibilityOf(fromInput));
        el.click();
        el.clear();
        el.sendKeys(from);
        sleep(2);

        try {
            By dropdownItem = By.xpath(
                "//ul[contains(@class,'autocomplete') or contains(@class,'suggestion')]//li[contains(.,'" + from + "')]" +
                " | //div[contains(@class,'result-row') and contains(.,'" + from + "')]"
            );
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(dropdownItem)).click();
        } catch (Exception e) {
            el.sendKeys(Keys.ARROW_DOWN);
            el.sendKeys(Keys.ENTER);
        }
        System.out.println("From entered: " + from);
    }

    public void enterTo(WebDriver driver, String to) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement el = wait.until(ExpectedConditions.visibilityOf(toInput));
        el.click();
        el.clear();
        el.sendKeys(to);
        sleep(2);

        try {
            By dropdownItem = By.xpath(
                "//ul[contains(@class,'autocomplete') or contains(@class,'suggestion')]//li[contains(.,'" + to + "')]" +
                " | //div[contains(@class,'result-row') and contains(.,'" + to + "')]"
            );
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(dropdownItem)).click();
        } catch (Exception e) {
            el.sendKeys(Keys.ARROW_DOWN);
            el.sendKeys(Keys.ENTER);
        }
        System.out.println("To entered: " + to);
    }

    public void selectDate(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            WebElement dateBox = wait.until(ExpectedConditions.elementToBeClickable(dateField));
            dateBox.click();
            sleep(2);

            while (true) {
                try {
                    WebElement next = driver.findElement(By.xpath("//button[contains(@class,'rd-next')]"));
                    if (!next.isEnabled()) break;
                    next.click();
                    sleep(1);
                } catch (Exception e) { break; }
            }

            WebElement date = wait.until(ExpectedConditions.elementToBeClickable(dateLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);
            sleep(1);
            dateBox.click();
        } catch (Exception e) {
            System.out.println("Date selection skipped: " + e.getMessage());
        }
    }

    public void clickSearch(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        } catch (Exception e) {
            String[] fallbacks = {
                "//button[contains(.,'Search')]",
                "//button[@type='submit']"
            };
            for (String xpath : fallbacks) {
                try {
                    driver.findElement(By.xpath(xpath)).click();
                    return;
                } catch (Exception ignored) {}
            }
        }
        sleep(3);
        System.out.println("Search clicked. URL: " + driver.getCurrentUrl());
    }

    public void selectAVLAndBook(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        sleep(3);

        boolean clicked = false;

        for (int scroll = 0; scroll < 8 && !clicked; scroll++) {
            String[] avlXpaths = {
                "//div[contains(@class,'avail-class') and contains(normalize-space(.),'AVL')]",
                "//div[contains(@class,'avail-class') and .//*[contains(text(),'AVL')]]",
                "//span[starts-with(normalize-space(.),'AVL') and string-length(normalize-space(.)) < 10]",
                "//div[starts-with(normalize-space(text()),'AVL') and string-length(normalize-space(text())) < 10]",
                "//*[normalize-space(text())='AVL']"
            };

            for (String xpath : avlXpaths) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(xpath));
                    for (WebElement el : elements) {
                        if (el.isDisplayed() && el.isEnabled()) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                            sleep(1);
                            try { el.click(); }
                            catch (Exception e) { js.executeScript("arguments[0].click();", el); }
                            System.out.println("Clicked AVL: " + el.getText().trim());
                            clicked = true;
                            break;
                        }
                    }
                    if (clicked) break;
                } catch (Exception ignored) {}
            }

            if (!clicked) {
                js.executeScript("window.scrollBy(0, 600)");
                sleep(2);
            }
        }

        if (!clicked) {
            System.out.println("No AVL element found — clicking BOOK directly for login popup");
        }

        sleep(2);
        clickBookButton(driver, js);
    }

    private void clickBookButton(WebDriver driver, JavascriptExecutor js) {
        System.out.println("Clicking BOOK button...");

        String[] bookXpaths = {
            "//div[contains(@class,'train-status-item')]//button[contains(.,'Book')]",
            "//button[normalize-space()='Book']",
            "//button[normalize-space()='BOOK']",
            "//button[normalize-space()='Book Now']",
            "//button[contains(.,'Book') and not(contains(.,'Bookmarks'))]",
            "//a[contains(.,'Book') and not(contains(.,'Bookmarks'))]"
        };

        for (String xpath : bookXpaths) {
            try {
                WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                sleep(1);
                try { btn.click(); }
                catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                System.out.println("BOOK clicked via: " + xpath);
                return;
            } catch (Exception ignored) {}
        }

        List<WebElement> allBtns = driver.findElements(By.tagName("button"));
        for (WebElement btn : allBtns) {
            try {
                String t = btn.getText().trim();
                if ((t.equalsIgnoreCase("BOOK") || t.equalsIgnoreCase("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("BOOK clicked via full scan");
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — login popup may appear without clicking BOOK");
    }

    private void sleep(int sec) {
        try { Thread.sleep(sec * 1000L); } catch (Exception e) { Thread.currentThread().interrupt(); }
    }
}

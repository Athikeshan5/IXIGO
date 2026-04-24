package com.ixigo.testing.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.SeatAvailabilityHandler;

public class IxigoTrainNamePage {

    // ── @FindBy locators (PageFactory pattern) ────────────────────────────────

    @FindBy(xpath = "//a[contains(@href,'/trains/name-number')]")
    private WebElement searchByName;

    @FindBy(xpath = "//input[@placeholder='Search trains by name or number']")
    private WebElement trainInput;

    @FindBy(xpath = "(//div[contains(@class,'flex pb-15')])[1]")
    private WebElement firstTrain;

    @FindBy(xpath = "//a[contains(@class,'calendar-page-link')]")
    private WebElement viewCalendar;

    // ── ACTIONS (driver passed as parameter, matching teammate pattern) ────────

    public void goToTrainNamePage(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        // WebElement overload: waitForClickable(driver, element, seconds)
        util.waitForClickable(driver, searchByName, 20);
        searchByName.click();
    }

    public void enterTrainName(WebDriver driver, String train) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // WebElement is already injected by PageFactory — wait on the element, not By
        wait.until(ExpectedConditions.visibilityOf(trainInput));
        trainInput.click();
        trainInput.clear();
        trainInput.sendKeys(train);
        util.sleep(2);
        trainInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void selectTrain(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Wait for element visibility, then click directly
        wait.until(ExpectedConditions.visibilityOf(firstTrain));
        firstTrain.click();
    }

    public void selectAvailableDate(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        util.sleep(2);

        SeatAvailabilityHandler seatHandler = new SeatAvailabilityHandler(driver);
        if (seatHandler.isOnSeatAvailabilityPage()) {
            System.out.println("Already on seat-availability — delegating to handler");
            seatHandler.handleSeatAvailabilityAndBook();
            return;
        }

        js.executeScript("window.scrollBy(0, 400)");
        util.sleep(2);

        By avlClassBoxLocator = By.xpath(
            "//div[contains(@class,'train-class-main') and contains(@class,'green')]");
        By dayItemsAll = By.xpath("//div[contains(@class,'day-item')]");

        boolean classClicked = false;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(avlClassBoxLocator));
            List<WebElement> avlBoxes = driver.findElements(avlClassBoxLocator);
            for (WebElement box : avlBoxes) {
                try {
                    if (box.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", box);
                        util.sleep(1);
                        try { box.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", box); }
                        System.out.println("Clicked AVL class box: " + box.getText().trim().replace("\n", " "));
                        classClicked = true;
                        util.sleep(2);
                        break;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("AVL class box not found: " + e.getMessage());
        }

        if (!classClicked) {
            System.out.println("No AVL class box found on results page — checking day tiles directly");
            util.sleep(2);
            js.executeScript("window.scrollBy(0, 500)");
            util.sleep(1);

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(dayItemsAll));
                List<WebElement> days = driver.findElements(dayItemsAll);
                for (WebElement day : days) {
                    try {
                        if (day.getText().contains("AVL") && !day.getText().contains("NOT AVL")) {
                            js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                            util.sleep(1);
                            try { day.click(); }
                            catch (Exception e) { js.executeScript("arguments[0].click();", day); }
                            System.out.println("Selected AVL date tile directly");
                            classClicked = true;
                            util.sleep(2);
                            break;
                        }
                    } catch (Exception ignored) {}
                }

                if (!classClicked) {
                    System.out.println("No AVL in strip → Clicking 2 Months Calendar");
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(viewCalendar));
                        js.executeScript("arguments[0].click();", viewCalendar);
                        util.sleep(3);
                        List<WebElement> fullDates = driver.findElements(dayItemsAll);
                        for (WebElement day : fullDates) {
                            if (day.getText().contains("AVL") && !day.getText().contains("NOT AVL")) {
                                js.executeScript("arguments[0].scrollIntoView({block:'center'});", day);
                                util.sleep(1);
                                js.executeScript("arguments[0].click();", day);
                                System.out.println("Clicked AVL date from full calendar");
                                classClicked = true;
                                util.sleep(2);
                                break;
                            }
                        }
                    } catch (Exception ignored) {}
                }
            } catch (Exception e) {
                System.out.println("Day tiles not found: " + e.getMessage());
            }

            if (!classClicked) {
                System.out.println("No AVL date found — train may be fully booked. Proceeding to assert.");
                return;
            }
        }

        util.sleep(2);
        if (seatHandler.isOnSeatAvailabilityPage()) {
            System.out.println("Redirected to seat-availability after class click");
            seatHandler.handleSeatAvailabilityAndBook();
        }
    }

    public void clickBook(WebDriver driver) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            By loginPopup = By.xpath(
                "//div[contains(@class,'ixigo-login')] | //*[contains(text(),'Log in to ixigo')]"
            );
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPopup));
            System.out.println("Login popup already visible — skipping BOOK click");
            return;
        } catch (Exception ignored) {}

        System.out.println("Clicking BOOK button...");

        String[] bookXpaths = {
            "//div[contains(@class,'book-btn')]//button",
            "//button[contains(@class,'c-btn') and contains(@class,'u-link') and (normalize-space()='Book' or normalize-space()='BOOK')]",
            "//div[contains(@class,'sticky') or contains(@class,'bottom') or contains(@class,'fixed')]//button[contains(.,'Book') or contains(.,'BOOK')]",
            "//button[normalize-space()='BOOK']",
            "//button[normalize-space()='Book']",
            "//button[normalize-space()='Book Now']",
            "//button[contains(text(),'BOOK')]"
        };

        for (String xpath : bookXpaths) {
            try {
                List<WebElement> btns = driver.findElements(By.xpath(xpath));
                for (WebElement btn : btns) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        util.sleep(1);
                        try { btn.click(); }
                        catch (Exception e) { js.executeScript("arguments[0].click();", btn); }
                        System.out.println("Clicked BOOK via: " + xpath);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }

        for (WebElement btn : driver.findElements(By.tagName("button"))) {
            try {
                String t = btn.getText().trim().toUpperCase();
                if ((t.equals("BOOK") || t.equals("BOOK NOW")) && btn.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    util.sleep(1);
                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("Clicked BOOK via full scan");
                    return;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("BOOK button not found — login popup may appear directly");
    }
}

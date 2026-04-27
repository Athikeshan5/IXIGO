package com.ixigo.testing.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TrackTicketPage {

    @FindBy(xpath = "//span[.='Track Ticket']")
    private WebElement trackticket;

    @FindBy(xpath = "//input[@placeholder='Please enter the booking ID']")
    private WebElement busID;

    @FindBy(xpath = "//input[@placeholder='Please enter the mobile number']")
    private WebElement mobileno;

    @FindBy(xpath = "//a[@class='btn btn-search dark filled primary md rounded-md inactive button']")
    private WebElement ticketDetailsBtn;

    public void clickTrackTicket(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(trackticket));
        trackticket.click();
    }

    public void enterBusID(WebDriver driver, String bookingId) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOf(busID));
        busID.clear();
        busID.sendKeys(bookingId);
    }

    public void enterMobileNo(WebDriver driver, String mobile) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOf(mobileno));
        mobileno.clear();
        mobileno.sendKeys(mobile);
    }

    public void clickTicketDetails(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(ticketDetailsBtn));
        ticketDetailsBtn.click();
    }

    public String getStatusMessage(WebDriver driver) {
        String[] errorLocators = {
            "//*[contains(@class,'error')]",
            "//*[contains(@class,'alert')]",
            "//*[contains(@class,'message')]",
            "//*[contains(@class,'toast')]",
            "//*[contains(@class,'notification')]",
            "//*[contains(text(),'not found')]",
            "//*[contains(text(),'invalid')]",
            "//*[contains(text(),'No booking')]",
            "//*[contains(text(),'incorrect')]"
        };

        for (String locator : errorLocators) {
            try {
                WebElement msg = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                String text = msg.getText().trim();
                if (!text.isEmpty() && !text.equalsIgnoreCase("Ticket Details")) {
                    System.out.println("Error message found: " + text);
                    return text;
                }
            } catch (Exception ignored) {}
        }

        System.out.println("No specific error message found on page");
        return "No error message found";
    }
}
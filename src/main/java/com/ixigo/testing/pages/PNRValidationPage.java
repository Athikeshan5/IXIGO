package com.ixigo.testing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PNRValidationPage {

    
    @FindBy(linkText = "PNR No. is not valid")
    private WebElement pnrmessage;

    public WebElement getPnrmessage() {
        return pnrmessage;
    }

    public boolean validpnr(WebDriver driver, String name) {
       
        String f = driver.getCurrentUrl();
        return f.contains(name);
    }
}
package com.ixigo.testing.utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.OneWayFlightPage;


public class Pages {
     public static OneWayFlightPage OFP;
     
     public static void allPages(WebDriver driver) {
    	 OFP=PageFactory.initElements(driver, OneWayFlightPage.class);
     }

	
	
}

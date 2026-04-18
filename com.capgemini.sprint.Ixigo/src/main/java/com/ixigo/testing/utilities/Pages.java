package com.ixigo.testing.utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.FlightFilterPage;
import com.ixigo.testing.pages.FlightdetailsPage;
import com.ixigo.testing.pages.OneWayFlightPage;



public class Pages {
     public static OneWayFlightPage OFP;
     public static FlightFilterPage FFP;
     public static FlightdetailsPage FDP;
     
     public static void allPages(WebDriver driver) {
    	 OFP=PageFactory.initElements(driver,OneWayFlightPage.class);
    	 FFP=PageFactory.initElements(driver,FlightFilterPage.class);
    	 FDP=PageFactory.initElements(driver,FlightdetailsPage.class);
     }

	
	
}

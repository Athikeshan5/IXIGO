package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v141.page.Page;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testingPages.AddHotelToWishListPage;
import com.ixigo.testingPages.HotelBookingPage;

//import com.ixigo.testing.pages.HomePage;
//import com.ixigo.testing.pages.PNRStatusPage;
//import com.ixigo.testing.pages.PNRValidationPage;
//import com.ixigo.testing.pages.RunningStatusPage;
//import com.ixigo.testing.pages.SeatAvailabilityFilterPage;
//import com.ixigo.testing.pages.TrainRuningStatusPage;
//import com.ixigo.testing.pages.TrainSeatAvailabilityPage;
//import com.ixigo.testing.pages.TrainsPage;

public class Pages {
	
	
	

	
	public static ThreadLocal<HotelBookingPage> HBP = new ThreadLocal<>();
    public static ThreadLocal<AddHotelToWishListPage> WL = new ThreadLocal<>();
    
    public static void allPages(WebDriver driver) {
        // Use .set() to store a unique instance for the current thread
    	HBP .set(PageFactory.initElements(driver, HotelBookingPage.class));
		WL .set(PageFactory.initElements(driver, AddHotelToWishListPage.class));

	}
}

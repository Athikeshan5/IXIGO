package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v141.page.Page;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testingPages.AddCompositeFilters;
import com.ixigo.testingPages.AddHotelToWishListPage;
import com.ixigo.testingPages.GuestDetailsPage;
import com.ixigo.testingPages.HotelBookingPage;
import com.ixigo.testingPages.HotelSortingPage;
import com.ixigo.testingPages.HotelWithFilters;
import com.ixigo.testingPages.ListOfHotelPage;
import com.ixigo.testingPages.PaymentPage;
import com.ixigo.testingPages.ReservePage;

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
    public static ThreadLocal<ListOfHotelPage> LHP = new ThreadLocal<>();
    public static ThreadLocal<ReservePage> RP = new ThreadLocal<>();
    public static ThreadLocal<GuestDetailsPage> GDP = new ThreadLocal<>();
    public static ThreadLocal<PaymentPage> PP = new ThreadLocal<>();
    public static ThreadLocal<HotelSortingPage> HSP = new ThreadLocal<>();
    public static ThreadLocal<HotelWithFilters> FH = new ThreadLocal<>();
    public static ThreadLocal<AddCompositeFilters> ACF = new ThreadLocal<>();
    public static void allPages(WebDriver driver) {
        // Use .set() to store a unique instance for the current thread
    	HBP .set(PageFactory.initElements(driver, HotelBookingPage.class));
		WL .set(PageFactory.initElements(driver, AddHotelToWishListPage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        RP.set(PageFactory.initElements(driver, ReservePage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        HSP.set(PageFactory.initElements(driver, HotelSortingPage.class));
        FH.set(PageFactory.initElements(driver, HotelWithFilters.class));
        ACF.set(PageFactory.initElements(driver, AddCompositeFilters.class));
        
	}
}

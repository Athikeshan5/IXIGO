package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.FlightFilterPage;
import com.ixigo.testing.pages.FlightdetailsPage;
import com.ixigo.testing.pages.HomePage;
import com.ixigo.testing.pages.OneWayFlightPage;
import com.ixigo.testing.pages.PNRStatusPage;
import com.ixigo.testing.pages.PNRValidationPage;
import com.ixigo.testing.pages.RunningStatusPage;
import com.ixigo.testing.pages.SeatAvailabilityFilterPage;
import com.ixigo.testing.pages.TakalTicketBookingPage;
import com.ixigo.testing.pages.TatkalPage;
import com.ixigo.testing.pages.TrainRuningStatusPage;
import com.ixigo.testing.pages.TrainSeatAvailabilityPage;
import com.ixigo.testing.pages.TrainsPage;

public class Pages {

	public static ThreadLocal<HomePage> hp = new ThreadLocal<>();
    public static ThreadLocal<TrainsPage> tp = new ThreadLocal<>();
    public static ThreadLocal<PNRStatusPage> pnrs = new ThreadLocal<>();
    public static ThreadLocal<PNRValidationPage> pnrv = new ThreadLocal<>();
    public static ThreadLocal<TrainRuningStatusPage> trs = new ThreadLocal<>();
    public static ThreadLocal<RunningStatusPage> rs = new ThreadLocal<>();
    public static ThreadLocal<TrainSeatAvailabilityPage> ts = new ThreadLocal<>();
    public static ThreadLocal<SeatAvailabilityFilterPage> saf = new ThreadLocal<>();
    public static ThreadLocal<TatkalPage> tkp = new ThreadLocal<>();
    public static ThreadLocal<TakalTicketBookingPage> ttb = new ThreadLocal<>();
    
    public static void allPages(WebDriver driver) {
        // Use .set() to store a unique instance for the current thread
        hp.set(PageFactory.initElements(driver, HomePage.class));
        tp.set(PageFactory.initElements(driver, TrainsPage.class));
        pnrs.set(PageFactory.initElements(driver, PNRStatusPage.class));
        pnrv.set(PageFactory.initElements(driver, PNRValidationPage.class));
        trs.set(PageFactory.initElements(driver, TrainRuningStatusPage.class));
        rs.set(PageFactory.initElements(driver, RunningStatusPage.class));
        ts.set(PageFactory.initElements(driver, TrainSeatAvailabilityPage.class));
        saf.set(PageFactory.initElements(driver, SeatAvailabilityFilterPage.class));
        tkp.set(PageFactory.initElements(driver, TatkalPage.class));
        ttb.set(PageFactory.initElements(driver, TakalTicketBookingPage.class));
    }
}

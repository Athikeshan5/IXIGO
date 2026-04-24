package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.*;


/**
 * Pages — thread-safe registry of Page Object instances.
 *
 * WHY ThreadLocal here?
 *   In parallel mode, Thread-1 and Thread-2 both call Pages.allPages().
 *   Without ThreadLocal, Thread-2's call would overwrite the static field
 *   that Thread-1 is actively using — causing NullPointerExceptions or
 *   Thread-1 suddenly operating on Thread-2's browser window.
 *
 *   ThreadLocal<T> gives each thread its own independent copy of the field.
 *   Thread-1's hp.get() always returns Thread-1's HomePage instance.
 *   Thread-2's hp.get() always returns Thread-2's HomePage instance.
 *
 * HOW to use in step definitions:
 *   Pages.hp.get().clickTrains(driver);    // Thread-safe: gets THIS thread's HomePage
 *   Pages.tp.get().clickseat();            // Thread-safe: gets THIS thread's TrainsPage
 */
public class Pages {

    // One page object per thread, stored in ThreadLocal
    public static final ThreadLocal<HomePage>               hp  = new ThreadLocal<>();
    public static final ThreadLocal<TrainsPage>             tp  = new ThreadLocal<>();
    public static final ThreadLocal<PNRStatusPage>          pnrs = new ThreadLocal<>();
    public static final ThreadLocal<PNRValidationPage>      pnrv = new ThreadLocal<>();
    public static final ThreadLocal<TrainRuningStatusPage>  trs  = new ThreadLocal<>();
    public static final ThreadLocal<RunningStatusPage>      rs   = new ThreadLocal<>();
    public static final ThreadLocal<TrainSeatAvailabilityPage> ts = new ThreadLocal<>();
    public static final ThreadLocal<SeatAvailabilityFilterPage> saf = new ThreadLocal<>();
    public static final ThreadLocal<TatkalPage>             tkp  = new ThreadLocal<>();
    public static final ThreadLocal<TakalTicketBookingPage> ttb  = new ThreadLocal<>();
    public static ThreadLocal<HomeResultPage> hrp=new ThreadLocal<>();
    public static ThreadLocal<BusSearchPage> bsp=new ThreadLocal<>();
    public static ThreadLocal<SeatSelectionPage> ssp=new ThreadLocal<>();
    public static ThreadLocal<PassengerPage> pp=new ThreadLocal<>();
    public static ThreadLocal<HomePage> lp=new ThreadLocal<>();
    public static final ThreadLocal<IxigoFoodPage>        foodPage      = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPaymentPage>     paymentPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPlatformPage>    platformPage  = new ThreadLocal<>();
    public static final ThreadLocal<IxigoStationPage>     stationPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoTrainNamePage>   trainNamePage = new ThreadLocal<>();
    public static final ThreadLocal<IxigoVandeBharatPage> vandePage     = new ThreadLocal<>();

	public static ThreadLocal<HotelBookingPage> HBP = new ThreadLocal<>();
    public static ThreadLocal<AddHotelToWishListPage> WL = new ThreadLocal<>();
    public static ThreadLocal<ListOfHotelPage> LHP = new ThreadLocal<>();
    public static ThreadLocal<ReservePage> RP = new ThreadLocal<>();
    public static ThreadLocal<HotelSortingPage> HSP = new ThreadLocal<>();
    public static ThreadLocal<HotelWithFilters> FH = new ThreadLocal<>();
    public static ThreadLocal<AddCompositeFilters> ACF = new ThreadLocal<>();
    public static final ThreadLocal<FlightHomePage> FHP = new ThreadLocal<FlightHomePage>();
    public static final ThreadLocal<FlightResultsPage> FRP = new ThreadLocal<FlightResultsPage>();
    public static final ThreadLocal<FlightFilterPage> FFp = new ThreadLocal<FlightFilterPage>();
    public static final ThreadLocal<PassangerDetailsPage> PDP = new ThreadLocal<PassangerDetailsPage>();
    public static final ThreadLocal<BookingSummaryPage> BSP = new ThreadLocal<BookingSummaryPage>();
    public static final ThreadLocal<PaymentPage> PP = new ThreadLocal<PaymentPage>();
    public static final ThreadLocal<FlightTrackerPage> FTP = new ThreadLocal<FlightTrackerPage>();
    /**
     * Initialise all page objects for the current thread's WebDriver.
     * Called once per scenario from Hook.@Before after the browser is launched.
     * PageFactory.initElements wires up @FindBy annotations to the live driver.
     */
    public static void allPages(WebDriver driver) {
        lp.set(PageFactory.initElements(driver,HomePage.class));
        hrp.set(PageFactory.initElements(driver,HomeResultPage.class));
        bsp.set(PageFactory.initElements(driver,BusSearchPage.class));
        ssp.set(PageFactory.initElements(driver,SeatSelectionPage.class));
        pp.set(PageFactory.initElements(driver,PassengerPage.class));
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
        foodPage.set(PageFactory.initElements(driver, IxigoFoodPage.class));
        paymentPage.set(PageFactory.initElements(driver, IxigoPaymentPage.class));
        platformPage.set(PageFactory.initElements(driver, IxigoPlatformPage.class));
        stationPage.set(PageFactory.initElements(driver, IxigoStationPage.class));
        trainNamePage.set(PageFactory.initElements(driver, IxigoTrainNamePage.class));
        vandePage.set(PageFactory.initElements(driver, IxigoVandeBharatPage.class));
        HBP .set(PageFactory.initElements(driver, HotelBookingPage.class));
		WL .set(PageFactory.initElements(driver, AddHotelToWishListPage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        RP.set(PageFactory.initElements(driver, ReservePage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        LHP.set(PageFactory.initElements(driver, ListOfHotelPage.class));
        HSP.set(PageFactory.initElements(driver, HotelSortingPage.class));
        FH.set(PageFactory.initElements(driver, HotelWithFilters.class));
        ACF.set(PageFactory.initElements(driver, AddCompositeFilters.class));
        FHP.set(PageFactory.initElements(driver, FlightHomePage.class));
        FRP.set(PageFactory.initElements(driver, FlightResultsPage.class));
        FFp.set(PageFactory.initElements(driver, FlightFilterPage.class));
        PDP.set(PageFactory.initElements(driver, PassangerDetailsPage.class));
        BSP.set(PageFactory.initElements(driver, BookingSummaryPage.class));
        PP.set(PageFactory.initElements(driver, PaymentPage.class));
        FTP.set(PageFactory.initElements(driver, FlightTrackerPage.class));
    }
}
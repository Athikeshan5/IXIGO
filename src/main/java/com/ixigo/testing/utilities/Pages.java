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

    /**
     * Initialise all page objects for the current thread's WebDriver.
     * Called once per scenario from Hook.@Before after the browser is launched.
     * PageFactory.initElements wires up @FindBy annotations to the live driver.
     */
    public static void allPages(WebDriver driver) {
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
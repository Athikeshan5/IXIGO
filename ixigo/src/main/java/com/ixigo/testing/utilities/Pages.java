package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.IxigoFoodPage;
import com.ixigo.testing.pages.IxigoPaymentPage;
import com.ixigo.testing.pages.IxigoPlatformPage;
import com.ixigo.testing.pages.IxigoStationPage;
import com.ixigo.testing.pages.IxigoTrainNamePage;
import com.ixigo.testing.pages.IxigoVandeBharatPage;

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
 *
 * HOW to use in step definitions:
 *   Pages.foodPage.get().enterPNR(driver, pnr);
 *   Pages.platformPage.get().navigateToPlatformLocator(driver);
 */
public class Pages {

    // One page object per thread, stored in ThreadLocal
    public static final ThreadLocal<IxigoFoodPage>        foodPage      = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPaymentPage>     paymentPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPlatformPage>    platformPage  = new ThreadLocal<>();
    public static final ThreadLocal<IxigoStationPage>     stationPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoTrainNamePage>   trainNamePage = new ThreadLocal<>();
    public static final ThreadLocal<IxigoVandeBharatPage> vandePage     = new ThreadLocal<>();

    /**
     * Initialise all page objects for the current thread's WebDriver.
     * Called once per scenario from Hook.@Before after the browser is launched.
     * PageFactory.initElements wires up @FindBy annotations to the live driver.
     */
    public static void allPages(WebDriver driver) {
        foodPage.set(PageFactory.initElements(driver, IxigoFoodPage.class));
        paymentPage.set(PageFactory.initElements(driver, IxigoPaymentPage.class));
        platformPage.set(PageFactory.initElements(driver, IxigoPlatformPage.class));
        stationPage.set(PageFactory.initElements(driver, IxigoStationPage.class));
        trainNamePage.set(PageFactory.initElements(driver, IxigoTrainNamePage.class));
        vandePage.set(PageFactory.initElements(driver, IxigoVandeBharatPage.class));
    }
}

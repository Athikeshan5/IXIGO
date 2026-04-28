package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.IxigoFoodPage;
import com.ixigo.testing.pages.IxigoPaymentPage;
import com.ixigo.testing.pages.IxigoPlatformPage;
import com.ixigo.testing.pages.IxigoStationPage;
import com.ixigo.testing.pages.IxigoTrainNamePage;
import com.ixigo.testing.pages.IxigoVandeBharatPage;


public class Pages {

    // One page object per thread, stored in ThreadLocal
    public static final ThreadLocal<IxigoFoodPage>        foodPage      = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPaymentPage>     paymentPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoPlatformPage>    platformPage  = new ThreadLocal<>();
    public static final ThreadLocal<IxigoStationPage>     stationPage   = new ThreadLocal<>();
    public static final ThreadLocal<IxigoTrainNamePage>   trainNamePage = new ThreadLocal<>();
    public static final ThreadLocal<IxigoVandeBharatPage> vandePage     = new ThreadLocal<>();

    public static void allPages(WebDriver driver) {
        foodPage.set(PageFactory.initElements(driver, IxigoFoodPage.class));
        paymentPage.set(PageFactory.initElements(driver, IxigoPaymentPage.class));
        platformPage.set(PageFactory.initElements(driver, IxigoPlatformPage.class));
        stationPage.set(PageFactory.initElements(driver, IxigoStationPage.class));
        trainNamePage.set(PageFactory.initElements(driver, IxigoTrainNamePage.class));
        vandePage.set(PageFactory.initElements(driver, IxigoVandeBharatPage.class));
    }
}

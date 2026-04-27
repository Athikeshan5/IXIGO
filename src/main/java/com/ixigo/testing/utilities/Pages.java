
	package com.ixigo.testing.utilities;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.support.PageFactory;

	import com.ixigo.testing.pages.*;

	public class Pages {

	    public static ThreadLocal<HomeResultPage> hrp=new ThreadLocal<>();
	    public static ThreadLocal<BusSearchPage> bsp=new ThreadLocal<>();
	    public static ThreadLocal<SeatSelectionPage> ssp=new ThreadLocal<>();
	    public static ThreadLocal<PassengerPage> pp=new ThreadLocal<>();
	    public static ThreadLocal<HomeLoginPage> lp=new ThreadLocal<>();
	    public static ThreadLocal<TrackTicketPage> tk=new ThreadLocal<>();

        

	    public static void allPages(WebDriver driver) {
	        lp.set(PageFactory.initElements(driver,HomeLoginPage.class));
	        hrp.set(PageFactory.initElements(driver,HomeResultPage.class));
	        bsp.set(PageFactory.initElements(driver,BusSearchPage.class));
	        ssp.set(PageFactory.initElements(driver,SeatSelectionPage.class));
	        pp.set(PageFactory.initElements(driver,PassengerPage.class));
	        tk.set(PageFactory.initElements(driver,TrackTicketPage.class));

	    
	}
	}

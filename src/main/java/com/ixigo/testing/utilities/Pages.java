package com.ixigo.testing.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ixigo.testing.pages.HomePage;
import com.ixigo.testing.pages.PNRStatusPage;
import com.ixigo.testing.pages.PNRValidationPage;
import com.ixigo.testing.pages.RunningStatusPage;
import com.ixigo.testing.pages.TrainRuningStatusPage;
import com.ixigo.testing.pages.TrainsPage;

public class Pages {

	public static HomePage hp;
	public static TrainsPage tp;
	public static PNRStatusPage pnrs;
	public static PNRValidationPage pnrv;
	public static TrainRuningStatusPage trs;
	public static RunningStatusPage rs;
	
	public static void allPages(WebDriver driver) {
		hp=PageFactory.initElements(driver, HomePage.class);
		tp=PageFactory.initElements(driver, TrainsPage.class);
		pnrs=PageFactory.initElements(driver, PNRStatusPage.class);
		pnrv=PageFactory.initElements(driver, PNRValidationPage.class);
		trs=PageFactory.initElements(driver, TrainRuningStatusPage.class);
		rs=PageFactory.initElements(driver, RunningStatusPage.class);
	}
}

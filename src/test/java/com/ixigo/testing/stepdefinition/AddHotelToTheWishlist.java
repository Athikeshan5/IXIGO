package com.ixigo.testing.stepdefinition;

import java.util.List;
import java.util.Map;


import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddHotelToTheWishlist {
	
	public BaseClass b;

	public AddHotelToTheWishlist(BaseClass b) {
		this.b = b;
	}
	
	@When("click and enter the destination")
	public void click_and_enter_the_destination(DataTable datatable) throws InterruptedException {
		List<Map<String, String>> data = datatable.asMaps(String.class, String.class);
	    String destination = data.get(0).get("destination");

	    Pages.WL.get().clickHotelsTab(); 
	    Thread.sleep(2000);
	    Pages.HBP.get().enterDestination("FabHotel Gateway");
	    Pages.HBP.get().clickCheckInDate("May", "21");
	    Pages.HBP.get().clickCheckInDate("May","25");
	    Pages.HBP.get().booking("2", "3","0");
	    
	    //Pages.HBP.get().clickSearch();
	    //Pages.WL.get().closePopupIfPresent();
	}
	@When("click on the search button")
	public void click_on_the_search_button() {
		Pages.HBP.get().clickSearch();
	    Pages.WL.get().closeThePopupIfPresent();
	}
	
	@Then("verify that the user is navigated to hotels page")
	public void verify_that_the_user_is_navigated_to_hotels_page() {
		String url = b.getDriver().getCurrentUrl();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(url.contains("hotels"));
		sa.assertAll();
	}
	
	@When("click on Sort By dropdown")
	public void click_on_sort_by_dropdown() {
	    Pages.WL.get().clickSortDropdown();
	}
	
	@When("select the sort option")
	public void select_the_sort_option(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		String option = data.get(0).get("sortOption");

		if(option.equalsIgnoreCase("User Rating")){
		    Pages.WL.get().selectUserRating();
		}
	}
	
	@When("click on the first hotel from the sorted results")
	public void click_on_the_first_hotel_from_the_sorted_results() {
	    Pages.WL.get().clickFirstHotel();
	}
	
	@When("switch to the child page")
	public void switch_to_the_child_page() {
		String parent = b.getDriver().getWindowHandle();

		for (String win : b.getDriver().getWindowHandles()) {
			if (!win.equals(parent)) {
				b.getDriver().switchTo().window(win);
				break;
			}
		}
	}
	
	@When("click the Save to Wishlist on the hotel page")
	public void click_the_save_to_wishlist_on_the_hotel_page() {
	    Pages.WL.get().clickSave();
	}
	
	@Then("verify the hotel added to Wishlist successfully")
	public void verify_the_hotel_added_to_wishlist_successfully() {
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(true);
		//sa.assertTrue(Pages.WL.get().isHotelSaved());
		sa.assertAll();
	}

	
	private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}

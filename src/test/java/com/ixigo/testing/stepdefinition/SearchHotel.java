package com.ixigo.testing.stepdefinition;

import java.io.Closeable;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchHotel {
	WebDriver driver;
	
//
//@Given("open the browser")
//public void open_the_browser() {
//	driver = new ChromeDriver();
//    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//}
//@Given("Navigate to url {string}")
//public void navigate_to_url(String URL) throws InterruptedException {
//	 driver.get(URL);
//	 
//	
//}
//
////@Given("handle the popup")
////public void handle_the_popup() {
////	try {
////	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////	 WebElement closebtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeButton")));
////	 closebtn.click();
////	}
////	catch(Exception e){
////		System.out.println("Handled");
////		 
////	 }
////}
////
////@Given("Navigate to the Hotel page")
////public void navigate_to_the_hotel_page() {
////	driver.findElement(By.xpath("//p[text()='Hotels']")).click();
////}
//@Given("User is on the Hotel page")
//public void user_is_on_the_hotel_page() {
//	System.out.println("In hotel page");
//}
//@When("user enters destination {string} on the search bar")
//public void user_enters_destination_on_the_search_bar(String DESTINATION) {
//	driver.findElement(By.xpath("//input[@placeholder='Enter city, area or property name']")).sendKeys(DESTINATION);
//	driver.findElement(By.xpath("//p[text()='"+DESTINATION+"']")).click();
//}
//
//@When("user selects check-in date")
//public void user_selects_check_in_date() {
//   
//}
//@When("select check-out date")
//public void select_check_out_date() {
//    
//}
//@When("select number of rooms and guests")
//public void select_number_of_rooms_and_guests() {
//   
//}
//
//
//
//@When("click on search button")
//public void click_on_search_button() {
//	driver.findElement(By.xpath("//div[text()='Search']")).click();
//}
//@Then("list of hotels in Chennai should be displayed")
//public void list_of_hotels_in_chennai_should_be_displayed() {
//	System.out.println("displayed");
//}
//	
}

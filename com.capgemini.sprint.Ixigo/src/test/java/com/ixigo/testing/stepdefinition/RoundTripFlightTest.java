package com.ixigo.testing.stepdefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RoundTripFlightTest {
	WebDriver driver;
	@Given("the user selects {string} trip type")
	public void the_user_selects_trip_type() {
	   driver.findElement(By.xpath("//button[.='Round Trip']")).click();
	}
	@When("the user enters source city as {string}")
	public void the_user_enters_source_city_as(String Source) throws InterruptedException {
		 // Step 1: Click the 'From' span to open the input
	    WebElement from_field = driver.findElement(By.xpath("//span[text()='From']"));
	    from_field.click();
	    Thread.sleep(1000);

	    // Step 2: Find the actual input field and type
	    WebElement from_input = driver.findElement(
	        By.xpath("//label[text()='From']/..//input")
	    );
	    from_input.clear();
	    from_input.sendKeys(Source);
	    Thread.sleep(3000); // Wait for dropdown suggestions to fully load
	    WebElement dropdown = driver.findElement(By.xpath("//span[contains(.,'" + Source + "')]"));

	 // Force click using JavaScript
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript("arguments[0].click();", dropdown);
	}
	@When("the user enters destination city as {string}")
	public void the_user_enters_destination_city_as(String Destination) {
		
	    
	}
	@When("the user selects a valid departure date")
	public void the_user_selects_a_valid_departure_date() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("the user selects a valid return date")
	public void the_user_selects_a_valid_return_date() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("the user clicks the {string} button")
	public void the_user_clicks_the_button(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@Then("both onward and return flights from {string} to {string} should be displayed")
	public void both_onward_and_return_flights_from_to_should_be_displayed(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


}

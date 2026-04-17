//package com.ixigo.testing.stepdefinitions;
//
//import com.ixigo.testing.pages.IxigoLoginPage;
//import com.ixigo.testing.utilities.BaseClass;
//
//import io.cucumber.java.en.*;
//
//public class LoginSteps extends BaseClass {
//
//    IxigoLoginPage loginPage;
//
//    @Given("User is on ixigo homepage")
//    public void open_homepage() {
//        initializeDriver();
//        openUrl("https://www.ixigo.com/trains");
//        loginPage = new IxigoLoginPage(driver);
//    }
//
//    @When("User clicks login button")
//    public void click_login() {
//        loginPage.clickLogin();
//    }
//
//    @When("User enters mobile number {string}")
//    public void enter_mobile(String number) {
//        loginPage.enterMobile(number);
//    }
//
//    @When("User clicks continue")
//    public void click_continue() {
//        loginPage.clickContinue();
//    }
//
//    @Then("User enters OTP manually")
//    public void handle_otp() {
//        loginPage.waitForOTP();
//
//        if (loginPage.isLoginSuccessful()) {
//            System.out.println("Login Successful");
//        } else {
//            System.out.println("Login Failed");
//            throw new RuntimeException("Login not successful");
//        }
//    }
//}
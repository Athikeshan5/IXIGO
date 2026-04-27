package com.ixigo.testing.stepdefinition;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ixigo.testing.utilities.BaseClass;
import com.ixigo.testing.utilities.ExcelDataProvider;
import com.ixigo.testing.utilities.Pages;

import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;


public class TrainNameSteps {

    public BaseClass b;

    // Excel data loaded once per test run
    private final List<Map<String, String>> excelData;
    private static int excelRowIndex = 0;

    public TrainNameSteps(BaseClass b) {
        this.b = b;
        this.excelData = ExcelDataProvider.getData("TrainData");
        System.out.println("Excel data loaded: " + excelData.size() + " rows");
    }

    @Given("User is on train name search page")
    public void user_on_train_name_page() {
        log(Status.INFO, "Given: Navigating to Train Name Search page");
        Pages.trainNamePage.get().goToTrainNamePage(b.getDriver());
        log(Status.PASS, "Given PASSED: Train Name Search page loaded");
    }

    @When("User enters train name {string}")
    public void user_enters_train_name(String trainFromFeature) {
        String trainToUse = trainFromFeature;

        if (!excelData.isEmpty()) {
            int row = excelRowIndex % excelData.size();
            String excelTrain = excelData.get(row).get("TrainName");
            if (excelTrain != null && !excelTrain.isEmpty()) {
                trainToUse = excelTrain;
                log(Status.INFO, "Excel parametrization [Row " + (row + 1) + "]: TrainName = " + trainToUse);
            }
            excelRowIndex++;
        }

        log(Status.INFO, "When: Entering train name: " + trainToUse);
        Pages.trainNamePage.get().enterTrainName(b.getDriver(), trainToUse);
        log(Status.PASS, "When PASSED: Train name entered — " + trainToUse);
    }

    @When("User selects train from list")
    public void select_train() {
        log(Status.INFO, "When: Selecting first train from search results");
        Pages.trainNamePage.get().selectTrain(b.getDriver());
        log(Status.PASS, "When PASSED: Train selected from list");
    }

    @When("User selects available date")
    public void select_date() {
        log(Status.INFO, "When: Selecting first available (AVL) date");
        Pages.trainNamePage.get().selectAvailableDate(b.getDriver());
        log(Status.PASS, "When PASSED: AVL date selected");
    }

    @When("User clicks on book")
    public void user_clicks_book() {
        log(Status.INFO, "When: Clicking BOOK button");
        Pages.trainNamePage.get().clickBook(b.getDriver());
        log(Status.PASS, "When PASSED: BOOK button clicked");
    }

    // @Then "User should see login popup" is in CommonSteps to avoid duplicate

    private void log(Status status, String msg) {
        System.out.println("  " + (status == Status.PASS ? "✅" : status == Status.FAIL ? "❌" : "ℹ️") + " " + msg);
        ExtentTest test = BaseClass.test.get();
        if (test != null) {
            try { test.log(status, msg); } catch (Exception ignored) {}
        }
    }
}

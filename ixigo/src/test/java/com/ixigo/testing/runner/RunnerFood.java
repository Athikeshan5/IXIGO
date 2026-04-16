package com.ixigo.testing.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/com/ixigo/testing/features/foodDelivery.feature",
    glue = "com.ixigo.testing.stepdefinitions",
    plugin = {"pretty", "html:Reports/food-report.html"},
    monochrome = true
)

public class RunnerFood extends AbstractTestNGCucumberTests {
}
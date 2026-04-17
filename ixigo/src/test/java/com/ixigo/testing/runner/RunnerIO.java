package com.ixigo.testing.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/com/ixigo/testing/features", // 🔥 all feature files
    glue = "com.ixigo.testing.stepdefinitions",
    plugin = {
        "pretty",
        "html:Reports/cucumber-report.html",
        "json:Reports/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)

public class RunnerIO extends AbstractTestNGCucumberTests {
}
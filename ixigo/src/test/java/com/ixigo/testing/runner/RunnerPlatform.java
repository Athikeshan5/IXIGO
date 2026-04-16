package com.ixigo.testing.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/com/ixigo/testing/features/platform.feature",
    glue = "com.ixigo.testing.stepdefinitions",
    plugin = {
        "pretty",
        "html:Reports/platform-report.html"
    },
    monochrome = true
)

public class RunnerPlatform extends AbstractTestNGCucumberTests {

}
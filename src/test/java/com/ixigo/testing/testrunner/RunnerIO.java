package com.ixigo.testing.testrunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"./src/test/java/com/ixigo/featurefile"},glue = "com.ixigo.testing.stepdefinition" ,dryRun = false)
public class RunnerIO extends AbstractTestNGCucumberTests{




//
//@CucumberOptions(features = {"src\\test\\java\\com\\ixigo\\testing\\featurefile\\.feature"},glue="com.ixigo.testing.stepdefinition",plugin = {"pretty", "html:target/cucumber-report.html"},
//monochrome = true)
//public class RunnerIO extends AbstractTestNGCucumberTests{

	@Override
    @DataProvider(parallel = true) 
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
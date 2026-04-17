package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/java/com/ixigo/testing/featurefile"},glue="com.ixigo.testing.stepdefinition",dryRun =false)
public class RunnerIO extends AbstractTestNGCucumberTests{
     
}

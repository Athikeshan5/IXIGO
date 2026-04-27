package com.ixigo.testing.testrunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * RunnerIO — the TestNG entry point for all Cucumber scenarios.
 *
 * Parallelism:
 *   @DataProvider(parallel = true) tells TestNG to run each scenario
 *   on a separate thread. The thread count is controlled by testng.xml
 *   (thread-count="4" means at most 4 browsers open simultaneously).
 *
 * Plugins:
 *   "pretty"                       — console output with colours
 *   "html:target/cucumber-report.html" — built-in Cucumber HTML report
 *   CucumberExtentListener         — wires each step result to ExtentReports
 *
 * glue:
 *   Points to the package containing Hook + all step definition classes.
 *   PicoContainer is on the classpath (cucumber-picocontainer dependency)
 *   so BaseClass is automatically injected into Hook and every step class
 *   that declares a matching constructor parameter.
 */

		@CucumberOptions(
			    features = "src/test/java/com/ixigo/testing/featurefile",
			    glue     = "com.ixigo.testing.stepdefinition",
			    plugin   = {
			        "com.ixigo.testing.utilities.CucumberExtentListener",  // ✅ required
			        "pretty",
			        "html:target/cucumber-reports.html"
			    }
			,
    monochrome = true
)
public class RunnerIO extends AbstractTestNGCucumberTests {

    /**
     * parallel = true — each Cucumber scenario runs on its own thread.
     * Thread count is set in testng.xml (thread-count="4").
     * Reduce thread-count if your machine runs out of memory or Chrome instances crash.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
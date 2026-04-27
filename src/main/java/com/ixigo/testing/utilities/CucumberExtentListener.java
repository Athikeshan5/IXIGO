package com.ixigo.testing.utilities;

import com.aventstack.extentreports.*;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import io.cucumber.plugin.event.Status;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

public class CucumberExtentListener implements EventListener {

    
    private final ThreadLocal<String> scenarioId = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class,  this::onScenarioStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onScenarioEnd);
    }

    private void onScenarioStart(TestCaseStarted event) {
        String id   = event.getTestCase().getId().toString();
        String name = event.getTestCase().getName();

        scenarioId.set(id);

        ExtentTest node = ReportManager.getReport()
                .createTest("Scenario: " + name);
        BaseClass.test.set(node);

        System.out.println("[Listener] Started: " + name + " | id=" + id);
    }

   
    private void onStepFinished(TestStepFinished event) {

        if (!(event.getTestStep() instanceof PickleStepTestStep step)) return;

        ExtentTest node = BaseClass.test.get();
        if (node == null) return;

        String keyword = step.getStep().getKeyword().trim();
        String text    = step.getStep().getText();
        Status status  = event.getResult().getStatus();
        String label   = keyword + " " + text;

        switch (status) {
            case PASSED  -> node.pass("✅ " + label);

            case FAILED  -> {
                Throwable err = event.getResult().getError();
                String cause  = (err != null) ? err.getMessage() : "unknown";
                node.fail("❌ " + label + " | Cause: " + cause);
                takeAndAttach(node, text);   
            }

            case SKIPPED -> node.skip("⏭ " + label);
            default      -> node.info(label);
        }
    }

    
    private void onScenarioEnd(TestCaseFinished event) {
        String id = event.getTestCase().getId().toString();

        WebDriver driver = BaseClass.driverStore.remove(id); 
        if (driver != null) {
            try {
              driver.quit();   
                System.out.println("[Listener] Driver quit: " + id);
            } catch (Exception e) {
                System.err.println("[Listener] Quit error: " + e.getMessage());
            }
        }

        scenarioId.remove();
    }

   
    private void takeAndAttach(ExtentTest node, String stepText) {
        try {
            
            String    id     = scenarioId.get();
            WebDriver driver = (id != null) ? BaseClass.driverStore.get(id) : null;

           
            if (driver == null && !BaseClass.driverStore.isEmpty()) {
                driver = BaseClass.driverStore.values().iterator().next();
                System.out.println("[Screenshot] Using fallback driver");
            }

            if (driver == null) {
                node.warning("⚠️ No driver — screenshot skipped");
                System.out.println("[Screenshot] ABORT — driver is null");
                return;
            }

        
            String safe   = stepText.replaceAll("[^a-zA-Z0-9_\\-]", "_")
                          + "_" + System.currentTimeMillis();
            String dir    = System.getProperty("user.dir") + "/ScreenShot/";
            String file   = dir + safe + ".png";

            new File(dir).mkdirs();

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(file);
            FileHandler.copy(src, dest);

            System.out.println("[Screenshot] Saved: " + dest.getAbsolutePath()
                    + " (" + dest.length() + " bytes)");

        
            node.fail(MediaEntityBuilder
                    .createScreenCaptureFromPath(dest.getAbsolutePath())
                    .build());

            System.out.println("[Screenshot] Attached to report ✅");

        } catch (Exception e) {
            System.err.println("[Screenshot] Error: " + e.getMessage());
            node.warning("⚠️ Screenshot error: " + e.getMessage());
        }
    }
}
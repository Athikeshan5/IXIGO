package com.ixigo.testing.utilities;

import java.io.File;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager implements ISuiteListener {

    private static final ExtentReports extent;

    static {
        new File("Reports").mkdirs();

        ExtentSparkReporter reporter =
                new ExtentSparkReporter("Reports/Ixigo_extent.html");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            extent.flush();
        }));
    }

    public static ExtentReports getReport() {
        return extent;
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
    }
}
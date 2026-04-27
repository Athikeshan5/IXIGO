package com.ixigo.testing.utilities;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ReportManager implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {}

    @Override
    public void onFinish(ISuite suite) {
        AllUtilityFunctions util = new AllUtilityFunctions();
        util.getReport().flush();
        System.out.println("Extent Report flushed after all tests");
    }
}
package com.ixigo.testing.stepdefinitions;

import com.ixigo.testing.utilities.BaseClass;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook extends BaseClass {

    @Before
    public void setUp() {
        initializeDriver();   // Launch browser + open ixigo
    }

    @After
    public void tearDownScenario() {
        tearDown();   // Close browser
    }
}
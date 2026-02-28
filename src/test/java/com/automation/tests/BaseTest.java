package com.automation.tests;

import com.automation.base.AppDriver;
import com.automation.utils.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for every test.
 * Handles driver setup / teardown and automatic screenshots on failure.
 */
public class BaseTest {

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = AppDriver.createDriver();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            // Capture screenshot on failure for debugging
            if (result.getStatus() == ITestResult.FAILURE) {
                TestUtils.takeScreenshot(driver, result.getName());
            }
            driver.quit();
        }
    }
}
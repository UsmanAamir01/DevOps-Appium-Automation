package com.automation.tests;

import com.automation.utils.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * BaseTest — driver lifecycle shared by every test class.
 *
 * Design decisions:
 *  • UiAutomator2Options configured inline — no hidden factory class.
 *  • No implicitlyWait — explicit waits in BasePage make implicit waits harmful
 *    (they mask TimeoutExceptions and slow negative-path tests).
 *  • @AfterMethod receives ITestResult so screenshots are saved only on failure.
 */
public class BaseTest {

    private static final String APPIUM_URL  = "http://127.0.0.1:4723";
    private static final String DEVICE_NAME = "emulator-5554";
    private static final String APK_PATH    =
            new File("src/test/resources/apps/MyDemoApp.apk").getAbsolutePath();

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(DEVICE_NAME);
        options.setApp(APK_PATH);
        options.setAutoGrantPermissions(true);
        options.setFullReset(false);
        options.setNoReset(false);

        try {
            driver = new AndroidDriver(new URL(APPIUM_URL), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + APPIUM_URL, e);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                TestUtils.takeScreenshot(driver, result.getName());
            }
            driver.quit();
        }
    }
}
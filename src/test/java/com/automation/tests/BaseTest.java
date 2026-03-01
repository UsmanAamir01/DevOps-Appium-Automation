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
import java.time.Duration;

public class BaseTest {

    private static final String APPIUM_URL  = "http://127.0.0.1:4723";
    private static final String DEVICE_NAME = "emulator-5554";
    private static final String APK_PATH    = resolveApkPath();

    /** True when running inside a CI environment (GitHub Actions sets CI=true). */
    private static final boolean IS_CI = "true".equalsIgnoreCase(System.getenv("CI"));

    /** True when the APK has been pre-installed on the emulator via adb install. */
    private static final boolean APP_PRE_INSTALLED =
            "true".equalsIgnoreCase(System.getenv("APP_PRE_INSTALLED"));

    private static String resolveApkPath() {
        String envPath = System.getenv("APP_PATH");
        if (envPath != null && !envPath.isEmpty()) {
            return envPath;
        }
        return new File("src/test/resources/apps/MyDemoApp.apk").getAbsolutePath();
    }

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(DEVICE_NAME);
        options.setAutoGrantPermissions(true);
        options.setCapability("appium:newCommandTimeout", IS_CI ? 180 : 120);

        // App activity wait configuration
        options.setCapability("appium:appWaitActivity",
                "com.saucelabs.mydemoapp.android.view.activities.SplashActivity," +
                "com.saucelabs.mydemoapp.android.view.activities.MainActivity");
        options.setCapability("appium:appWaitDuration", IS_CI ? 60000 : 30000);

        if (APP_PRE_INSTALLED) {
            // CI pre-installed mode: launch by package/activity instead of re-installing the APK
            options.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.android");
            options.setCapability("appium:appActivity",
                    "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
            options.setFullReset(false);
            options.setNoReset(false);
            options.setCapability("appium:appWaitForLaunch", true);
            options.setCapability("appium:adbExecTimeout", 60000);
        } else {
            // Normal mode: install APK via Appium
            options.setApp(APK_PATH);
            options.setFullReset(false);
            options.setNoReset(false);
        }

        try {
            driver = new AndroidDriver(new URL(APPIUM_URL), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IS_CI ? 15 : 10));
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
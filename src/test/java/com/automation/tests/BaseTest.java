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
        options.setApp(APK_PATH);
        options.setAutoGrantPermissions(true);
        options.setCapability("appium:newCommandTimeout", 180);

        // App activity wait configuration
        options.setCapability("appium:appWaitActivity",
                "com.saucelabs.mydemoapp.android.view.activities.SplashActivity," +
                "com.saucelabs.mydemoapp.android.view.activities.MainActivity");
        options.setCapability("appium:appWaitDuration", 60000);

        if (IS_CI) {
            // CI mode: don't reinstall the pre-installed APK every test, just clear app data
            options.setFullReset(false);
            options.setNoReset(false);
            options.setCapability("appium:appWaitForLaunch", true);
            // Longer ADB command timeout for slow CI emulators
            options.setCapability("appium:adbExecTimeout", 60000);
            // Don't use app path if the APK is already installed via adb install
            String installed = System.getenv("APP_PRE_INSTALLED");
            if ("true".equalsIgnoreCase(installed)) {
                options.removeCapability("appium:app");
                options.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.android");
                options.setCapability("appium:appActivity",
                        "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
            }
        } else {
            // Local mode: clean app state per test
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
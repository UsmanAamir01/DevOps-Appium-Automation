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

/**
 * Base class for all test classes.
 * Uses ThreadLocal for thread-safe parallel test execution.
 * Supports 3 modes: local, CI (emulator-runner), and Docker.
 */
public class BaseTest {

    private static final boolean IS_CI = "true".equalsIgnoreCase(System.getenv("CI"));
    private static final boolean IS_DOCKER = "true".equalsIgnoreCase(System.getenv("DOCKER_MODE"));
    private static final boolean APP_PRE_INSTALLED =
            "true".equalsIgnoreCase(System.getenv("APP_PRE_INSTALLED"));

    private static final String APPIUM_URL = resolveAppiumUrl();
    private static final String DEVICE_NAME = resolveDeviceName();
    private static final String APK_PATH = resolveApkPath();

    /** Thread-local driver for parallel test execution. */
    private static final ThreadLocal<AndroidDriver> driverThread = new ThreadLocal<>();

    protected AndroidDriver driver;

    private static String resolveAppiumUrl() {
        String envUrl = System.getenv("APPIUM_URL");
        return (envUrl != null && !envUrl.isEmpty()) ? envUrl : "http://127.0.0.1:4723";
    }

    private static String resolveDeviceName() {
        String envDevice = System.getenv("DEVICE_NAME");
        return (envDevice != null && !envDevice.isEmpty()) ? envDevice : "emulator-5554";
    }

    private static String resolveApkPath() {
        String envPath = System.getenv("APP_PATH");
        if (envPath != null && !envPath.isEmpty()) {
            return envPath;
        }
        return new File("src/test/resources/apps/MyDemoApp.apk").getAbsolutePath();
    }

    @BeforeMethod
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(DEVICE_NAME);
        options.setAutoGrantPermissions(true);
        options.setCapability("appium:newCommandTimeout", IS_CI || IS_DOCKER ? 300 : 120);

        // UiAutomator2 server install & launch timeouts
        options.setCapability("appium:uiautomator2ServerInstallTimeout", IS_CI || IS_DOCKER ? 120000 : 30000);
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", IS_CI || IS_DOCKER ? 120000 : 30000);

        // App activity wait
        options.setCapability("appium:appWaitActivity",
                "com.saucelabs.mydemoapp.android.view.activities.SplashActivity," +
                "com.saucelabs.mydemoapp.android.view.activities.MainActivity");
        options.setCapability("appium:appWaitDuration", IS_CI || IS_DOCKER ? 90000 : 30000);

        if (IS_DOCKER) {
            // Docker mode: APK is mounted at /root/tmp, install via Appium
            options.setApp(APK_PATH);
            options.setFullReset(false);
            options.setNoReset(true);
            options.setCapability("appium:adbExecTimeout", 120000);
        } else if (APP_PRE_INSTALLED) {
            // CI emulator-runner mode: app pre-installed via adb
            options.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.android");
            options.setCapability("appium:appActivity",
                    "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
            options.setFullReset(false);
            options.setNoReset(true);
            options.setCapability("appium:appWaitForLaunch", true);
            options.setCapability("appium:adbExecTimeout", 120000);
            options.setCapability("appium:skipDeviceInitialization", true);
        } else {
            // Local mode
            options.setApp(APK_PATH);
            options.setFullReset(false);
            options.setNoReset(false);
        }

        try {
            AndroidDriver d = new AndroidDriver(new URL(APPIUM_URL), options);
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(IS_CI || IS_DOCKER ? 15 : 10));
            driverThread.set(d);
            this.driver = d;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + APPIUM_URL, e);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        AndroidDriver d = driverThread.get();
        if (d != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                TestUtils.takeScreenshot(d, result.getName());
            }
            d.quit();
            driverThread.remove();
        }
        this.driver = null;
    }
}
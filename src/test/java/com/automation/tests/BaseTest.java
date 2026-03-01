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
 * Base test class for all Appium tests.
 * <p>
 * Supports two modes:
 * <ul>
 *   <li><b>Local</b> — default (Appium at localhost:4723, emulator-5554)</li>
 *   <li><b>CI</b>    — set env var CI=true (longer timeouts, same Appium URL)</li>
 * </ul>
 * Uses ThreadLocal for thread-safe parallel execution.
 */
public class BaseTest {

    private static final boolean IS_CI = "true".equalsIgnoreCase(System.getenv("CI"));

    private static final String APPIUM_URL = envOrDefault("APPIUM_URL", "http://127.0.0.1:4723");
    private static final String DEVICE_NAME = envOrDefault("DEVICE_NAME", "emulator-5554");
    private static final String APK_PATH = resolveApkPath();

    private static final ThreadLocal<AndroidDriver> driverThread = new ThreadLocal<>();
    protected AndroidDriver driver;

    private static String envOrDefault(String key, String defaultValue) {
        String val = System.getenv(key);
        return (val != null && !val.isEmpty()) ? val : defaultValue;
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
        options.setApp(APK_PATH);
        options.setAutoGrantPermissions(true);

        // Reset behaviour: clear app data but don't uninstall/reinstall
        options.setFullReset(false);
        options.setNoReset(IS_CI);

        // Timeouts — generous for CI, snappy for local
        options.setCapability("appium:newCommandTimeout", IS_CI ? 300 : 120);
        options.setCapability("appium:uiautomator2ServerInstallTimeout", IS_CI ? 120000 : 30000);
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", IS_CI ? 120000 : 30000);
        options.setCapability("appium:adbExecTimeout", IS_CI ? 120000 : 30000);

        // Wait for app to launch
        options.setCapability("appium:appWaitActivity",
                "com.saucelabs.mydemoapp.android.view.activities.SplashActivity," +
                "com.saucelabs.mydemoapp.android.view.activities.MainActivity");
        options.setCapability("appium:appWaitDuration", IS_CI ? 90000 : 30000);

        try {
            AndroidDriver d = new AndroidDriver(new URL(APPIUM_URL), options);
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(IS_CI ? 15 : 10));
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
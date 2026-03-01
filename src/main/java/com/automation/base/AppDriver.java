package com.automation.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Manages the AndroidDriver lifecycle.
 * Centralizes driver creation so every test reuses the same configuration.
 */
public class AppDriver {

    private static final String APPIUM_URL = "http://127.0.0.1:4723";
    private static final String DEVICE_NAME = "emulator-5554";
    private static final String APK_PATH = resolveApkPath();

    private static String resolveApkPath() {
        String envPath = System.getenv("APP_PATH");
        if (envPath != null && !envPath.isEmpty()) {
            return envPath;
        }
        return new File("src/test/resources/apps/MyDemoApp.apk").getAbsolutePath();
    }

    private AppDriver() {
        // utility class
    }

    /**
     * Creates and returns a configured AndroidDriver instance.
     */
    public static AndroidDriver createDriver() {
        try {
            File apk = new File(APK_PATH);

            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName(DEVICE_NAME);
            options.setApp(apk.getAbsolutePath());
            options.setAutoGrantPermissions(true);

            AndroidDriver driver = new AndroidDriver(new URL(APPIUM_URL), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            return driver;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + APPIUM_URL, e);
        }
    }
}


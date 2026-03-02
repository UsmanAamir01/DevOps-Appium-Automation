package com.automation.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    private TestUtils() {
    }

    public static File takeScreenshot(AndroidDriver driver, String testName) {
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path destDir = Paths.get("target", "screenshots");
            Files.createDirectories(destDir);
            Path dest = destDir.resolve(testName + "_" + timestamp + ".png");
            Files.copy(source.toPath(), dest);
            System.out.println("Screenshot saved: " + dest.toAbsolutePath());
            return dest.toFile();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}


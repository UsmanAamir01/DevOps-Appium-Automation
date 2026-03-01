package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage — single source of truth for all driver interactions.
 *
 * Architecture rules:
 *  • No PageFactory / AppiumFieldDecorator (defeats explicit waits).
 *  • No Thread.sleep() anywhere in the hierarchy.
 *  • Every child page declares locators as private static final By constants.
 *  • WebDriverWait is protected so subclasses can compose custom conditions.
 *  • Fluent Navigation: action methods that cause page transitions return the next Page Object.
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    // ── Canonical interaction helpers ────────────────────────────────────

    /**
     * Waits until the element is visible and returns it.
     * Use this for assertions and reading text.
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element is clickable, then taps it.
     * Covers the "element present but not yet interactable" race condition.
     */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Waits for the field to be visible, clears it, then types {@code text}.
     */
    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    /**
     * Safe boolean visibility check — returns false instead of throwing
     * when the element is absent or the wait times out.
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForElementVisible(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns the trimmed visible text of the first element matching {@code locator}.
     */
    protected String getText(By locator) {
        return waitForElementVisible(locator).getText().trim();
    }
}

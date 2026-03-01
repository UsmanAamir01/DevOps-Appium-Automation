package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Page Object — Login screen.
 *
 * Encapsulation:
 *  • All locators are private static final — hidden from tests.
 *  • Public methods represent user actions, returning the next Page Object (Fluent Navigation).
 *
 * Two login flows exist in MyDemoApp:
 *  1. Direct login via menu  → lands on CatalogPage   → use login()
 *  2. Cart checkout flow     → lands on CheckoutPage  → use loginToCheckout()
 */
public class LoginPage extends BasePage {

    // ── Private locators ─────────────────────────────────────────────────
    private static final By USERNAME_FIELD =
            By.id("com.saucelabs.mydemoapp.android:id/nameET");
    private static final By PASSWORD_FIELD =
            By.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private static final By LOGIN_BUTTON   =
            By.id("com.saucelabs.mydemoapp.android:id/loginBtn");
    private static final By LOGIN_HEADER   =
            By.xpath("//android.widget.TextView[@text='Login']");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Page state ───────────────────────────────────────────────────────

    /** Returns true when the Login screen header is visible. */
    public boolean isPageLoaded() {
        return isDisplayed(LOGIN_HEADER);
    }

    /** Alias for isPageLoaded(). */
    public boolean isLoginDisplayed() {
        return isPageLoaded();
    }

    // ── User actions ─────────────────────────────────────────────────────

    /** Enters the username into the username field. Returns this for fluent chaining. */
    public LoginPage enterUsername(String username) {
        type(USERNAME_FIELD, username);
        return this;
    }

    /** Enters the password into the password field. Returns this for fluent chaining. */
    public LoginPage enterPassword(String password) {
        type(PASSWORD_FIELD, password);
        return this;
    }

    /**
     * Performs a full login via the direct menu flow.
     * Taps the login button and returns the CatalogPage (Fluent Navigation).
     */
    public CatalogPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return new CatalogPage(driver);
    }

    /**
     * Performs login during the Cart → Checkout flow.
     * Taps the login button and returns the CheckoutPage (Fluent Navigation).
     */
    public CheckoutPage loginToCheckout(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return new CheckoutPage(driver);
    }
}

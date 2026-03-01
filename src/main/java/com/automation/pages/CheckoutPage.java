package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Page Object — Checkout / Shipping Address screen.
 *
 * Encapsulation:
 *  • All locators are private static final — hidden from tests.
 *  • Public methods expose user-facing form actions only.
 *  • Fluent Navigation: proceedToPayment() returns this (form still same screen type).
 */
public class CheckoutPage extends BasePage {

    // ── Private locators ─────────────────────────────────────────────────
    private static final By CHECKOUT_TITLE  =
            By.id("com.saucelabs.mydemoapp.android:id/checkoutTitleTV");
    private static final By FULL_NAME_FIELD =
            By.id("com.saucelabs.mydemoapp.android:id/fullNameET");
    private static final By ADDRESS_FIELD   =
            By.id("com.saucelabs.mydemoapp.android:id/address1ET");
    private static final By CITY_FIELD      =
            By.id("com.saucelabs.mydemoapp.android:id/cityET");
    private static final By ZIP_FIELD       =
            By.id("com.saucelabs.mydemoapp.android:id/zipET");
    private static final By COUNTRY_FIELD   =
            By.id("com.saucelabs.mydemoapp.android:id/countryET");
    private static final By TO_PAYMENT_BTN  =
            By.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Page state ───────────────────────────────────────────────────────

    /** Returns true when the "Checkout" title label is visible. */
    public boolean isPageLoaded() {
        return isDisplayed(CHECKOUT_TITLE);
    }

    // ── User actions ─────────────────────────────────────────────────────

    /**
     * Fills all fields of the shipping address form.
     * Returns this to allow chaining with proceedToPayment().
     */
    public CheckoutPage fillShippingAddress(String fullName, String address,
                                            String city, String zip, String country) {
        type(FULL_NAME_FIELD, fullName);
        type(ADDRESS_FIELD,   address);
        type(CITY_FIELD,      city);
        type(ZIP_FIELD,       zip);
        type(COUNTRY_FIELD,   country);
        return this;
    }

    /**
     * Taps the "To Payment" button.
     * Fluent Navigation → transitions away from checkout to the payment screen.
     */
    public void proceedToPayment() {
        click(TO_PAYMENT_BTN);
    }
}

package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Page Object — Product Detail screen.
 *
 * Encapsulation:
 *  • All locators are private static final — hidden from tests.
 *  • Public methods expose user-facing actions only.
 *  • Fluent Navigation: addToCart() returns this (same screen); openCart() transitions to CartPage.
 */
public class ProductDetailPage extends BasePage {

    // ── Private locators ─────────────────────────────────────────────────
    private static final By PRODUCT_LABEL   =
            By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_PRICE   =
            By.id("com.saucelabs.mydemoapp.android:id/priceTV");
    private static final By ADD_TO_CART_BTN =
            By.id("com.saucelabs.mydemoapp.android:id/cartBt");
    private static final By CART_BUTTON     =
            AppiumBy.accessibilityId("View cart");

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Page state ───────────────────────────────────────────────────────

    /** Returns true when the product name label is visible. */
    public boolean isPageLoaded() {
        return isDisplayed(PRODUCT_LABEL);
    }

    // ── User actions ─────────────────────────────────────────────────────

    /** Returns the product name from the detail screen. */
    public String getProductName() {
        return getText(PRODUCT_LABEL);
    }

    /** Returns the product price string, e.g. "$ 29.99". */
    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    /**
     * Taps the "Add To Cart" button.
     * Returns this (same screen) — allows chaining before navigating to cart.
     */
    public ProductDetailPage addToCart() {
        click(ADD_TO_CART_BTN);
        return this;
    }

    /**
     * Taps the cart icon in the top bar.
     * Fluent Navigation → returns CartPage.
     */
    public CartPage openCart() {
        click(CART_BUTTON);
        return new CartPage(driver);
    }
}

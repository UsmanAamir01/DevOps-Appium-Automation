package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object — My Cart screen.
 *
 * Two states:
 *  1. Empty cart  → shows "No Items" text + "Go Shopping" button
 *  2. Filled cart → shows product list + "Proceed To Checkout" button
 *
 * Encapsulation:
 *  • All locators are private static final — hidden from tests.
 *  • Public methods expose user-facing actions only.
 *  • Fluent Navigation: transition methods return the next Page Object.
 */
public class CartPage extends BasePage {

    // ── Private locators ─────────────────────────────────────────────────
    private static final By CART_TITLE       =
            By.xpath("//android.widget.TextView[@text='My Cart']");
    private static final By NO_ITEMS_TEXT    =
            By.xpath("//android.widget.TextView[@text='No Items']");
    private static final By GO_SHOPPING_BTN  =
            By.xpath("//android.widget.Button[@text='Go Shopping']");
    private static final By CART_ITEM_NAMES  =
            By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private static final By TOTAL_PRICE      =
            By.id("com.saucelabs.mydemoapp.android:id/totalPriceTV");
    private static final By PROCEED_CHECKOUT =
            By.id("com.saucelabs.mydemoapp.android:id/cartBt");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Page state ───────────────────────────────────────────────────────

    /** Returns true when the "My Cart" title is visible (empty or filled). */
    public boolean isPageLoaded() {
        return isDisplayed(CART_TITLE);
    }

    /** Returns true when the cart is empty (no items added). */
    public boolean isCartEmpty() {
        return isDisplayed(NO_ITEMS_TEXT);
    }

    // ── User actions ─────────────────────────────────────────────────────

    /** Returns the number of distinct product lines currently in the cart. */
    public int getCartItemCount() {
        try {
            List<WebElement> items = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(CART_ITEM_NAMES));
            return items.size();
        } catch (org.openqa.selenium.TimeoutException e) {
            return 0;
        }
    }

    /** Returns the total price string shown at the bottom of the cart. */
    public String getTotalPrice() {
        return getText(TOTAL_PRICE);
    }

    /**
     * Taps "Go Shopping" on the empty cart screen.
     * Fluent Navigation → returns CatalogPage.
     */
    public CatalogPage goShopping() {
        click(GO_SHOPPING_BTN);
        return new CatalogPage(driver);
    }

    /**
     * Taps "Proceed To Checkout".
     * Fluent Navigation → returns LoginPage (user must log in to continue).
     */
    public LoginPage proceedToCheckout() {
        click(PROCEED_CHECKOUT);
        return new LoginPage(driver);
    }
}

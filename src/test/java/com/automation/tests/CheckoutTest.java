package com.automation.tests;

import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Checkout / Shipping Address screen.
 *
 * Uses POM — no locators or WebElements appear in this class.
 *
 * Navigation chain:
 *   CatalogPage → ProductDetailPage → addToCart → CartPage
 *     → proceedToCheckout → LoginPage → loginToCheckout → CheckoutPage
 */
public class CheckoutTest extends BaseTest {

    private static final String VALID_USER = "bob@example.com";
    private static final String VALID_PASS = "10203040";

    /**
     * Reusable helper: navigates to the CheckoutPage via the full cart/login flow.
     */
    private CheckoutPage navigateToCheckoutPage() {
        CatalogPage catalog   = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart         = detail.openCart();
        LoginPage login       = cart.proceedToCheckout();
        return login.loginToCheckout(VALID_USER, VALID_PASS);
    }

    @Test(description = "Verify checkout page is displayed after completing login from cart")
    public void testCheckoutPageDisplayed() {
        CheckoutPage checkout = navigateToCheckoutPage();

        Assert.assertTrue(checkout.isPageLoaded(),
                "Checkout page should be visible after login from cart");
        System.out.println("✓ Checkout page is displayed");
    }

    @Test(description = "Verify user can fill in shipping address on checkout screen")
    public void testFillShippingAddress() {
        CheckoutPage checkout = navigateToCheckoutPage();

        checkout.fillShippingAddress(
                "John Doe", "123 Main Street", "New York", "10001", "United States");
        System.out.println("✓ Shipping address filled successfully");
    }
}

package com.automation.tests;

import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Checkout / Shipping Address screen.
 */
public class CheckoutTest extends BaseTest {

    @Test(description = "Verify checkout page is displayed after login")
    public void testCheckoutPageDisplayed() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        LoginPage login = cart.proceedToCheckout();
        CheckoutPage checkout = login.login("bob@example.com", "10203040");

        Assert.assertTrue(checkout.isCheckoutDisplayed(),
                "Checkout page should be visible after login");
        System.out.println("✓ Checkout page is displayed");
    }

    @Test(description = "Verify user can fill in shipping address")
    public void testFillShippingAddress() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        LoginPage login = cart.proceedToCheckout();
        CheckoutPage checkout = login.login("bob@example.com", "10203040");

        checkout.fillShippingAddress(
                "John Doe",
                "123 Main Street",
                "New York",
                "10001",
                "United States"
        );
        System.out.println("✓ Shipping address filled successfully");
    }
}


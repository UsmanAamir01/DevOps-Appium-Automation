package com.automation.tests;

import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Login screen.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Verify login screen is shown when proceeding to checkout")
    public void testLoginPageDisplayed() {
        // Navigate: Catalog → Product → Add to Cart → Cart → Proceed to Checkout → Login
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        LoginPage login = cart.proceedToCheckout();

        Assert.assertTrue(login.isLoginDisplayed(), "Login page should be visible");
        System.out.println("✓ Login page displayed successfully");
    }

    @Test(description = "Verify user can log in with valid credentials")
    public void testValidLogin() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        LoginPage login = cart.proceedToCheckout();

        // bob@example.com / 10203040 are default demo credentials
        CheckoutPage checkout = login.login("bob@example.com", "10203040");
        Assert.assertTrue(checkout.isCheckoutDisplayed(),
                "Checkout page should be displayed after successful login");
        System.out.println("✓ Login successful — Checkout page reached");
    }
}


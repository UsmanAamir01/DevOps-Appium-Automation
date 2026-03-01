package com.automation.tests;

import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Login screen.
 *
 * Uses POM — no locators or WebElements appear in this class.
 *
 * Navigation chain for login via checkout:
 *   CatalogPage → ProductDetailPage → addToCart → CartPage → proceedToCheckout → LoginPage
 */
public class LoginTest extends BaseTest {

    private static final String VALID_USER = "bob@example.com";
    private static final String VALID_PASS = "10203040";

    /**
     * Reusable helper: navigates through the cart flow to reach the Login screen.
     */
    private LoginPage navigateToLoginPage() {
        CatalogPage catalog   = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        return cart.proceedToCheckout();
    }

    @Test(description = "Verify the login screen is shown when proceeding to checkout")
    public void testLoginPageDisplayed() {
        LoginPage loginPage = navigateToLoginPage();

        Assert.assertTrue(loginPage.isLoginDisplayed(),
                "Login page should be visible after proceeding to checkout");
        System.out.println("✓ Login page displayed successfully");
    }

    @Test(description = "Verify user can log in with valid credentials and reach the checkout screen")
    public void testValidLogin() {
        LoginPage loginPage = navigateToLoginPage();
        CheckoutPage checkoutPage = loginPage.loginToCheckout(VALID_USER, VALID_PASS);

        Assert.assertTrue(checkoutPage.isPageLoaded(),
                "Checkout page should be displayed after successful login");
        System.out.println("✓ Login successful — Checkout page reached");
    }
}

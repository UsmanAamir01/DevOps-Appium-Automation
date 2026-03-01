package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final String VALID_USER = "bob@example.com";
    private static final String VALID_PASS = "10203040";

    private LoginPage navigateToLoginPage() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();
        return cart.proceedToCheckout();
    }

    @Test(description = "Verify the login screen is shown when proceeding to checkout")
    public void testLoginPageDisplayed() {
        Assert.assertTrue(navigateToLoginPage().isLoginDisplayed(),
                "Login page should be visible after proceeding to checkout");
    }

    @Test(description = "Verify user can log in with valid credentials and reach the checkout screen")
    public void testValidLogin() {
        CheckoutPage checkout = navigateToLoginPage().loginToCheckout(VALID_USER, VALID_PASS);
        Assert.assertTrue(checkout.isPageLoaded(),
                "Checkout page should be displayed after successful login");
    }
}

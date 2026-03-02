package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FunctionalTestCases6to10 extends BaseTest {

    private static final String VALID_USER = "bob@example.com";
    private static final String VALID_PASS = "10203040";

    @Test(description = "TC-06: Cart shows empty state when no product has been added")
    public void tc06_cartIsEmptyOnFreshLaunch() {
        CatalogPage catalog = new CatalogPage(driver);
        Assert.assertTrue(catalog.isPageLoaded(),
                "TC-06 FAIL: Catalog screen did not load");

        CartPage cart = catalog.openCart();
        Assert.assertTrue(cart.isPageLoaded(),
                "TC-06 FAIL: Cart page did not load");
        Assert.assertTrue(cart.isCartEmpty(),
                "TC-06 FAIL: Cart should show empty state when no product was added");
        Assert.assertEquals(cart.getCartItemCount(), 0,
                "TC-06 FAIL: Cart item count should be 0 on empty cart");
    }

    @Test(description = "TC-07: Login page is displayed when proceeding to checkout")
    public void tc07_loginPageShownOnProceedToCheckout() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();

        Assert.assertTrue(cart.isPageLoaded(),
                "TC-07 FAIL: Cart page did not load");
        Assert.assertFalse(cart.isCartEmpty(),
                "TC-07 FAIL: Cart should not be empty before proceeding to checkout");

        LoginPage login = cart.proceedToCheckout();
        Assert.assertTrue(login.isPageLoaded(),
                "TC-07 FAIL: Login page was not displayed after tapping Proceed To Checkout");
    }

    @Test(description = "TC-08: User logs in with valid credentials and reaches checkout")
    public void tc08_validLoginLeadsToCheckoutScreen() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        detail.addToCart();
        LoginPage login = detail.openCart().proceedToCheckout();

        Assert.assertTrue(login.isPageLoaded(),
                "TC-08 FAIL: Login page did not load");

        CheckoutPage checkout = login.loginToCheckout(VALID_USER, VALID_PASS);
        Assert.assertTrue(checkout.isPageLoaded(),
                "TC-08 FAIL: Checkout page was not displayed after successful login");
    }

    @Test(description = "TC-09: Checkout page accepts a complete shipping address")
    public void tc09_checkoutPageAcceptsShippingAddress() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        detail.addToCart();
        LoginPage login    = detail.openCart().proceedToCheckout();
        CheckoutPage checkout = login.loginToCheckout(VALID_USER, VALID_PASS);

        Assert.assertTrue(checkout.isPageLoaded(),
                "TC-09 FAIL: Checkout page did not load");

        checkout.fillShippingAddress(
                "Jane Doe", "456 Oak Avenue", "Los Angeles", "90001", "United States");

        Assert.assertTrue(checkout.isPageLoaded(),
                "TC-09 FAIL: Checkout page disappeared after filling shipping address");
    }

    @Test(description = "TC-10: E2E flow — menu login, catalog browse, product detail verified")
    public void tc10_endToEndLoginAndBrowseFlow() {
        CatalogPage catalog = new CatalogPage(driver);
        Assert.assertTrue(catalog.isPageLoaded(),
                "TC-10 FAIL: Catalog screen did not load on app launch");

        LoginPage loginPage = catalog.openLoginFromMenu();
        Assert.assertTrue(loginPage.isPageLoaded(),
                "TC-10 FAIL: Login screen did not appear after tapping menu login");

        catalog = loginPage.login(VALID_USER, VALID_PASS);
        Assert.assertTrue(catalog.isPageLoaded(),
                "TC-10 FAIL: Catalog screen did not reload after login");

        int productCount = catalog.getProductCount();
        Assert.assertTrue(productCount > 0,
                "TC-10 FAIL: No products found on catalog after login");

        String firstProductName = catalog.getProductNames().get(0);
        Assert.assertFalse(firstProductName.isEmpty(),
                "TC-10 FAIL: First product name was empty after login");

        ProductDetailPage detail = catalog.openFirstProduct();
        Assert.assertTrue(detail.isPageLoaded(),
                "TC-10 FAIL: Product detail page did not load");

        Assert.assertEquals(detail.getProductName(), firstProductName,
                "TC-10 FAIL: Product detail name did not match the name on the catalog");

        String price = detail.getProductPrice();
        Assert.assertNotNull(price, "TC-10 FAIL: Product price was null on detail page");
        Assert.assertTrue(price.contains("$"),
                "TC-10 FAIL: Product price did not contain '$', got: " + price);
    }
}


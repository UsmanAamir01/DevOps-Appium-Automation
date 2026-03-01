package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-01 to TC-05 — First 5 functional test cases.
 * Each test is fully independent: the app is re-launched fresh by @BeforeMethod in BaseTest.
 */
public class FunctionalTestCases1to5 extends BaseTest {

    // ── TC-01 ─────────────────────────────────────────────────────────────
    // Verify the app launches successfully and the catalog screen is displayed.
    @Test(description = "TC-01: App launches and catalog screen is displayed")
    public void tc01_appLaunchShowsCatalogScreen() {
        CatalogPage catalog = new CatalogPage(driver);

        Assert.assertTrue(catalog.isPageLoaded(),
                "TC-01 FAIL: Catalog screen was not displayed after app launch");
    }

    // ── TC-02 ─────────────────────────────────────────────────────────────
    // Verify the catalog screen lists at least one product with a non-empty name.
    @Test(description = "TC-02: Catalog lists products with names")
    public void tc02_catalogListsProductsWithNames() {
        CatalogPage catalog = new CatalogPage(driver);

        int count = catalog.getProductCount();
        Assert.assertTrue(count > 0,
                "TC-02 FAIL: No products were listed on the catalog screen");

        String firstName = catalog.getProductNames().get(0);
        Assert.assertNotNull(firstName,
                "TC-02 FAIL: First product name was null");
        Assert.assertFalse(firstName.isEmpty(),
                "TC-02 FAIL: First product name was empty");
    }

    // ── TC-03 ─────────────────────────────────────────────────────────────
    // Verify tapping a product opens its detail page showing a name and price.
    @Test(description = "TC-03: Tapping a product opens detail page with name and price")
    public void tc03_productDetailShowsNameAndPrice() {
        CatalogPage catalog = new CatalogPage(driver);
        Assert.assertTrue(catalog.isPageLoaded(),
                "TC-03 FAIL: Catalog screen did not load");

        ProductDetailPage detail = catalog.openFirstProduct();
        Assert.assertTrue(detail.isPageLoaded(),
                "TC-03 FAIL: Product detail page did not load after tapping a product");

        String name = detail.getProductName();
        Assert.assertNotNull(name, "TC-03 FAIL: Product name was null");
        Assert.assertFalse(name.isEmpty(), "TC-03 FAIL: Product name was empty");

        String price = detail.getProductPrice();
        Assert.assertNotNull(price, "TC-03 FAIL: Product price was null");
        Assert.assertTrue(price.contains("$"),
                "TC-03 FAIL: Product price did not contain '$', got: " + price);
    }

    // ── TC-04 ─────────────────────────────────────────────────────────────
    // Verify user can add a product to the cart from the detail screen.
    @Test(description = "TC-04: User can add a product to the cart from detail screen")
    public void tc04_addProductToCartFromDetailScreen() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        Assert.assertTrue(detail.isPageLoaded(),
                "TC-04 FAIL: Product detail page did not load");

        detail.addToCart();

        CartPage cart = detail.openCart();
        Assert.assertTrue(cart.isPageLoaded(),
                "TC-04 FAIL: Cart page did not load after tapping cart icon");
        Assert.assertTrue(cart.getCartItemCount() > 0,
                "TC-04 FAIL: Cart was empty after adding a product");
    }

    // ── TC-05 ─────────────────────────────────────────────────────────────
    // Verify the cart shows the correct item count and a non-empty total price.
    @Test(description = "TC-05: Cart shows correct item count and total price")
    public void tc05_cartShowsItemCountAndTotalPrice() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();

        Assert.assertTrue(cart.isPageLoaded(),
                "TC-05 FAIL: Cart page did not load");

        int itemCount = cart.getCartItemCount();
        Assert.assertEquals(itemCount, 1,
                "TC-05 FAIL: Expected 1 item in cart but found: " + itemCount);

        String totalPrice = cart.getTotalPrice();
        Assert.assertNotNull(totalPrice, "TC-05 FAIL: Total price was null");
        Assert.assertFalse(totalPrice.isEmpty(), "TC-05 FAIL: Total price was empty");
        Assert.assertTrue(totalPrice.contains("$"),
                "TC-05 FAIL: Total price did not contain '$', got: " + totalPrice);
    }
}


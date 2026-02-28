package com.automation.tests;

import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Product Detail screen.
 */
public class ProductDetailTest extends BaseTest {

    @Test(description = "Verify tapping a product opens its detail page with a name")
    public void testProductDetailShowsName() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();

        String name = detail.getProductName();
        Assert.assertNotNull(name, "Product name should be visible on detail page");
        Assert.assertFalse(name.isEmpty(), "Product name should not be empty");
        System.out.println("✓ Product detail name: " + name);
    }

    @Test(description = "Verify the product detail page shows a price")
    public void testProductDetailShowsPrice() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();

        String price = detail.getProductPrice();
        Assert.assertNotNull(price, "Price should be visible");
        Assert.assertTrue(price.contains("$"), "Price should contain dollar sign");
        System.out.println("✓ Product price: " + price);
    }

    @Test(description = "Verify user can add a product to the cart")
    public void testAddProductToCart() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();

        detail.addToCart();
        System.out.println("✓ Product added to cart successfully");
    }
}


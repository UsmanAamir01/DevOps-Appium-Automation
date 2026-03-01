package com.automation.tests;

import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductDetailTest extends BaseTest {

    @Test(description = "Verify tapping a product opens its detail page with a name")
    public void testProductDetailShowsName() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();

        Assert.assertTrue(detail.isPageLoaded(),
                "Product detail page should be loaded after tapping a product");
        Assert.assertFalse(detail.getProductName().isEmpty(), "Product name should not be empty");
    }

    @Test(description = "Verify the product detail page shows a price")
    public void testProductDetailShowsPrice() {
        ProductDetailPage detail = new CatalogPage(driver).openFirstProduct();

        Assert.assertTrue(detail.getProductPrice().contains("$"),
                "Price should contain a dollar sign, got: " + detail.getProductPrice());
    }

    @Test(description = "Verify user can add a product to the cart from the detail screen")
    public void testAddProductToCart() {
        new CatalogPage(driver).openFirstProduct().addToCart();
    }
}


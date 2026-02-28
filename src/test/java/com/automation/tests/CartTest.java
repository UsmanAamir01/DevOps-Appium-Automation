package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Cart screen.
 */
public class CartTest extends BaseTest {

    @Test(description = "Verify the cart shows the added item")
    public void testCartDisplaysAddedItem() {
        // Navigate: Catalog → Product Detail → Add to Cart → Open Cart
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();

        Assert.assertTrue(cart.isCartDisplayed(), "Cart page should be visible");
        Assert.assertTrue(cart.getCartItemCount() > 0, "Cart should have at least one item");
        System.out.println("✓ Cart has " + cart.getCartItemCount() + " item(s)");
    }

    @Test(description = "Verify the cart shows a total price")
    public void testCartShowsTotalPrice() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        CartPage cart = detail.openCart();

        String total = cart.getTotalPrice();
        Assert.assertNotNull(total, "Total price should be visible");
        System.out.println("✓ Cart total: " + total);
    }
}


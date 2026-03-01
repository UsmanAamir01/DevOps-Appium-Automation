package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Cart screen.
 *
 * Uses POM — no locators or WebElements appear in this class.
 * Navigation chain: CatalogPage → ProductDetailPage → addToCart → openCart → CartPage.
 */
public class CartTest extends BaseTest {

    /**
     * Reusable helper: navigates to the cart after adding the first product.
     */
    private CartPage addFirstProductAndOpenCart() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailPage detail = catalog.openFirstProduct();
        detail.addToCart();
        return detail.openCart();
    }

    @Test(description = "Verify the cart screen is displayed after adding an item")
    public void testCartPageLoads() {
        CartPage cart = addFirstProductAndOpenCart();

        Assert.assertTrue(cart.isPageLoaded(),
                "Cart page should be visible after adding a product");
        System.out.println("✓ Cart page loaded successfully");
    }

    @Test(description = "Verify the cart shows the added item")
    public void testCartDisplaysAddedItem() {
        CartPage cart = addFirstProductAndOpenCart();

        Assert.assertTrue(cart.getCartItemCount() > 0,
                "Cart should have at least one item after adding a product");
        System.out.println("✓ Cart has " + cart.getCartItemCount() + " item(s)");
    }

    @Test(description = "Verify the cart shows a total price")
    public void testCartShowsTotalPrice() {
        CartPage cart = addFirstProductAndOpenCart();

        String total = cart.getTotalPrice();
        Assert.assertNotNull(total, "Total price should be visible in the cart");
        Assert.assertFalse(total.isEmpty(), "Total price should not be empty");
        System.out.println("✓ Cart total: " + total);
    }
}

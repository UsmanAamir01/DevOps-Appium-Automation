package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

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
    }

    @Test(description = "Verify the cart shows the added item")
    public void testCartDisplaysAddedItem() {
        CartPage cart = addFirstProductAndOpenCart();

        Assert.assertTrue(cart.getCartItemCount() > 0,
                "Cart should have at least one item after adding a product");
    }

    @Test(description = "Verify the cart shows a total price")
    public void testCartShowsTotalPrice() {
        CartPage cart = addFirstProductAndOpenCart();

        String total = cart.getTotalPrice();
        Assert.assertNotNull(total, "Total price should be visible in the cart");
        Assert.assertFalse(total.isEmpty(), "Total price should not be empty");
    }
}

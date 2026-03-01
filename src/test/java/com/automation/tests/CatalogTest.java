package com.automation.tests;

import com.automation.pages.CatalogPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CatalogTest extends BaseTest {

    @Test(description = "Verify the app launches and the catalog screen is displayed")
    public void testAppLaunchShowsCatalog() {
        CatalogPage catalog = new CatalogPage(driver);

        Assert.assertTrue(catalog.isPageLoaded(),
                "Catalog screen should be visible after app launch");
    }

    @Test(description = "Verify products are listed on the catalog screen")
    public void testProductsAreListed() {
        CatalogPage catalog = new CatalogPage(driver);
        int count = catalog.getProductCount();

        Assert.assertTrue(count > 0,
                "At least one product should be listed on the catalog");
    }

    @Test(description = "Verify first product name is not empty")
    public void testFirstProductHasName() {
        CatalogPage catalog = new CatalogPage(driver);
        String name = catalog.getProductNames().get(0);

        Assert.assertNotNull(name, "First product name should not be null");
        Assert.assertFalse(name.isEmpty(), "First product name should not be empty");
    }
}

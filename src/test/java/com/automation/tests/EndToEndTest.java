package com.automation.tests;

import com.automation.pages.CatalogPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    private static final String USERNAME = "bob@example.com";
    private static final String PASSWORD = "10203040";

    @Test(description = "E2E: catalog → menu login → verify catalog → open product → verify detail")
    public void testFullNavigationFlow() {
        CatalogPage catalogPage = new CatalogPage(driver);
        Assert.assertTrue(catalogPage.isPageLoaded(),
                "FAIL — Catalog screen did not load on app launch.");

        LoginPage loginPage = catalogPage.openLoginFromMenu();
        Assert.assertTrue(loginPage.isPageLoaded(),
                "FAIL — Login screen did not load after tapping menu Log In.");

        catalogPage = loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue(catalogPage.isPageLoaded(),
                "FAIL — Catalog screen did not load after login.");

        int productCount = catalogPage.getProductCount();
        Assert.assertTrue(productCount > 0,
                "FAIL — No products found on the Catalog screen.");

        ProductDetailPage detailPage = catalogPage.openFirstProduct();
        Assert.assertTrue(detailPage.isPageLoaded(),
                "FAIL — Product Detail screen did not load after tapping a product.");

        String productName = detailPage.getProductName();
        Assert.assertFalse(productName.isEmpty(),
                "FAIL — Product name on Detail screen is empty.");

        String productPrice = detailPage.getProductPrice();
        Assert.assertTrue(productPrice.contains("$"),
                "FAIL — Product price does not contain '$': " + productPrice);
    }
}

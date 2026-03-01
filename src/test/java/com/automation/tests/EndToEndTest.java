package com.automation.tests;

import com.automation.pages.CatalogPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * End-to-End test — proves the full navigation chain works across multiple screens.
 *
 * Flow:
 *   Launch app
 *     → Catalog screen (app opens here)             ← Assert: page loaded
 *       → tap Menu → "Log In"
 *         → Login screen                            ← Assert: page loaded
 *           → enter credentials → tap Login
 *             → Catalog screen (logged-in user)     ← Assert: loaded + products > 0
 *               → tap first product
 *                 → Product Detail screen           ← Assert: name + price visible
 *
 * Architecture rules enforced:
 *  • No Thread.sleep() — all waits are driven by BasePage via WebDriverWait.
 *  • No locators or WebElements in this test class.
 *  • Every screen transition uses Fluent Navigation (method returns next Page Object).
 */
public class EndToEndTest extends BaseTest {

    private static final String USERNAME = "bob@example.com";
    private static final String PASSWORD = "10203040";

    @Test(description = "E2E: catalog → menu login → verify catalog → open product → verify detail")
    public void testFullNavigationFlow() {

        // ── Step 1: App launches on the Catalog screen ───────────────────
        CatalogPage catalogPage = new CatalogPage(driver);
        Assert.assertTrue(catalogPage.isPageLoaded(),
                "FAIL — Catalog screen did not load on app launch.");
        System.out.println("✓ Step 1 passed: Catalog screen loaded on launch.");

        // ── Step 2: Open hamburger menu → tap "Log In" ───────────────────
        LoginPage loginPage = catalogPage.openLoginFromMenu();
        Assert.assertTrue(loginPage.isPageLoaded(),
                "FAIL — Login screen did not load after tapping menu Log In.");
        System.out.println("✓ Step 2 passed: Login screen loaded via menu.");

        // ── Step 3: Log in with valid credentials ────────────────────────
        catalogPage = loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue(catalogPage.isPageLoaded(),
                "FAIL — Catalog screen did not load after login.");
        System.out.println("✓ Step 3 passed: Catalog screen loaded after login.");

        // ── Step 4: Verify products are listed ───────────────────────────
        int productCount = catalogPage.getProductCount();
        Assert.assertTrue(productCount > 0,
                "FAIL — No products found on the Catalog screen.");
        System.out.println("✓ Step 4 passed: " + productCount + " product(s) visible.");

        // ── Step 5: Open the first product ───────────────────────────────
        ProductDetailPage detailPage = catalogPage.openFirstProduct();
        Assert.assertTrue(detailPage.isPageLoaded(),
                "FAIL — Product Detail screen did not load after tapping a product.");
        System.out.println("✓ Step 5 passed: Product Detail screen loaded.");

        // ── Step 6: Verify product name is not empty ─────────────────────
        String productName = detailPage.getProductName();
        Assert.assertFalse(productName.isEmpty(),
                "FAIL — Product name on Detail screen is empty.");
        System.out.println("✓ Step 6 passed: Product name = \"" + productName + "\"");

        // ── Step 7: Verify product price contains "$" ────────────────────
        String productPrice = detailPage.getProductPrice();
        Assert.assertTrue(productPrice.contains("$"),
                "FAIL — Product price does not contain '$': " + productPrice);
        System.out.println("✓ Step 7 passed: Product price = " + productPrice);

        System.out.println("✅ End-to-End test completed — all pages verified.");
    }
}

package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object — Products screen.
 *
 * This is an alias for CatalogPage used by flows that start directly
 * on the products listing without going through the menu login.
 *
 * Encapsulation:
 *  • All locators are private static final — hidden from tests.
 *  • Public methods expose user-facing actions only.
 *  • Fluent Navigation: transition methods return the next Page Object.
 */
public class ProductsPage extends BasePage {

    // ── Private locators ─────────────────────────────────────────────────
    private static final By PRODUCTS_TITLE     = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_ITEM_TITLE = By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private static final By PRODUCT_ITEM_IMAGE = By.id("com.saucelabs.mydemoapp.android:id/productIV");

    public ProductsPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Page state ───────────────────────────────────────────────────────

    /** Returns true when the Products title banner is visible. */
    public boolean isPageLoaded() {
        return isDisplayed(PRODUCTS_TITLE);
    }

    // ── User actions ─────────────────────────────────────────────────────

    /** Returns a list of all visible product names on screen. */
    public List<String> getAllProductNames() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_TITLE));
        return elements.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    /** Returns the total number of visible products. */
    public int getProductCount() {
        return getAllProductNames().size();
    }

    /** Returns the product name at the given zero-based index. */
    public String getProductNameAt(int index) {
        return getAllProductNames().get(index);
    }

    /**
     * Taps the first product image.
     * Fluent Navigation → returns ProductDetailPage.
     */
    public ProductDetailPage openFirstProduct() {
        List<WebElement> images = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_IMAGE));
        images.get(0).click();
        return new ProductDetailPage(driver);
    }

    /**
     * Finds a product by name and taps it.
     * Fluent Navigation → returns ProductDetailPage.
     *
     * @throws IllegalArgumentException if no product with that name is found on screen.
     */
    public ProductDetailPage openProductByName(String name) {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_TITLE));
        for (WebElement el : elements) {
            if (el.getText().trim().equalsIgnoreCase(name)) {
                el.click();
                return new ProductDetailPage(driver);
            }
        }
        throw new IllegalArgumentException("Product not found on screen: " + name);
    }
}

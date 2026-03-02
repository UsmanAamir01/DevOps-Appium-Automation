package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    private static final By PRODUCTS_TITLE     = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_ITEM_TITLE = By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private static final By PRODUCT_ITEM_IMAGE = By.id("com.saucelabs.mydemoapp.android:id/productIV");

    public ProductsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(PRODUCTS_TITLE);
    }

    public List<String> getAllProductNames() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_TITLE));
        return elements.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public int getProductCount() {
        return getAllProductNames().size();
    }

    public String getProductNameAt(int index) {
        return getAllProductNames().get(index);
    }

    public ProductDetailPage openFirstProduct() {
        List<WebElement> images = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_IMAGE));
        images.get(0).click();
        return new ProductDetailPage(driver);
    }

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

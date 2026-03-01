package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ProductDetailPage extends BasePage {

    private static final By PRODUCT_LABEL   = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_PRICE   = By.id("com.saucelabs.mydemoapp.android:id/priceTV");
    private static final By ADD_TO_CART_BTN = By.id("com.saucelabs.mydemoapp.android:id/cartBt");
    private static final By CART_BUTTON     = AppiumBy.accessibilityId("View cart");

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(PRODUCT_LABEL);
    }

    public String getProductName() {
        return getText(PRODUCT_LABEL);
    }

    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    public ProductDetailPage addToCart() {
        click(ADD_TO_CART_BTN);
        return this;
    }

    public CartPage openCart() {
        click(CART_BUTTON);
        return new CartPage(driver);
    }
}

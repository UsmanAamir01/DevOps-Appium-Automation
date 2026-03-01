package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends BasePage {

    private static final By PRODUCTS_TITLE      = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_NAMES       = AppiumBy.accessibilityId("Product Title");
    private static final By FIRST_PRODUCT_IMAGE = By.xpath("(//android.widget.ImageView[@content-desc='Product Image'])[1]");
    private static final By MENU_BUTTON         = AppiumBy.accessibilityId("View menu");
    private static final By MENU_LOGIN_ITEM     = AppiumBy.accessibilityId("Login Menu Item");
    private static final By CART_BUTTON         = AppiumBy.accessibilityId("View cart");

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(PRODUCTS_TITLE);
    }

    public List<String> getProductNames() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_NAMES));
        return elements.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public int getProductCount() {
        return getProductNames().size();
    }

    public ProductDetailPage openFirstProduct() {
        click(FIRST_PRODUCT_IMAGE);
        return new ProductDetailPage(driver);
    }

    public LoginPage openLoginFromMenu() {
        click(MENU_BUTTON);
        click(MENU_LOGIN_ITEM);
        return new LoginPage(driver);
    }

    public CartPage openCart() {
        click(CART_BUTTON);
        return new CartPage(driver);
    }
}

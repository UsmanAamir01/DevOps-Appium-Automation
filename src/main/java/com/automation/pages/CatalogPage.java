package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends BasePage {

    private static final By PRODUCTS_TITLE      = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By PRODUCT_ITEM_TITLE  = By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private static final By PRODUCT_ITEM_IMAGE  = By.id("com.saucelabs.mydemoapp.android:id/productIV");
    private static final By MENU_BUTTON         = By.id("com.saucelabs.mydemoapp.android:id/menuIV");
    private static final By MENU_LOGIN_ITEM     = By.xpath("//android.widget.TextView[@text='Log In']");
    private static final By CART_BUTTON         = By.id("com.saucelabs.mydemoapp.android:id/cartRL");

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(PRODUCTS_TITLE);
    }

    public List<String> getProductNames() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_TITLE));
        return elements.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public int getProductCount() {
        return getProductNames().size();
    }

    public ProductDetailPage openFirstProduct() {
        List<WebElement> images = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEM_IMAGE));
        images.get(0).click();
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

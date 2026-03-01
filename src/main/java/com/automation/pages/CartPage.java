package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private static final By CART_TITLE       = By.xpath("//android.widget.TextView[@text='My Cart']");
    private static final By NO_ITEMS_TEXT    = By.xpath("//android.widget.TextView[@text='No Items']");
    private static final By GO_SHOPPING_BTN  = By.xpath("//android.widget.Button[@text='Go Shopping']");
    private static final By CART_ITEM_NAMES  = By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private static final By TOTAL_PRICE      = By.id("com.saucelabs.mydemoapp.android:id/totalPriceTV");
    private static final By PROCEED_CHECKOUT = By.id("com.saucelabs.mydemoapp.android:id/cartBt");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(CART_TITLE);
    }

    public boolean isCartEmpty() {
        return isDisplayed(NO_ITEMS_TEXT);
    }

    public int getCartItemCount() {
        try {
            List<WebElement> items = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(CART_ITEM_NAMES));
            return items.size();
        } catch (org.openqa.selenium.TimeoutException e) {
            return 0;
        }
    }

    public String getTotalPrice() {
        return getText(TOTAL_PRICE);
    }

    public CatalogPage goShopping() {
        click(GO_SHOPPING_BTN);
        return new CatalogPage(driver);
    }

    public LoginPage proceedToCheckout() {
        click(PROCEED_CHECKOUT);
        return new LoginPage(driver);
    }
}

package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private static final By CART_SCREEN      = AppiumBy.accessibilityId("cart screen");
    private static final By NO_ITEMS_TEXT    = AppiumBy.accessibilityId("No Items");
    private static final By GO_SHOPPING_BTN  = AppiumBy.accessibilityId("Go Shopping button");
    private static final By CART_ITEM_LABEL  = AppiumBy.accessibilityId("product label");
    private static final By TOTAL_PRICE      = AppiumBy.accessibilityId("total price");
    private static final By PROCEED_CHECKOUT = AppiumBy.accessibilityId("Proceed To Checkout button");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(CART_SCREEN);
    }

    public boolean isCartEmpty() {
        return isDisplayed(NO_ITEMS_TEXT);
    }

    public int getCartItemCount() {
        try {
            List<WebElement> items = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(CART_ITEM_LABEL));
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

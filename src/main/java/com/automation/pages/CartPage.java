package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the Cart / My Cart screen.
 */
public class CartPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My Cart']")
    private WebElement myCartTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='product label']")
    private List<WebElement> cartItemNames;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='product price']")
    private List<WebElement> cartItemPrices;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed To Checkout button']")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='total price']")
    private WebElement totalPrice;

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    /** Returns true if the My Cart screen is displayed. */
    public boolean isCartDisplayed() {
        return myCartTitle.isDisplayed();
    }

    /** Returns the number of items currently in the cart. */
    public int getCartItemCount() {
        return cartItemNames.size();
    }

    /** Returns the total price text shown at the bottom. */
    public String getTotalPrice() {
        return totalPrice.getText();
    }

    /** Taps Proceed To Checkout, which navigates to the Login page. */
    public LoginPage proceedToCheckout() {
        proceedToCheckoutButton.click();
        return new LoginPage(driver);
    }
}


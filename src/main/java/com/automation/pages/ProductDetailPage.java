package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Product Detail screen.
 * Reached after tapping a product on the Catalog page.
 */
public class ProductDetailPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='product label']")
    private WebElement productLabel;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='product price']")
    private WebElement productPrice;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Add To Cart button']")
    private WebElement addToCartButton;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='cart badge']")
    private WebElement cartBadge;

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
    }

    /** Returns the product name shown on the detail page. */
    public String getProductName() {
        return productLabel.getText();
    }

    /** Returns the product price text, e.g. "$29.99". */
    public String getProductPrice() {
        return productPrice.getText();
    }

    /** Taps the Add To Cart button. */
    public void addToCart() {
        addToCartButton.click();
    }

    /** Taps the cart badge icon to navigate to the Cart page. */
    public CartPage openCart() {
        cartBadge.click();
        return new CartPage(driver);
    }
}


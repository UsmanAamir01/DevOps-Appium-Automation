package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the Catalog / Products listing screen.
 * This is the first screen displayed after the app launches.
 */
public class CatalogPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Products']")
    private WebElement productsTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='store item text']")
    private List<WebElement> productNames;

    @AndroidFindBy(xpath = "(//android.widget.ImageView[@content-desc='store item image'])[1]")
    private WebElement firstProductImage;

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    /** Returns true when the Products title is visible. */
    public boolean isCatalogDisplayed() {
        return productsTitle.isDisplayed();
    }

    /** Returns the number of visible products on screen. */
    public int getProductCount() {
        return productNames.size();
    }

    /** Returns the name text of a product at the given index (0-based). */
    public String getProductName(int index) {
        return productNames.get(index).getText();
    }

    /** Taps the first product to navigate to the Product Detail page. */
    public ProductDetailPage openFirstProduct() {
        firstProductImage.click();
        return new ProductDetailPage(driver);
    }
}


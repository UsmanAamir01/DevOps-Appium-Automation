package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Checkout / Shipping Address screen.
 * Shown after a successful login during the checkout flow.
 */
public class CheckoutPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Checkout']")
    private WebElement checkoutTitle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Full Name* input field']")
    private WebElement fullNameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Address Line 1* input field']")
    private WebElement addressLine1Field;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='City* input field']")
    private WebElement cityField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Zip Code* input field']")
    private WebElement zipCodeField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Country* input field']")
    private WebElement countryField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='To Payment button']")
    private WebElement toPaymentButton;

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    /** Returns true if the Checkout title is visible. */
    public boolean isCheckoutDisplayed() {
        return checkoutTitle.isDisplayed();
    }

    /** Fills in the shipping address form. */
    public void fillShippingAddress(String fullName, String address, String city,
                                    String zip, String country) {
        fullNameField.clear();
        fullNameField.sendKeys(fullName);

        addressLine1Field.clear();
        addressLine1Field.sendKeys(address);

        cityField.clear();
        cityField.sendKeys(city);

        zipCodeField.clear();
        zipCodeField.sendKeys(zip);

        countryField.clear();
        countryField.sendKeys(country);
    }

    /** Taps To Payment to proceed to the payment screen. */
    public void proceedToPayment() {
        toPaymentButton.click();
    }
}


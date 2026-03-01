package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By USERNAME_FIELD = By.id("com.saucelabs.mydemoapp.android:id/nameET");
    private static final By PASSWORD_FIELD = By.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private static final By LOGIN_BUTTON   = By.id("com.saucelabs.mydemoapp.android:id/loginBtn");
    private static final By LOGIN_HEADER   = By.xpath("//android.widget.TextView[@text='Login']");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(LOGIN_HEADER);
    }

    public boolean isLoginDisplayed() {
        return isPageLoaded();
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME_FIELD, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD_FIELD, password);
        return this;
    }

    public CatalogPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return new CatalogPage(driver);
    }

    public CheckoutPage loginToCheckout(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return new CheckoutPage(driver);
    }
}

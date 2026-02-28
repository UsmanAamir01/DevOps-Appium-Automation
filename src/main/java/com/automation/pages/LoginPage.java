package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Login screen.
 * Shown when the user proceeds to checkout without being logged in.
 */
public class LoginPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Username input field']")
    private WebElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='Password input field']")
    private WebElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Login button']")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
    private WebElement loginTitle;

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /** Returns true if the Login screen title is visible. */
    public boolean isLoginDisplayed() {
        return loginTitle.isDisplayed();
    }

    /** Types the given username. */
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /** Types the given password. */
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /** Taps the Login button. */
    public CheckoutPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        loginButton.click();
        return new CheckoutPage(driver);
    }
}


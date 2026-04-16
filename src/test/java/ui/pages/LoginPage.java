package ui.pages;

import framework.config.ConfigManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @Step("Open login page")
    public void open() {
        driver.get(ConfigManager.get("base.ui.url"));
    }

    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    @Step("Click login button")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Login with username: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Get error message text")
    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    @Step("Check error message displayed")
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
}
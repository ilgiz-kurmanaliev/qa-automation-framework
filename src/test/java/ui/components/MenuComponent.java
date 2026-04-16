package ui.components;

import framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.pages.BasePage;
import ui.pages.LoginPage;

public class MenuComponent extends BasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public void openMenu() {
        click(burgerMenuButton);
        WaitUtils.getWait().until(ExpectedConditions.visibilityOf(logoutLink));
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        WaitUtils.getWait().until(ExpectedConditions.urlContains("saucedemo.com"));
        return new LoginPage();
    }
}
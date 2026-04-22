package ui.tests;

import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test(description = "Verify valid login")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        Assert.assertTrue(
                inventoryPage.isInventoryListDisplayed(),
                "Inventory list is not displayed after login"
        );
    }

    @Test(description = "Verify invalid login")
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.login("wrong_user", "wrong_password");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Login error message is not displayed"
        );
    }
}
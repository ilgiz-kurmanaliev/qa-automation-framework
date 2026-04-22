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
import ui.components.MenuComponent;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Logout")
public class LogoutTests extends BaseTest {

    @Test(description = "Verify user can logout successfully")
    @Story("Successful Logout")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void logoutTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        MenuComponent menu = inventoryPage.header().menu();
        menu.openMenu();
        LoginPage loginPageAfterLogout = menu.clickLogout();

        Assert.assertTrue(
                loginPageAfterLogout.getCurrentUrl().contains("saucedemo.com"),
                "Expected to return to login page after logout"
        );
    }
}
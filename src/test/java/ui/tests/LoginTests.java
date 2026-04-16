package ui.tests;

import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ui.assertions.LoginAssertions;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test(description = "Verify valid user can login successfully")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        LoginAssertions.assertLoginSuccessful(inventoryPage);
    }

    @Test(description = "Verify invalid user sees error message")
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.open();
        loginPage.login("wrong_user", "wrong_pass");

        LoginAssertions.assertLoginErrorDisplayed(loginPage);
        LoginAssertions.assertLoginErrorTextContains(loginPage, "Username and password do not match");
    }
}
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
import testdata.DataProviders;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Advanced Login")
public class AdvancedLoginTests extends BaseTest {

    @Test(
            dataProvider = "loginData",
            dataProviderClass = DataProviders.class,
            description = "Verify login with multiple credential sets"
    )
    @Story("Data Driven Login")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void loginWithMultipleCredentialsTest(String username, String password, boolean shouldLoginSuccessfully) {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login(username, password);

        if (shouldLoginSuccessfully) {
            loginPage.waitForInventoryPageToLoad();

            Assert.assertTrue(
                    inventoryPage.isInventoryListDisplayed(),
                    "Expected successful login"
            );
        } else {
            Assert.assertTrue(
                    loginPage.isErrorMessageDisplayed(),
                    "Expected login error message"
            );
        }
    }
}
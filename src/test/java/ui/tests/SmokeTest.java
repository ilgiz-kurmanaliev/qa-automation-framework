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
@Feature("Smoke")
public class SmokeTest extends BaseTest {

    @Test(description = "Verify smoke login flow")
    @Story("Smoke Login")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void frameworkSmokeTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        Assert.assertTrue(
                inventoryPage.isInventoryListDisplayed(),
                "Smoke test failed: inventory page is not displayed"
        );
    }
}
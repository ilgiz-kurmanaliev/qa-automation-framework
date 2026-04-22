package ui.tests;

import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ui.assertions.InventoryAssertions;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Inventory")
public class InventoryTests extends BaseTest {

    @Test(description = "Verify inventory page loads successfully")
    @Story("Inventory Page Load")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void inventoryPageLoadedTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        InventoryAssertions.assertInventoryPageOpened(inventoryPage);
        InventoryAssertions.assertProductsLoaded(inventoryPage);
    }

    @Test(description = "Verify user can add first product to cart")
    @Story("Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void addFirstProductToCartTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.addFirstProductToCart();

        InventoryAssertions.assertCartBadgeCount(inventoryPage, 1);
        InventoryAssertions.assertFirstProductButtonText(inventoryPage, "Remove");
    }
}
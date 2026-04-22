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
@Feature("Advanced Cart")
public class CartAdvancedTests extends BaseTest {

    @Test(description = "Verify user can remove product from cart on inventory page")
    @Story("Remove Product from Cart")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void removeProductFromCartTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.addFirstProductToCart();
        InventoryAssertions.assertCartBadgeCount(inventoryPage, 1);
        InventoryAssertions.assertFirstProductButtonText(inventoryPage, "Remove");

        inventoryPage.removeFirstProductFromCart();

        InventoryAssertions.assertCartBadgeCount(inventoryPage, 0);
        InventoryAssertions.assertFirstProductButtonText(inventoryPage, "Add to cart");
    }
}
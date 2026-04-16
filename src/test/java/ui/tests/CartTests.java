package ui.tests;

import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ui.assertions.CartAssertions;
import ui.pages.CartPage;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Cart")
public class CartTests extends BaseTest {

    @Test(description = "Verify added product appears in cart")
    @Story("Open Cart with Product")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void openCartWithAddedProductTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();

        CartAssertions.assertCartPageOpened(cartPage);
        CartAssertions.assertCartItemsCount(cartPage, 1);
    }
}
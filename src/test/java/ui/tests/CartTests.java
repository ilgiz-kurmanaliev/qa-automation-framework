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
import ui.pages.CartPage;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Cart")
public class CartTests extends BaseTest {

    @Test(description = "Verify user can open cart with added product")
    @Story("Open Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void openCartWithAddedProductTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();

        Assert.assertTrue(
                cartPage.isCartPageDisplayed(),
                "Cart page is not displayed"
        );
        Assert.assertEquals(
                cartPage.getCartItemsCount(),
                1,
                "Cart item count is incorrect"
        );
    }
}
package ui.assertions;

import org.testng.Assert;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

public class LoginAssertions {

    public static void assertLoginSuccessful(InventoryPage inventoryPage) {
        Assert.assertTrue(
                inventoryPage.isInventoryListDisplayed(),
                "Inventory list is not displayed after login"
        );
    }

    public static void assertLoginErrorDisplayed(LoginPage loginPage) {
        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Expected login error message is not displayed"
        );
    }

    public static void assertLoginErrorTextContains(LoginPage loginPage, String expectedText) {
        Assert.assertTrue(
                loginPage.getErrorMessageText().contains(expectedText),
                "Expected text was not found in error message"
        );
    }
}
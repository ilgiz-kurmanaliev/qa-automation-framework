package ui.assertions;

import org.testng.Assert;
import ui.pages.CartPage;

public class CartAssertions {

    public static void assertCartPageOpened(CartPage cartPage) {
        Assert.assertTrue(
                cartPage.isCartPageDisplayed(),
                "Cart page is not displayed"
        );
    }

    public static void assertCartItemsCount(CartPage cartPage, int expectedCount) {
        Assert.assertEquals(
                cartPage.getCartItemsCount(),
                expectedCount,
                "Cart items count is incorrect"
        );
    }
}
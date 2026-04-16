package ui.assertions;

import org.testng.Assert;
import ui.pages.InventoryPage;

public class InventoryAssertions {

    public static void assertInventoryPageOpened(InventoryPage inventoryPage) {
        Assert.assertEquals(
                inventoryPage.getPageTitle(),
                "Products",
                "Inventory page title is incorrect"
        );
    }

    public static void assertProductsLoaded(InventoryPage inventoryPage) {
        Assert.assertTrue(
                inventoryPage.getInventoryItemsCount() > 0,
                "No products were loaded on inventory page"
        );
    }

    public static void assertCartBadgeCount(InventoryPage inventoryPage, int expectedCount) {
        Assert.assertEquals(
                inventoryPage.header().getCartBadgeCount(),
                expectedCount,
                "Cart badge count is incorrect"
        );
    }

    public static void assertFirstProductButtonText(InventoryPage inventoryPage, String expectedText) {
        Assert.assertEquals(
                inventoryPage.getFirstProductButtonText(),
                expectedText,
                "Unexpected first product button text"
        );
    }
}
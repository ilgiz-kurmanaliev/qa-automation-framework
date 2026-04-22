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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Epic("UI Tests")
@Feature("Sorting")
public class SortingTests extends BaseTest {

    @Test(description = "Verify products can be sorted by name A to Z")
    @Story("Sort by Name A to Z")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void sortByNameAToZTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.selectSortOption("Name (A to Z)");

        List<String> actualNames = inventoryPage.getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        Assert.assertEquals(actualNames, expectedNames, "Products are not sorted A to Z");
    }

    @Test(description = "Verify products can be sorted by price low to high")
    @Story("Sort by Price Low to High")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void sortByPriceLowToHighTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.selectSortOption("Price (low to high)");

        List<Double> actualPrices = inventoryPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices, "Products are not sorted by price low to high");
    }
}
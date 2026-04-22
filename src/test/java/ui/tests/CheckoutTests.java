package ui.tests;

import framework.utils.RandomDataUtils;
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
import ui.pages.CheckoutCompletePage;
import ui.pages.CheckoutOverviewPage;
import ui.pages.CheckoutPage;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

@Epic("UI Tests")
@Feature("Checkout")
public class CheckoutTests extends BaseTest {

    @Test(description = "Verify user can complete checkout successfully")
    @Story("Complete Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void completeCheckoutTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        CheckoutOverviewPage overviewPage = checkoutPage.fillCheckoutFormAndContinue(
                RandomDataUtils.getFirstName(),
                RandomDataUtils.getLastName(),
                "12345"
        );

        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "Checkout overview page was not opened"
        );

        overviewPage.clickFinish();

        CheckoutCompletePage completePage = new CheckoutCompletePage();

        Assert.assertTrue(
                completePage.isCheckoutCompletePageDisplayed(),
                "Checkout complete page is not displayed"
        );

        Assert.assertEquals(
                completePage.getCompleteHeaderText(),
                "Thank you for your order!",
                "Checkout completion header text is incorrect"
        );
    }

    @Test(description = "Verify checkout shows validation error when fields are empty")
    @Story("Checkout Validation Error")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void checkoutValidationErrorTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.clickContinue();

        Assert.assertTrue(
                checkoutPage.isErrorMessageDisplayed(),
                "Checkout error message is not displayed"
        );

        Assert.assertTrue(
                checkoutPage.getErrorMessageText().contains("Error"),
                "Unexpected checkout error message text"
        );
    }
}
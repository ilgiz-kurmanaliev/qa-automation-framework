package ui.tests;

import framework.utils.RandomDataUtils;
import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ui.assertions.CheckoutAssertions;
import ui.pages.*;

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

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.fillCheckoutFormAndContinue(
                RandomDataUtils.getFirstName(),
                RandomDataUtils.getLastName(),
                "12345"
        );

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        CheckoutAssertions.assertCheckoutOverviewOpened(overviewPage);

        overviewPage.clickFinish();

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        CheckoutAssertions.assertCheckoutComplete(completePage);
        CheckoutAssertions.assertCheckoutCompleteHeader(completePage, "Thank you for your order!");
    }

    @Test(description = "Verify checkout shows validation error when fields are empty")
    @Story("Checkout Validation Error")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void checkoutValidationErrorTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstProductToCart();

        CartPage cartPage = inventoryPage.header().clickCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.clickContinue();

        CheckoutAssertions.assertCheckoutErrorDisplayed(checkoutPage);
    }
}
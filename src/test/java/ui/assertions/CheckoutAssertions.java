package ui.assertions;

import org.testng.Assert;
import ui.pages.CheckoutCompletePage;
import ui.pages.CheckoutOverviewPage;
import ui.pages.CheckoutPage;

public class CheckoutAssertions {

    public static void assertCheckoutOverviewOpened(CheckoutOverviewPage checkoutOverviewPage) {
        Assert.assertEquals(
                checkoutOverviewPage.getPageTitle(),
                "Checkout: Overview",
                "Checkout overview page title is incorrect"
        );
    }

    public static void assertCheckoutErrorDisplayed(CheckoutPage checkoutPage) {
        Assert.assertTrue(
                checkoutPage.isErrorMessageDisplayed(),
                "Checkout error message is not displayed"
        );
    }

    public static void assertCheckoutComplete(CheckoutCompletePage completePage) {
        Assert.assertTrue(
                completePage.isCheckoutCompletePageDisplayed(),
                "Checkout complete page is not displayed"
        );
    }

    public static void assertCheckoutCompleteHeader(CheckoutCompletePage completePage, String expectedText) {
        Assert.assertEquals(
                completePage.getCompleteHeaderText(),
                expectedText,
                "Checkout completion header text is incorrect"
        );
    }
}
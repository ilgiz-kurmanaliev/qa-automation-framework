package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public void waitForPageToLoad() {
        WaitUtils.getWait().until(ExpectedConditions.urlContains("cart.html"));
        waitForVisibility(pageTitle);
    }

    public int getCartItemsCount() {
        waitForPageToLoad();
        return cartItems.size();
    }

    public String getPageTitle() {
        waitForPageToLoad();
        return getText(pageTitle);
    }

    public boolean isCartPageDisplayed() {
        try {
            waitForPageToLoad();
            return getCurrentUrl().contains("cart.html")
                    && getText(pageTitle).equalsIgnoreCase("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    public CheckoutPage clickCheckout() {
        waitForPageToLoad();

        try {
            click(checkoutButton);
        } catch (Exception e) {
            WebElement button = driver.findElement(By.id("checkout"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

        WaitUtils.getWait().until(ExpectedConditions.urlContains("checkout-step-one.html"));
        return new CheckoutPage();
    }
}
package ui.components;

import framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.pages.BasePage;
import ui.pages.CartPage;

public class HeaderComponent extends BasePage {

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    private final MenuComponent menuComponent = new MenuComponent();

    public CartPage clickCart() {
        click(cartIcon);
        WaitUtils.getWait().until(ExpectedConditions.urlContains("cart.html"));
        return new CartPage();
    }

    public int getCartBadgeCount() {
        try {
            waitForVisibility(cartIcon);

            if (cartBadge.isDisplayed()) {
                String badgeText = cartBadge.getText().trim();
                return Integer.parseInt(badgeText);
            }
        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    public boolean isCartIconDisplayed() {
        try {
            return isDisplayed(cartIcon);
        } catch (Exception e) {
            return false;
        }
    }

    public MenuComponent menu() {
        return menuComponent;
    }
}
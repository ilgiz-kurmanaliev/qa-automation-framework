package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.components.HeaderComponent;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    private final By backpackAddButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By backpackRemoveButton = By.id("remove-sauce-labs-backpack");
    private final By cartBadge = By.className("shopping_cart_badge");

    private final HeaderComponent headerComponent = new HeaderComponent();

    public void waitForPageToLoad() {
        waitForVisibility(inventoryList);
        waitForVisibility(pageTitle);
    }

    public boolean isInventoryListDisplayed() {
        try {
            waitForPageToLoad();
            return inventoryList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getInventoryItemsCount() {
        waitForPageToLoad();
        return inventoryItems.size();
    }

    public String getPageTitle() {
        waitForPageToLoad();
        return getText(pageTitle);
    }

    public HeaderComponent header() {
        return headerComponent;
    }

    public void addFirstProductToCart() {
        waitForPageToLoad();

        By addButtonLocator = backpackAddButton;
        By removeButtonLocator = backpackRemoveButton;

        clickWithRetry(addButtonLocator);

        WaitUtils.getWait().until(driver -> {
            try {
                boolean removeVisible = !driver.findElements(removeButtonLocator).isEmpty();
                List<WebElement> badges = driver.findElements(cartBadge);

                return removeVisible
                        && !badges.isEmpty()
                        && "1".equals(badges.get(0).getText().trim());
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        By removeButtonLocator = backpackRemoveButton;
        By addButtonLocator = backpackAddButton;

        clickWithRetry(removeButtonLocator);

        WaitUtils.getWait().until(driver -> {
            try {
                boolean addVisible = !driver.findElements(addButtonLocator).isEmpty();
                boolean badgeGone = driver.findElements(cartBadge).isEmpty();
                return addVisible && badgeGone;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    public String getFirstProductButtonText() {
        waitForPageToLoad();

        List<WebElement> removeButtons = driver.findElements(backpackRemoveButton);
        if (!removeButtons.isEmpty() && removeButtons.get(0).isDisplayed()) {
            return removeButtons.get(0).getText().trim();
        }

        List<WebElement> addButtons = driver.findElements(backpackAddButton);
        if (!addButtons.isEmpty() && addButtons.get(0).isDisplayed()) {
            return addButtons.get(0).getText().trim();
        }

        return "";
    }

    public void selectSortOption(String visibleText) {
        waitForPageToLoad();
        new Select(sortDropdown).selectByVisibleText(visibleText);
    }

    public List<String> getProductNames() {
        waitForPageToLoad();

        List<String> names = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            names.add(item.findElement(By.className("inventory_item_name")).getText());
        }

        return names;
    }

    public List<Double> getProductPrices() {
        waitForPageToLoad();

        List<Double> prices = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            String price = item.findElement(By.className("inventory_item_price"))
                    .getText()
                    .replace("$", "")
                    .trim();

            prices.add(Double.parseDouble(price));
        }

        return prices;
    }

    private void clickWithRetry(By locator) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement element = WaitUtils.getWait().until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
                click(element);
                return;
            } catch (Exception e) {
                lastException = new RuntimeException("Failed to click element on attempt " + attempt, e);
            }
        }

        throw lastException;
    }
}
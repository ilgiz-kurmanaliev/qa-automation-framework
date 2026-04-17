package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.components.HeaderComponent;

import java.time.Duration;
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

        boolean added = false;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement addButton = WaitUtils.getWait().until(
                        ExpectedConditions.presenceOfElementLocated(backpackAddButton)
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", addButton
                );

                try {
                    WaitUtils.getWait().until(ExpectedConditions.elementToBeClickable(backpackAddButton));
                    addButton.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
                }

                waitUntilProductAdded();
                added = true;
                break;

            } catch (Exception ignored) {
                // следующая попытка
            }
        }

        if (!added) {
            throw new RuntimeException("Failed to add backpack product to cart after 3 attempts");
        }
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        // если товара ещё нет в корзине — сначала добавим
        if (driver.findElements(backpackRemoveButton).isEmpty()) {
            addFirstProductToCart();
        }

        boolean removed = false;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement removeButton = WaitUtils.getWait().until(
                        ExpectedConditions.presenceOfElementLocated(backpackRemoveButton)
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", removeButton
                );

                try {
                    WaitUtils.getWait().until(ExpectedConditions.elementToBeClickable(backpackRemoveButton));
                    removeButton.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeButton);
                }

                waitUntilProductRemoved();
                removed = true;
                break;

            } catch (Exception ignored) {
                // следующая попытка
            }
        }

        if (!removed) {
            throw new RuntimeException("Failed to remove backpack product from cart after 3 attempts");
        }
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

    private void waitUntilProductAdded() {
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(10).toMillis();

        while (System.currentTimeMillis() < endTime) {
            boolean removeVisible = !driver.findElements(backpackRemoveButton).isEmpty();
            List<WebElement> badges = driver.findElements(cartBadge);

            if (removeVisible && !badges.isEmpty()) {
                String badgeText = badges.get(0).getText().trim();
                if ("1".equals(badgeText)) {
                    return;
                }
            }

            sleep(300);
        }

        throw new TimeoutException("Product was not added to cart in time");
    }

    private void waitUntilProductRemoved() {
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(10).toMillis();

        while (System.currentTimeMillis() < endTime) {
            boolean addVisible = !driver.findElements(backpackAddButton).isEmpty();
            boolean badgeGone = driver.findElements(cartBadge).isEmpty();

            if (addVisible && badgeGone) {
                return;
            }

            sleep(300);
        }

        throw new TimeoutException("Product was not removed from cart in time");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}
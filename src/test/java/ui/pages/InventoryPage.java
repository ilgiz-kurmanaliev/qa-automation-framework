package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        if (!getCurrentUrl().contains("inventory.html")) {
            throw new RuntimeException("Inventory page is not opened. Current URL: " + getCurrentUrl());
        }

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

        if (isBackpackAdded()) {
            return;
        }

        boolean success = clickUntilStateChanges(backpackAddButton, this::isBackpackAdded);

        if (!success) {
            throw new RuntimeException("Failed to add backpack product to cart after 3 attempts");
        }
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        if (!isBackpackAdded()) {
            addFirstProductToCart();
        }

        boolean success = clickUntilStateChanges(backpackRemoveButton, this::isBackpackRemoved);

        if (!success) {
            throw new RuntimeException("Failed to remove backpack product from cart after 3 attempts");
        }
    }

    public String getFirstProductButtonText() {
        waitForPageToLoad();

        List<WebElement> removeButtons = driver.findElements(backpackRemoveButton);
        if (!removeButtons.isEmpty()) {
            return removeButtons.get(0).getText().trim();
        }

        List<WebElement> addButtons = driver.findElements(backpackAddButton);
        if (!addButtons.isEmpty()) {
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

    private boolean clickUntilStateChanges(By buttonLocator, StateCheck expectedState) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                clickElement(buttonLocator);

                long endTime = System.currentTimeMillis() + 5000;
                while (System.currentTimeMillis() < endTime) {
                    if (expectedState.matches()) {
                        return true;
                    }
                    sleep(250);
                }

            } catch (Exception e) {
                lastException = new RuntimeException(
                        "Failed to click element " + buttonLocator + " on attempt " + attempt, e
                );
            }
        }

        if (lastException != null) {
            throw lastException;
        }

        return false;
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
            return;
        } catch (Exception ignored) {
        }

        try {
            element = driver.findElement(locator);
            new Actions(driver)
                    .moveToElement(element)
                    .pause(Duration.ofMillis(200))
                    .click()
                    .perform();
            return;
        } catch (Exception ignored) {
        }

        element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private boolean isBackpackAdded() {
        try {
            boolean removeExists = !driver.findElements(backpackRemoveButton).isEmpty();

            List<WebElement> badges = driver.findElements(cartBadge);
            boolean badgeExists = !badges.isEmpty();
            boolean badgeIsOne = badgeExists && "1".equals(badges.get(0).getText().trim());

            return removeExists && badgeIsOne;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private boolean isBackpackRemoved() {
        try {
            boolean addExists = !driver.findElements(backpackAddButton).isEmpty();
            boolean removeMissing = driver.findElements(backpackRemoveButton).isEmpty();
            boolean badgeMissing = driver.findElements(cartBadge).isEmpty();

            return addExists && removeMissing && badgeMissing;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
    }

    @FunctionalInterface
    private interface StateCheck {
        boolean matches();
    }
}
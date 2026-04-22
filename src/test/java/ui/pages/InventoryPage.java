package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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

        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                clickElementRobustly(backpackAddButton);
                waitUntilBackpackAdded();
                return;
            } catch (Exception e) {
                lastException = new RuntimeException(
                        "Failed to add backpack on attempt " + attempt, e
                );
                reopenInventoryPage();
            }
        }

        throw new RuntimeException("Failed to add backpack product to cart after 3 attempts", lastException);
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        if (!isBackpackAdded()) {
            addFirstProductToCart();
        }

        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                clickElementRobustly(backpackRemoveButton);
                waitUntilBackpackRemoved();
                return;
            } catch (Exception e) {
                lastException = new RuntimeException(
                        "Failed to remove backpack on attempt " + attempt, e
                );
                reopenInventoryPage();
            }
        }

        throw new RuntimeException("Failed to remove backpack product from cart after 3 attempts", lastException);
    }

    public String getFirstProductButtonText() {
        waitForPageToLoad();

        if (!driver.findElements(backpackRemoveButton).isEmpty()) {
            return driver.findElement(backpackRemoveButton).getText().trim();
        }

        if (!driver.findElements(backpackAddButton).isEmpty()) {
            return driver.findElement(backpackAddButton).getText().trim();
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

    private void clickElementRobustly(By locator) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement element = WaitUtils.getWait().until(
                        ExpectedConditions.presenceOfElementLocated(locator)
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element
                );

                try {
                    WaitUtils.getWait().until(ExpectedConditions.elementToBeClickable(locator));
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
                return;

            } catch (Exception e) {
                lastException = new RuntimeException(
                        "Failed to click element " + locator + " on attempt " + attempt, e
                );
            }
        }

        throw lastException;
    }

    private void waitUntilBackpackAdded() {
        long endTime = System.currentTimeMillis() + 12000;

        while (System.currentTimeMillis() < endTime) {
            if (isBackpackAdded()) {
                return;
            }
            sleep(300);
        }

        throw new TimeoutException("Backpack was not added to cart in time");
    }

    private void waitUntilBackpackRemoved() {
        long endTime = System.currentTimeMillis() + 12000;

        while (System.currentTimeMillis() < endTime) {
            if (isBackpackRemoved()) {
                return;
            }
            sleep(300);
        }

        throw new TimeoutException("Backpack was not removed from cart in time");
    }

    private boolean isBackpackAdded() {
        try {
            boolean removeVisible = !driver.findElements(backpackRemoveButton).isEmpty();
            List<WebElement> badges = driver.findElements(cartBadge);
            boolean badgeVisible = !badges.isEmpty();
            boolean badgeIsOne = badgeVisible && "1".equals(badges.get(0).getText().trim());

            return removeVisible && badgeIsOne;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private boolean isBackpackRemoved() {
        try {
            boolean addVisible = !driver.findElements(backpackAddButton).isEmpty();
            boolean removeGone = driver.findElements(backpackRemoveButton).isEmpty();
            boolean badgeGone = driver.findElements(cartBadge).isEmpty();

            return addVisible && removeGone && badgeGone;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private void reopenInventoryPage() {
        driver.get("https://www.saucedemo.com/inventory.html");
        waitForPageToLoad();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
    }
}
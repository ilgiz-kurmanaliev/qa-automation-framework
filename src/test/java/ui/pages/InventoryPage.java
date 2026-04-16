package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
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

    public HeaderComponent header() {
        return headerComponent;
    }

    public void addFirstProductToCart() {
        waitForPageToLoad();

        By firstButtonLocator = By.xpath("(//div[@class='inventory_item']//button)[1]");

        WebElement button = WaitUtils.getWait().until(
                ExpectedConditions.elementToBeClickable(firstButtonLocator)
        );

        button.click();

        WaitUtils.getWait().until(driver -> {
            WebElement updatedButton = driver.findElement(firstButtonLocator);
            return updatedButton.getText().contains("Remove");
        });
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        By firstButtonLocator = By.xpath("(//div[@class='inventory_item']//button)[1]");

        WebElement button = WaitUtils.getWait().until(
                ExpectedConditions.elementToBeClickable(firstButtonLocator)
        );

        button.click();

        WaitUtils.getWait().until(driver -> {
            WebElement updatedButton = driver.findElement(firstButtonLocator);
            return updatedButton.getText().contains("Add to cart");
        });
    }

    public String getFirstProductButtonText() {
        waitForPageToLoad();

        By firstButtonLocator = By.xpath("(//div[@class='inventory_item']//button)[1]");

        WebElement button = WaitUtils.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(firstButtonLocator)
        );

        return button.getText().trim();
    }

    public int getInventoryItemsCount() {
        waitForPageToLoad();
        return inventoryItems.size();
    }

    public String getPageTitle() {
        waitForPageToLoad();
        return getText(pageTitle);
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
}
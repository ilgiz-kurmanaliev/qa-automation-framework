package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

        if (!inventoryItems.isEmpty()) {
            WebElement firstButton = inventoryItems.get(0).findElement(By.tagName("button"));
            click(firstButton);
        }
    }

    public void removeFirstProductFromCart() {
        waitForPageToLoad();

        if (!inventoryItems.isEmpty()) {
            WebElement firstButton = inventoryItems.get(0).findElement(By.tagName("button"));
            click(firstButton);
        }
    }

    public String getFirstProductButtonText() {
        waitForPageToLoad();

        if (!inventoryItems.isEmpty()) {
            WebElement firstButton = inventoryItems.get(0).findElement(By.tagName("button"));
            return firstButton.getText();
        }

        return "";
    }

    public void selectSortOption(String visibleText) {
        waitForPageToLoad();
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(visibleText);
    }

    public List<String> getProductNames() {
        waitForPageToLoad();

        List<String> productNames = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            productNames.add(name);
        }

        return productNames;
    }

    public List<Double> getProductPrices() {
        waitForPageToLoad();

        List<Double> productPrices = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            String priceText = item.findElement(By.className("inventory_item_price")).getText();
            priceText = priceText.replace("$", "").trim();
            productPrices.add(Double.parseDouble(priceText));
        }

        return productPrices;
    }
}
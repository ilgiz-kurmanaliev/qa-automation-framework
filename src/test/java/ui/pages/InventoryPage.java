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

    public boolean isInventoryListDisplayed() {
        return isDisplayed(inventoryList);
    }

    public int getInventoryItemsCount() {
        return inventoryItems.size();
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public HeaderComponent header() {
        return headerComponent;
    }

    public void addFirstProductToCart() {
        if (!inventoryItems.isEmpty()) {
            inventoryItems.get(0).findElement(By.tagName("button")).click();
        }
    }

    public void removeFirstProductFromCart() {
        if (!inventoryItems.isEmpty()) {
            inventoryItems.get(0).findElement(By.tagName("button")).click();
        }
    }

    public String getFirstProductButtonText() {
        if (!inventoryItems.isEmpty()) {
            return inventoryItems.get(0)
                    .findElement(By.tagName("button"))
                    .getText();
        }
        return "";
    }

    public void selectSortOption(String visibleText) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(visibleText);
    }

    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            productNames.add(name);
        }

        return productNames;
    }

    public List<Double> getProductPrices() {
        List<Double> productPrices = new ArrayList<>();

        for (WebElement item : inventoryItems) {
            String priceText = item.findElement(By.className("inventory_item_price")).getText();
            priceText = priceText.replace("$", "").trim();
            productPrices.add(Double.parseDouble(priceText));
        }

        return productPrices;
    }
}
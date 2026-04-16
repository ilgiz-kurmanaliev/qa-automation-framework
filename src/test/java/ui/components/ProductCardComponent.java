package ui.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCardComponent {

    private final WebElement rootElement;

    public ProductCardComponent(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getProductName() {
        return rootElement.findElement(By.className("inventory_item_name")).getText();
    }

    public String getProductPrice() {
        return rootElement.findElement(By.className("inventory_item_price")).getText();
    }

    public void clickAddToCartButton() {
        rootElement.findElement(By.tagName("button")).click();
    }

    public String getButtonText() {
        return rootElement.findElement(By.tagName("button")).getText();
    }
}

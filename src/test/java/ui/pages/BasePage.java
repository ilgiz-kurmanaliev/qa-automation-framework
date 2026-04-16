package ui.pages;

import framework.driver.DriverManager;
import framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        WaitUtils.getWait().until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        WaitUtils.getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        waitForVisibility(element);
        return element.isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
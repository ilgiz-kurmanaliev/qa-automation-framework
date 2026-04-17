package ui.pages;

import framework.driver.DriverFactory;
import framework.utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        WaitUtils.getWait().until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        WaitUtils.getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    protected void click(WebElement element) {
        waitForClickability(element);
        scrollIntoView(element);

        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        scrollIntoView(element);
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
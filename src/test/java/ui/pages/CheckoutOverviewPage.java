package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    public void waitForPageToLoad() {
        WaitUtils.getWait().until(ExpectedConditions.urlContains("checkout-step-two.html"));
        waitForVisibility(pageTitle);
    }

    public String getPageTitle() {
        waitForPageToLoad();
        return getText(pageTitle);
    }

    public void clickFinish() {
        waitForPageToLoad();
        click(finishButton);
    }

    public boolean isOverviewPageDisplayed() {
        try {
            waitForPageToLoad();
            return getCurrentUrl().contains("checkout-step-two.html");
        } catch (Exception e) {
            return false;
        }
    }
}
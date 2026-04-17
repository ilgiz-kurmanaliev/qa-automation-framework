package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    public void waitForPageToLoad() {
        WaitUtils.getWait().until(ExpectedConditions.urlContains("checkout-complete.html"));
        waitForVisibility(pageTitle);
        waitForVisibility(completeHeader);
    }

    public boolean isCheckoutCompletePageDisplayed() {
        try {
            waitForPageToLoad();
            return getCurrentUrl().contains("checkout-complete.html");
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompleteHeaderText() {
        waitForPageToLoad();
        return getText(completeHeader);
    }
}
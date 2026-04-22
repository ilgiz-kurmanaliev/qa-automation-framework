package ui.pages;

import framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    private final By errorMessageLocator = By.cssSelector("[data-test='error']");

    public void waitForPageToLoad() {
        WaitUtils.getWait().until(ExpectedConditions.urlContains("checkout-step-one.html"));
        waitForVisibility(pageTitle);
        waitForVisibility(continueButton);
    }

    public void enterFirstName(String firstName) {
        waitForPageToLoad();
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        waitForPageToLoad();
        type(lastNameInput, lastName);
    }

    public void enterPostalCode(String postalCode) {
        waitForPageToLoad();
        type(postalCodeInput, postalCode);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        waitForPageToLoad();

        try {
            click(continueButton);
        } catch (Exception e) {
            WebElement button = driver.findElement(By.id("continue"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    public CheckoutOverviewPage fillCheckoutFormAndContinue(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        clickContinue();
        WaitUtils.getWait().until(ExpectedConditions.urlContains("checkout-step-two.html"));
        return new CheckoutOverviewPage();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.getWait().until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
            return driver.findElement(errorMessageLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        WaitUtils.getWait().until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
        return driver.findElement(errorMessageLocator).getText().trim();
    }
}
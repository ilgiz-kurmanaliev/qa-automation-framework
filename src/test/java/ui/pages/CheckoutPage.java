package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void enterFirstName(String firstName) {
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameInput, lastName);
    }

    public void enterPostalCode(String postalCode) {
        type(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void fillCheckoutFormAndContinue(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        clickContinue();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
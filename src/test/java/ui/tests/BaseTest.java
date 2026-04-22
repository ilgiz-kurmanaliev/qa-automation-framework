package ui.tests;

import framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ui.pages.LoginPage;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();

        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForInventoryPageToLoad();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
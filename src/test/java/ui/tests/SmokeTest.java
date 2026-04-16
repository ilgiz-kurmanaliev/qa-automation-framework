package ui.tests;

import framework.config.ConfigManager;
import framework.driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void frameworkSmokeTest() {
        DriverManager.getDriver().get(ConfigManager.get("base.ui.url"));
        String currentUrl = DriverManager.getDriver().getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("saucedemo"),
                "Framework smoke test failed: incorrect URL"
        );
    }
}
package framework.utils;

import framework.config.ConfigManager;
import framework.driver.DriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static WebDriverWait getWait() {
        int timeout = Integer.parseInt(ConfigManager.get("timeout"));
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
    }
}
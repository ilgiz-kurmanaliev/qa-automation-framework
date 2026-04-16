package framework.driver;

import framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static void initDriver() {
        String browser = ConfigManager.get("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // отключаем всплывашки браузера
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck,AutofillServerCommunication");
            options.addArguments("--guest");

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            prefs.put("autofill.profile_enabled", false);
            prefs.put("autofill.credit_card_enabled", false);

            options.setExperimentalOption("prefs", prefs);

            WebDriver driver = new ChromeDriver(options);
            DriverManager.setDriver(driver);
            DriverManager.getDriver().manage().window().maximize();
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
    }

    public static void quitDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}
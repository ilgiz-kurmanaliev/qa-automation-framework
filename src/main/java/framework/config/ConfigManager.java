package framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try {
            String env = System.getProperty("env", "qa");
            FileInputStream fileInputStream =
                    new FileInputStream("src/test/resources/configs/" + env + ".properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
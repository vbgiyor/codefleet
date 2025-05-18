package com.codefleet.cfinspector.modules.config;

import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties envProperties = new Properties();
    private static final Properties locatorProperties = new Properties();

    static {
        // Load environment properties (data.properties)
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("data.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to load data.properties file.");
            }
            envProperties.load(input);
        } catch (Exception e) {
            LoggerUtil.error("Failed to load data.properties", e);
            throw new RuntimeException("Failed to load data.properties", e);
        }

        // Load locator properties (locators.properties)
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("locators.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to load locators.properties file.");
            }
            locatorProperties.load(input);
        } catch (Exception e) {
            LoggerUtil.error("Failed to load locators.properties", e);
            throw new RuntimeException("Failed to load locators.properties", e);
        }
    }

    public static String getEnvProperty(String key, String defaultValue) {
        return envProperties.getProperty(key, defaultValue);
    }

    public static String getLocatorProperty(String key) {
        return locatorProperties.getProperty(key);
    }

    public static Properties getLocatorProperties() {
        return locatorProperties;
    }

    public static String getBaseUrl() {
        return envProperties.getProperty("baseUrl");
    }

    public static String getBrowser() {
        String browser = System.getProperty("browser");
        LoggerUtil.info("System property browser: " + browser);
        if (browser == null || browser.isEmpty()) {
            browser = envProperties.getProperty("browser", "chrome");
            LoggerUtil.info("Using browser from data.properties: " + browser);
        } else {
            LoggerUtil.info("Using browser from TestNG parameter: " + browser);
        }
        return browser;
    }

    public static int getImplicitTime() {
        return Integer.parseInt(envProperties.getProperty("implicit.wait.seconds", "10"));
    }

    public static int getPageLoadTimeOut() {
        return Integer.parseInt(envProperties.getProperty("page.load.timeout.seconds", "30"));
    }

    public static int getRetryAttempts() {
        return Integer.parseInt(envProperties.getProperty("retry.attempts", "1"));
    }

    public static boolean isHeadless() {
        String headless = envProperties.getProperty("headless", "false");
        return Boolean.parseBoolean(headless.trim());
    }
}
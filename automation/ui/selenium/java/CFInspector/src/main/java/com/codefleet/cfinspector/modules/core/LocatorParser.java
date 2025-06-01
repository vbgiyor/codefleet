package com.codefleet.cfinspector.modules.core;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import org.openqa.selenium.By;
import java.util.concurrent.ConcurrentHashMap;

/* This class acts as backbone for implementing object repository.
 * Each locator is parsed based on the fixed set of supported locator names.
 * This class helps to bring better abstraction and easy maintenance of UI related page classes.
 */
public class LocatorParser {
    private static final ConcurrentHashMap<String, By> locatorCache = new ConcurrentHashMap<>();

    public enum LocatorType {
        ID, TAGNAME, LINKTEXT, PARTIALLINK, CSS, NAME, XPATH;

        public static LocatorType fromString(String type) {
            try {
                return valueOf(type.toUpperCase().replace(" ", ""));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid locator type: " + type);
            }
        }
    }

    public By getElementLocator(String locatorKey) {
        // Check cache first
        return locatorCache.computeIfAbsent(locatorKey, key -> {
            String propertyString = ConfigManager.getLocatorProperty(key);
            if (propertyString == null) {
                throw new RuntimeException("Locator key not found: " + key);
            }

            if (!propertyString.contains("!")) {
                throw new RuntimeException("Invalid locator format for key " + key + ": " + propertyString + ". Expected format: locatorType!locatorValue");
            }

            String locatorType = propertyString.split("!")[0];
            String locatorValue = propertyString.split("!")[1];
            By locator;

            switch (LocatorType.fromString(locatorType)) {
                case ID:
                    locator = By.id(locatorValue);
                    break;
                case TAGNAME:
                    locator = By.tagName(locatorValue);
                    break;
                case LINKTEXT:
                    locator = By.linkText(locatorValue);
                    break;
                case PARTIALLINK:
                    locator = By.partialLinkText(locatorValue);
                    break;
                case CSS:
                    locator = By.cssSelector(locatorValue);
                    break;
                case NAME:
                    locator = By.name(locatorValue);
                    break;
                case XPATH:
                    locator = By.xpath(locatorValue);
                    break;
                default:
                    throw new RuntimeException("Unsupported locator type: " + locatorType);
            }
            return locator;
        });
    }

    // Method to clear cache for future dynamic locator support
    public void clearCache() {
        locatorCache.clear();
    }
}
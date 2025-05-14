package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.LocatorParser;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class TestSetup {

    public void validateLocators() {
        Properties locatorProps = ConfigManager.getLocatorProperties();
        Set<String> locatorKeys = locatorProps.stringPropertyNames();

        // List of expected locator keys
        List<String> expectedKeys = Arrays.asList(
                "home.title",
                "home.description",
                "automation.title",
                "automation.ab_testing_link",
                "automation.add_remove_link",
                "abtest.headline",
                "abtest.paragraph",
                "abtest.elemental_selenium_link"
        );

        // Validate expected keys exist
        for (String expectedKey : expectedKeys) {
            if (!locatorKeys.contains(expectedKey)) {
                throw new RuntimeException("Missing expected locator key in locators.properties: " + expectedKey);
            }
        }

        // Validate format and locator types
        LocatorParser.LocatorType[] validLocatorTypes = LocatorParser.LocatorType.values();
        for (String key : locatorKeys) {
            String value = locatorProps.getProperty(key);
            if (!value.contains("!")) {
                throw new RuntimeException("Invalid locator format for key " + key + ": " + value + ". Expected format: locatorType!locatorValue");
            }

            String locatorType = value.split("!")[0];
            try {
                LocatorParser.LocatorType.fromString(locatorType);
            } catch (RuntimeException e) {
                throw new RuntimeException("Invalid locator type for key " + key + ": " + locatorType + ". Valid types: " + Arrays.toString(validLocatorTypes));
            }
        }
    }
}
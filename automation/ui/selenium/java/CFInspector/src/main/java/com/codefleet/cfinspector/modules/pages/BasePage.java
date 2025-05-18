package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.CoreActions;
import com.codefleet.cfinspector.modules.core.LocatorParser;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import com.codefleet.cfinspector.modules.utils.WaitForElementsUtil;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage implements CoreActions {
    protected WebDriver driver;
    protected LocatorParser locatorParser;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.locatorParser = new LocatorParser();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public void click(WebElement element) {
        int attempts = 0;
        int maxAttempts = ConfigManager.getRetryAttempts();

        while (attempts <= maxAttempts) {
            try {
                WaitForElementsUtil.waitUntilElementToBeClickable(driver, element);
                assert element != null;
                LoggerUtil.info("Successful click for element: " + element.getText());
                element.click();
                return; // Exit the method once the click is successful
            } catch (TimeoutException e) {
                attempts++;
                if (attempts > maxAttempts) {
                    LoggerUtil.error("Unable to click element: " + (element != null ? element.getText() : "null element") +
                            " after " + maxAttempts + " retries.", e);
                    throw e; // Throw the exception after the last attempt
                }
                LoggerUtil.info("Retry " + attempts + "/" + maxAttempts + " - Element: " +
                        (element != null ? element.getText() : "null element") + " is not clickable yet.");
            }
        }
    }

    @Override
    public void insetText(WebElement element, String text) {
        WaitForElementsUtil.waitUntilElementToBeClickable(driver, element);
        element.clear();
        element.sendKeys(text);
        LoggerUtil.info("Text insert successful.");
    }

    @Override
    public String getText(WebElement element) {
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, element);
        return element.getText();
    }

    @Override
    public boolean isElementDisplayed(WebElement element) {
        int attempts = 0;
        int maxAttempts = ConfigManager.getRetryAttempts();

        while (attempts < maxAttempts) {
            try {
                WaitForElementsUtil.waitUntilElementToBeVisible(driver, element);
                boolean displayed = element.isDisplayed();
                if (displayed) {
                    LoggerUtil.info("Element: "+ element.getText() +" is displayed.");
                }
                return displayed;
            } catch (TimeoutException e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    LoggerUtil.error("Element: "+ element.getText() +" is not visible after "
                            + maxAttempts + ".", e);
                    return false;
                }
            }
            LoggerUtil.info("Element: "+ element.getText() +" is visible. Retrying (" + attempts + "/"
                    + maxAttempts + ".");
        }
        return false;
    }
}
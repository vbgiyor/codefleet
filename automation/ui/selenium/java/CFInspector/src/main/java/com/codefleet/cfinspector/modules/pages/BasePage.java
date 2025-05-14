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
                element.click();
                LoggerUtil.info("Successful element click.");
                return;
            } catch (TimeoutException e) {
                attempts++;
                if (attempts > maxAttempts) {
                    LoggerUtil.error("Unable to click element after " + maxAttempts + " retries.", e);
                    throw e;
                }
                LoggerUtil.info("Element is not clickable. Retrying in " + attempts + "/" + maxAttempts + ".");
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
                    LoggerUtil.info("Element is displayed.");
                }
                return displayed;
            } catch (TimeoutException e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    LoggerUtil.error("Element is not visible after " + maxAttempts + ".", e);
                    return false;
                }
            }
            LoggerUtil.info("Element is visible. Retrying (" + attempts + "/" + maxAttempts + ".");
        }
        return false;
    }
}
package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.CoreActions;
import com.codefleet.cfinspector.modules.core.LocatorParser;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import com.codefleet.cfinspector.modules.utils.WaitForElementsUtil;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public void navigateToPreviousPage() {
        driver.navigate().back();
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
                String elementText = element.getText();
                // Use JavaScript to click the element
                LoggerUtil.info("Successful click for element: " + elementText);
                element.click();
                return;
            } catch (TimeoutException | org.openqa.selenium.ElementClickInterceptedException e) {
                attempts++;
                // Take screenshot for debugging
                if (attempts > maxAttempts) {
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    try {
                        Files.copy(screenshot.toPath(), Paths.get("screenshot_" + System.currentTimeMillis() + ".png"));
                    } catch (IOException ex) {
                        LoggerUtil.error("Failed to save screenshot", ex);
                    }
                    LoggerUtil.error("Unable to click element: " + (element != null ? element.getText() : "null element") + " after " + maxAttempts + " retries.", e);
                    throw e;
                }
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
                    LoggerUtil.info("Element: " + element.getText().replaceAll("[\\n\\r]+", " ") + " is displayed.");
                }
                return displayed;
            } catch (TimeoutException e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    LoggerUtil.error("Element: " + element.getText() + " is not visible after " + maxAttempts + ".", e);
                    return false;
                }
            }
            LoggerUtil.info("Element: " + element.getText() + " is visible. Retrying (" + attempts + "/" + maxAttempts + ".");
        }
        return false;
    }
}
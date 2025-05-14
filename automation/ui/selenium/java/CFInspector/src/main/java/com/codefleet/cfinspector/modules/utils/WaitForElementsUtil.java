package com.codefleet.cfinspector.modules.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitForElementsUtil {

    private static final int DEFAULT_TIMEOUT = 10;

    public static void waitUntilElementToBeClickable(WebDriver driver, WebElement element)
    {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilElementToBeVisible(WebDriver driver, WebElement element)
    {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }
}

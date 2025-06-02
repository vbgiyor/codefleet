package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.utils.WaitForElementsUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ABTestPage extends BasePage {

    public ABTestPage(WebDriver driver) {
        super(driver);
    }


    public CFInspectorPage clickBackLink() {
        WebElement backLink = driver.findElement(locatorParser.getElementLocator("abtest.back_link"));
        /* In case of ElementClickIntercepted exception, replicate this logic evenly across project. Commented for time being.
            1. Scroll the element into view and adjust for any possible fixed elements like the navbar
            2. ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top - 100);", backLink);
            3. Add an explicit wait to ensure it's clickable
            4. WaitForElementsUtil.waitUntilElementToBeClickable(driver, backLink);
        */
        click(backLink);
        return new CFInspectorPage(driver);
    }

    public String getVersionAHeadlineText() {
        WebElement versionAHeadline = driver.findElement(locatorParser.getElementLocator("abtest.version_a_headline"));
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, versionAHeadline);
        return getText(versionAHeadline);
    }

    public String getVersionBHeadlineText() {
        WebElement versionBHeadline = driver.findElement(locatorParser.getElementLocator("abtest.version_b_headline"));
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, versionBHeadline);
        return getText(versionBHeadline);
    }

    public String getDescriptionText() {
        WebElement description = driver.findElement(locatorParser.getElementLocator("abtest.description"));
        return getText(description);
    }

    public boolean isVersionA() {
        List<WebElement> versionAElements = driver.findElements(locatorParser.getElementLocator("abtest.version_a_headline"));
        if (!versionAElements.isEmpty()) {
            WebElement versionAHeadline = versionAElements.get(0);
            try {
                WaitForElementsUtil.waitUntilElementToBeVisible(driver, versionAHeadline);
                return isElementDisplayed(versionAHeadline);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean isVersionB() {
        List<WebElement> versionBElements = driver.findElements(locatorParser.getElementLocator("abtest.version_b_headline"));
        if (!versionBElements.isEmpty()) {
            WebElement versionBHeadline = versionBElements.get(0);
            try {
                WaitForElementsUtil.waitUntilElementToBeVisible(driver, versionBHeadline);
                return isElementDisplayed(versionBHeadline);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
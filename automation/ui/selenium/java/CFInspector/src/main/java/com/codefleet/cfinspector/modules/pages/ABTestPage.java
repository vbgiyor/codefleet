package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.utils.WaitForElementsUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ABTestPage extends BasePage {

    public ABTestPage(WebDriver driver) {
        super(driver);
    }

    public AutomationPage clickBackLink() {
        WebElement backLink = driver.findElement(locatorParser.getElementLocator("abtest.back_link"));
        click(backLink);
        return new AutomationPage(driver);
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
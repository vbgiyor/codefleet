package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AutomationPage extends BasePage {

    public AutomationPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("automation.title"));
        return getText(title);
    }

    public ABTestPage clickABTestingLink() {
        WebElement abTestingLink = driver.findElement(locatorParser.getElementLocator("automation.ab_testing_link"));
        click(abTestingLink);
        return new ABTestPage(driver);
    }

    public boolean isAutomationPageLoaded() {
        WebElement abTestingLink = driver.findElement(locatorParser.getElementLocator("automation.ab_testing_link"));
        return isElementDisplayed(abTestingLink);
    }
}
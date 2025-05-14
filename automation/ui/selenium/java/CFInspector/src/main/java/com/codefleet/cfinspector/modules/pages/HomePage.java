package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("home.title"));
        return getText(title);
    }

    public String getDescriptionText() {
        WebElement description = driver.findElement(locatorParser.getElementLocator("home.description"));
        return getText(description);
    }

    public HomePage clickHomeLink() {
        WebElement homeLink = driver.findElement(locatorParser.getElementLocator("home.automation_link"));
        click(homeLink);
        return new HomePage(driver);
    }

    public AutomationPage clickAutomationLink() {
        WebElement automationLink = driver.findElement(locatorParser.getElementLocator("home.automation_link"));
        click(automationLink);
        return new AutomationPage(driver);
    }
}
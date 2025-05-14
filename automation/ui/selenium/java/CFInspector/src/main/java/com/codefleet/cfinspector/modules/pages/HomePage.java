package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getHomeTitleText() {
        WebElement description = driver.findElement(locatorParser.getElementLocator("home.title"));
        return getText(description);
    }

    public String getDescriptionText() {
        WebElement description = driver.findElement(locatorParser.getElementLocator("home.description"));
        return getText(description);
    }

    public AutomationPage clickAutomationLink() {
        // Click "Case Studies" to open dropdown
        WebElement caseStudiesButton = driver.findElement(locatorParser.getElementLocator("header.menu_case_studies"));
        click(caseStudiesButton);

        // Click "Automation" link in dropdown
        WebElement automationLink = driver.findElement(locatorParser.getElementLocator("header.case_studies_automation_link"));
        click(automationLink);
        return new AutomationPage(driver);
    }

    public ContactPage clickContactLink() {
        WebElement contactLink = driver.findElement(locatorParser.getElementLocator("header.menu_contact"));
        click(contactLink);
        return new ContactPage(driver);
    }
}
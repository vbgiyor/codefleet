package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaseStudiesPage extends BasePage {

    public CaseStudiesPage(WebDriver driver) {
        super(driver);
    }

    public AutomationPage clickAutomationLink() {
        WebElement automationLink = driver.findElement(locatorParser.getElementLocator("header.case_studies_automation_link"));
        click(automationLink);
        return new AutomationPage(driver);
    }
}
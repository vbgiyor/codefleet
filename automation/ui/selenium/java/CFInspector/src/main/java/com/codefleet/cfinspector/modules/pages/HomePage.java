package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.config.ConfigManager;
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

    public HomePage clickHomeButton() {
        WebElement homeButton = driver.findElement(locatorParser.getElementLocator("header.menu_home"));
        click(homeButton);
        return new HomePage(driver);
    }

    public ContactPage clickContactLink() {
        WebElement contactLink = driver.findElement(locatorParser.getElementLocator("header.menu_contact"));
        click(contactLink);
        return new ContactPage(driver);
    }

    public void clickCaseStudiesButton() {
        WebElement caseStudiesButton = driver.findElement(locatorParser.getElementLocator("header.menu_case_studies"));
        click(caseStudiesButton);
    }

    public boolean displayCaseStudiesDropdown() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.case_studies_dropdown"));
        return isElementDisplayed(dropdown);
    }

    public boolean displayJavaLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.case_studies_java_link"));
        return isElementDisplayed(dropdown);
    }

    public boolean displayPythonLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.case_studies_python_link"));
        return isElementDisplayed(dropdown);
    }

    public boolean displayAutomationLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.case_studies_automation_link"));
        return isElementDisplayed(dropdown);
    }

    public JavaPage clickJavaLink() {
        WebElement javaLink = driver.findElement(locatorParser.getElementLocator("header.case_studies_java_link"));
        click(javaLink);
        return new JavaPage(driver);
    }

    public PythonPage clickPythonLink() {
        WebElement pythonLink = driver.findElement(locatorParser.getElementLocator("header.case_studies_python_link"));
        click(pythonLink);
        return new PythonPage(driver);
    }

    public AutomationPage clickAutomationLink() {
        WebElement automationLink = driver.findElement(locatorParser.getElementLocator("header.case_studies_automation_link"));
        click(automationLink);
        return new AutomationPage(driver);
    }
}
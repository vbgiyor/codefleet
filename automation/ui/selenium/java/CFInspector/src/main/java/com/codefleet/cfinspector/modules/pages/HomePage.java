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

    public void clickResourcesButton() {
        WebElement resourcesButton = driver.findElement(locatorParser.getElementLocator("header.menu_resources"));
        click(resourcesButton);
    }

    public boolean displayResourcesDropdown() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.resources_dropdown"));
        return isElementDisplayed(dropdown);
    }

    public boolean displayJavaLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.resources_java_link"));
        return isElementDisplayed(dropdown);
    }

    public boolean displayPythonLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.resources_python_link"));
        return isElementDisplayed(dropdown);
    }

    public boolean displaySeleniumLink() {
        WebElement dropdown = driver.findElement(locatorParser.getElementLocator("header.resources_selenium_link"));
        return isElementDisplayed(dropdown);
    }

    public JavaPage clickJavaLink() {
        WebElement javaLink = driver.findElement(locatorParser.getElementLocator("header.resources_java_link"));
        click(javaLink);
        return new JavaPage(driver);
    }

    public PythonPage clickPythonLink() {
        WebElement pythonLink = driver.findElement(locatorParser.getElementLocator("header.resources_python_link"));
        click(pythonLink);
        return new PythonPage(driver);
    }

    public SeleniumPage clickSeleniumLink() {
        WebElement seleniumLink = driver.findElement(locatorParser.getElementLocator("header.resources_selenium_link"));
        click(seleniumLink);
        return new SeleniumPage(driver);
    }
}
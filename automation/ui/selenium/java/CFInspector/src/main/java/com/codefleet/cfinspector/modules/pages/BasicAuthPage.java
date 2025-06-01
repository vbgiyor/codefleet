package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasicAuthPage extends BasePage {

    public BasicAuthPage(WebDriver driver) {
        super(driver);
    }

    public CFInspectorPage clickBackLink() {
        WebElement backLink = driver.findElement(locatorParser.getElementLocator("basicauth.back_link"));
        click(backLink);
        return new CFInspectorPage(driver);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("basicauth.title"));
        return getText(title);
    }

    public String getSuccessMessageText() {
        WebElement successMessage = driver.findElement(locatorParser.getElementLocator("basicauth.success_message"));
        return getText(successMessage);
    }

    public String getInstructionsText() {
        WebElement instructions = driver.findElement(locatorParser.getElementLocator("basicauth.instructions"));
        return getText(instructions);
    }

    public void clickHerokuLink() {
        WebElement herokuLink = driver.findElement(locatorParser.getElementLocator("basicauth.heroku_link"));
        click(herokuLink);
    }
}
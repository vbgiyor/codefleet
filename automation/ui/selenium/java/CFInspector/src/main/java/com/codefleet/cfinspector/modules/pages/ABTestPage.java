package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ABTestPage extends BasePage {

    public ABTestPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadlineText() {
        WebElement headline = driver.findElement(locatorParser.getElementLocator("abtest.headline"));
        return getText(headline);
    }

    public String getParagraphText() {
        WebElement paragraph = driver.findElement(locatorParser.getElementLocator("abtest.paragraph"));
        return getText(paragraph);
    }

    public AutomationPage navigateBackToAutomationPage() {
        driver.navigate().back();
        return new AutomationPage(driver);
    }
}
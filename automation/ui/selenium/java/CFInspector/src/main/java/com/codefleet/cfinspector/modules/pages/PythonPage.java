package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PythonPage extends BasePage{

    public PythonPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPythonPageLoaded() {
        WebElement pythonPageHeading = driver.findElement(locatorParser.getElementLocator("python.heading"));
        return isElementDisplayed(pythonPageHeading);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("python.heading"));
        return getText(title);
    }
}

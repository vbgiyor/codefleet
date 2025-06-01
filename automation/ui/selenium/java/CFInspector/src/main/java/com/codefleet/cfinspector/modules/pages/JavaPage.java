package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaPage extends BasePage{
    public JavaPage(WebDriver driver)
    {
        super(driver);
    }

    public boolean isJavaPageLoaded() {
        WebElement javaPageHeading = driver.findElement(locatorParser.getElementLocator("java.title"));
        return isElementDisplayed(javaPageHeading);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("java.title"));
        return getText(title);
    }

}

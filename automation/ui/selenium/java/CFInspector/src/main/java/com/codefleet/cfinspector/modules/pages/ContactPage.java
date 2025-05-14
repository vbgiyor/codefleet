package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public boolean isContactLinkDisplayed() {
        WebElement contactLink = driver.findElement(locatorParser.getElementLocator("header.menu_contact"));
        return isElementDisplayed(contactLink);
    }

    public void clickContactLink() {
        WebElement contactLink = driver.findElement(locatorParser.getElementLocator("header.menu_contact"));
        click(contactLink);
    }
}
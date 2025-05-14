package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddRemoveElementsPage extends BasePage {

    public AddRemoveElementsPage(WebDriver driver) {
        super(driver);
    }

    public AutomationPage clickBackLink() {
        WebElement backLink = driver.findElement(locatorParser.getElementLocator("addremove.back_link"));
        click(backLink);
        return new AutomationPage(driver);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("addremove.title"));
        return getText(title);
    }

    public void insertInputFieldText(String text) {
        WebElement inputField = driver.findElement(locatorParser.getElementLocator("addremove.input_field"));
        insetText(inputField, text);
    }

    public void clickAddButton() {
        WebElement addButton = driver.findElement(locatorParser.getElementLocator("addremove.add_button"));
        click(addButton);
    }

    public void clickRemoveButton() {
        WebElement removeButton = driver.findElement(locatorParser.getElementLocator("addremove.remove_button"));
        click(removeButton);
    }

    public boolean isListDisplayed() {
        WebElement list = driver.findElement(locatorParser.getElementLocator("addremove.list"));
        return isElementDisplayed(list);
    }

    public String getListItemsText() {
        WebElement listItems = driver.findElement(locatorParser.getElementLocator("addremove.list_items"));
        return getText(listItems);
    }

    public String getEmptyMessageText() {
        WebElement emptyMessage = driver.findElement(locatorParser.getElementLocator("addremove.empty_message"));
        return getText(emptyMessage);
    }

    public String getLimitMessageText() {
        WebElement limitMessage = driver.findElement(locatorParser.getElementLocator("addremove.limit_message"));
        return getText(limitMessage);
    }
}
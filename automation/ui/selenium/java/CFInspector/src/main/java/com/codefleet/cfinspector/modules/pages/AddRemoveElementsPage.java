package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveElementsPage extends BasePage {

    public AddRemoveElementsPage(WebDriver driver) {
        super(driver);
    }

    public CFInspectorPage clickBackLink() {
        WebElement backLink = driver.findElement(locatorParser.getElementLocator("addremove.back_link"));
        click(backLink);
        return new CFInspectorPage(driver);
    }

    public WebElement getHeadingText() {
        return driver.findElement(locatorParser.getElementLocator("addremove.title"));
    }

    public WebElement insertTextField()
    {
        return driver.findElement(locatorParser.getElementLocator("addremove.input_field"));
    }

    public void insertInputFieldText(String text) {
        insetText(insertTextField(), text);
    }

    public WebElement displayAddButton() {
        return driver.findElement(locatorParser.getElementLocator("addremove.add_button"));
    }

    public void clickAddButton() {
        displayAddButton().click();
    }

    public WebElement displayRemoveButton() {
        return driver.findElement(locatorParser.getElementLocator("addremove.remove_button"));
    }

    public void clickRemoveButton() {
        displayRemoveButton().click();
    }

    public boolean isListDisplayed() {
        WebElement list = driver.findElement(locatorParser.getElementLocator("addremove.list"));
        return isElementDisplayed(list);
    }

    public List<String> getListItemsText() {
        List<WebElement> listItems = driver.findElements(locatorParser.getElementLocator("addremove.list_items"));

        List<String> listItem = new ArrayList<>();
        for(WebElement element : listItems)
        {
            listItem.add(element.getText());
        }
        return listItem;
    }

    public WebElement getEmptyMessageText() {
        return driver.findElement(locatorParser.getElementLocator("addremove.empty_message"));
    }

    public WebElement getLimitMessageText() {
        return driver.findElement(locatorParser.getElementLocator("addremove.limit_message"));
    }
}
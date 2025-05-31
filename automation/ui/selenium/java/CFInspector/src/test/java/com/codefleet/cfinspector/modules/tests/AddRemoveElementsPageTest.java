package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.AddRemoveElementsPage;
import com.codefleet.cfinspector.modules.pages.AutomationPage;
import com.codefleet.cfinspector.modules.pages.PageNavigationUtility;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AddRemoveElementsPageTest extends BasePageTest {
    private AddRemoveElementsPage addRemoveElementsPage;

    @BeforeMethod
    public void navigateToAddRemoveElementsPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        LoggerUtil.info("Navigating to Add Remove Elements Page");
        addRemoveElementsPage = PageNavigationUtility.navigatToAddRemoveElementsPage(driver);
    }

    @Test
    public void testAddRemovePageLoadsSuccessfully() {
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium/addremoveelements";
        String actualUrl = addRemoveElementsPage.getCurrentUrl();
        LoggerUtil.info("Current URL: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Add Remove Elements page did not load successfully");
    }

    @Test
    public void testAddElementButtonPresentAndClickable() {
        LoggerUtil.info("Checking if Add button is displayed");
        Assert.assertTrue(addRemoveElementsPage.displayAddButton().isDisplayed(),
                "Add button is not displayed");
        LoggerUtil.info("Clicking Add button");
        addRemoveElementsPage.clickAddButton();
    }

    @Test
    public void testRemoveElementButtonPresentAndClickable() {
        LoggerUtil.info("Inserting text to add element");
        addRemoveElementsPage.insertInputFieldText("testRemoveElementButtonPresentAndClickable");
        LoggerUtil.info("Clicking Add button to add element");
        addRemoveElementsPage.clickAddButton();
        LoggerUtil.info("Element added successfully.");
        LoggerUtil.info("Displaying Remove button");
        addRemoveElementsPage.displayRemoveButton();
        LoggerUtil.info("Clicking Remove button");
        addRemoveElementsPage.clickRemoveButton();
        LoggerUtil.info("Element removed successfully.");
    }

    @Test
    public void testMaxFiveElementsCanBeAdded() {
        String inputText = "Test";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        int elementCount = addRemoveElementsPage.getListItemsText().size();
        LoggerUtil.info("Number of elements after addition: " + elementCount);
        Assert.assertEquals(elementCount, 5, "More than 5 elements were added");
    }

    @Test
    public void testAddButtonDisabledAfterFifthElement() {
        String inputText = "Element ";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        LoggerUtil.info("Checking if Add button is disabled after 5 elements");
        if(addRemoveElementsPage.displayAddButton().isDisplayed())
            Assert.assertFalse(addRemoveElementsPage.displayAddButton().isEnabled(),
                    "Add button is not disabled after adding 5 elements");
    }

    @Test
    public void testMaxLimitMessageDisplayed() {
        String inputText = "Test";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element to reach limit: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        LoggerUtil.info("Checking limit message");
        String limitMessage = addRemoveElementsPage.getLimitMessageText().getText();
        Assert.assertEquals(limitMessage, "Maximum limit of 5 elements reached.",
                "Maximum limit message is not displayed correctly");
    }

    @Test
    public void testInputAndAddButtonDisabledAfterMaxElements() {
        String inputText = "Test";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        LoggerUtil.info("Verifying input and add button status after reaching limit");
        Assert.assertFalse(addRemoveElementsPage.insertTextField().isEnabled(),
                "Input field is not disabled after adding 5 elements");
        Assert.assertFalse(addRemoveElementsPage.displayAddButton().isEnabled(),
                "Add button is not disabled after adding 5 elements");
    }

    @Test
    public void testRemoveButtonRemovesLastElement() {
        String inputText = "Test";
        LoggerUtil.info("Adding element: " + inputText);
        addRemoveElementsPage.insertInputFieldText(inputText);
        addRemoveElementsPage.clickAddButton();

        LoggerUtil.info("Clicking Remove button to remove the last element");
        addRemoveElementsPage.clickRemoveButton();
        List<String> listTextAfter = addRemoveElementsPage.getListItemsText();
        LoggerUtil.info("List after removal: " + listTextAfter);

        Assert.assertFalse(listTextAfter.contains("New Element: " + inputText),
                "Last element was not removed");
    }

    @Test
    public void testNoElementsMessageAfterRemovingAll() {
        String inputText = "Test";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        LoggerUtil.info("Removing all elements");
        for (int i = 0; i < 5; i++) {
            addRemoveElementsPage.clickRemoveButton();
        }
        LoggerUtil.info("Checking no elements message");
        String emptyMessage = addRemoveElementsPage.getEmptyMessageText().getText();
        Assert.assertEquals(emptyMessage, "No elements to remove.",
                "No elements message is not displayed correctly");
    }

    @Test
    public void testInputEnabledAfterRemovingAllElements() {
        String inputText = "Test";
        for (int i = 0; i < 2; i++) {
            LoggerUtil.info("Adding element: " + inputText + i);
            addRemoveElementsPage.insertInputFieldText(inputText + i);
            addRemoveElementsPage.clickAddButton();
        }
        LoggerUtil.info("Removing all elements");
        for (int i = 0; i < 5; i++) {
            addRemoveElementsPage.clickRemoveButton();
        }
        LoggerUtil.info("Checking input field status after removal of all elements");
        Assert.assertTrue(addRemoveElementsPage.insertTextField().isEnabled(),
                "Input field is not enabled after removing all elements");
    }

    @Test
    public void testNavigationBackToAutomationPage() {
        LoggerUtil.info("Navigating back to Automation page");
        AutomationPage returnedPage = addRemoveElementsPage.clickBackLink();
        Assert.assertTrue(returnedPage.isAutomationPageLoaded(),
                "Navigation back to Automation page failed; A/B Testing link is not displayed.");
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium";
        String actualUrl = returnedPage.getCurrentUrl();
        LoggerUtil.info("Expected URL: " + expectedUrl);
        LoggerUtil.info("Actual URL: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Did not navigate back to Automation page URL.");
    }
}
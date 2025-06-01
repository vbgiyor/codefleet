package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BasePageTest {
    private HomePage homePage;

    @BeforeMethod
    public void navigateToHomePage()
    {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        homePage = PageNavigationUtility.navigateToHomePage(driver);
    }
    @Test
    public void testHomePageLoadsSuccessfully() {

        String expectedUrl = ConfigManager.getBaseUrl() + "/";
        String actualUrl = homePage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Home page did not load successfully");

        String expectedTitle = "Welcome to Codefleet";
        String actualTitle = homePage.getHomeTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Home page title is incorrect");

        String expectedDescription = "Your one-stop platform for learning Java, Python and Automation.";
        String actualDescription = homePage.getDescriptionText();
        Assert.assertEquals(actualDescription, expectedDescription, "Home page description is incorrect");
    }

    @Test
    public void testNavigationToResourcesWorks() {
        homePage.clickResourcesButton();
        boolean dropdown = homePage.displayResourcesDropdown();
        Assert.assertTrue(dropdown, "Resources dropdown is not displayed.");
        Assert.assertTrue(homePage.displayResourcesDropdown(), "Resources dropdown is not displayed.");
        Assert.assertTrue(homePage.displayJavaLink(), "Java redirection displayed in Resources dropdown.");
        Assert.assertTrue(homePage.displayPythonLink(), "Python redirection displayed in Resources dropdown.");
        Assert.assertTrue(homePage.displaySeleniumLink(), "Selenium redirection displayed in Resources dropdown.");
    }

    @Test
    public void testNavigationToJavaPageWorks()
    {
        homePage.clickResourcesButton();
        Assert.assertTrue(homePage.displayResourcesDropdown(), "Resources dropdown is not displayed.");
        JavaPage javaPage = homePage.clickJavaLink();
        String expected_url = ConfigManager.getBaseUrl() + "/resources/java";
        String actualUrl = javaPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expected_url, "Navigation to Java page failed.");
        Assert.assertTrue(javaPage.isJavaPageLoaded(), "Java page didn't load properly.");
        String expectedHeading = "Java Resource Library";
        String actualHeading = javaPage.getTitleText();
        Assert.assertEquals(actualHeading, expectedHeading, "Java page heading displayed incorrectly.");
    }

    @Test
    public void testNavigationToPythonPageWorks()
    {
        homePage.clickResourcesButton();
        Assert.assertTrue(homePage.displayResourcesDropdown(), "Resources dropdown is not displayed.");
        PythonPage pythonPage = homePage.clickPythonLink();
        String expected_url = ConfigManager.getBaseUrl() + "/resources/python";
        String actualUrl = pythonPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expected_url, "Navigation to Python page failed.");
        Assert.assertTrue(pythonPage.isPythonPageLoaded(), "Python page didn't load properly.");
        String expectedHeading = "Python Resource Library";
        String actualHeading = pythonPage.getTitleText();
        Assert.assertEquals(actualHeading, expectedHeading, "Python page heading displayed incorrectly.");
    }
}
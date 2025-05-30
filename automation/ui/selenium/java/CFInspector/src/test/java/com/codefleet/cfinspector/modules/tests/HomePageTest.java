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
    public void testNavigationToCaseStudiesWorks() {
        homePage.clickCaseStudiesButton();
        boolean dropdown = homePage.displayCaseStudiesDropdown();
        Assert.assertTrue(dropdown, "Case Studies dropdown is not displayed.");
        Assert.assertTrue(homePage.displayCaseStudiesDropdown(), "Case Studies dropdown is not displayed.");
        Assert.assertTrue(homePage.displayJavaLink(), "Java redirection displayed in Case Studies dropdown.");
        Assert.assertTrue(homePage.displayPythonLink(), "Python redirection displayed in Case Studies dropdown.");
        Assert.assertTrue(homePage.displayAutomationLink(), "Automation redirection displayed in Case Studies dropdown.");
    }

    @Test
    public void testNavigationToAutomationPageWorks() {
        homePage.clickCaseStudiesButton();
        AutomationPage automationPage = homePage.clickAutomationLink();
        String expectedUrl = ConfigManager.getBaseUrl() + "/case-studies/automation";
        String actualUrl = automationPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation to Automation page failed");
        Assert.assertTrue(automationPage.isAutomationPageLoaded(), "Automation page did not load properly");
        String expectedTitle = "Automation Testing";
        String actualTitle = automationPage.getTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Automation page title is incorrect");
    }

    @Test
    public void testNavigationToJavaPageWorks()
    {
        homePage.clickCaseStudiesButton();
        Assert.assertTrue(homePage.displayCaseStudiesDropdown(), "Case Studies dropdown is not displayed.");
        JavaPage javaPage = homePage.clickJavaLink();
        String expected_url = ConfigManager.getBaseUrl() + "/case-studies/java";
        String actualUrl = javaPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expected_url, "Navigation to Java page failed.");
        Assert.assertTrue(javaPage.isJavaPageLoaded(), "Java page didn't load properly.");
        String expectedHeading = "Java Case Study";
        String actualHeading = javaPage.getTitleText();
        Assert.assertEquals(actualHeading, expectedHeading, "Java page heading displayed incorrectly.");
    }

    @Test
    public void testNavigationToPythonPageWorks()
    {
        homePage.clickCaseStudiesButton();
        Assert.assertTrue(homePage.displayCaseStudiesDropdown(), "Case Studies dropdown is not displayed.");
        PythonPage pythonPage = homePage.clickPythonLink();
        String expected_url = ConfigManager.getBaseUrl() + "/case-studies/python";
        String actualUrl = pythonPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expected_url, "Navigation to Python page failed.");
        Assert.assertTrue(pythonPage.isPythonPageLoaded(), "Python page didn't load properly.");
        String expectedHeading = "Python Resource Library";
        String actualHeading = pythonPage.getTitleText();
        Assert.assertEquals(actualHeading, expectedHeading, "Python page heading displayed incorrectly.");
    }
}
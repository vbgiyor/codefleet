package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.*;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import com.codefleet.cfinspector.modules.utils.PageNavigationUtility;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class CFInspectorTest extends BasePageTest {

    private CFInspectorPage cfInspectorPage;

    @BeforeMethod
    public void navigateToCFInspectorPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        cfInspectorPage = PageNavigationUtility.navigateToCFInspectorPage(driver);
    }

    @Test
    public void testAutomationContextText() {
        String expectedDescription = "This collection of resources is designed to help you practice automation testing";
        String actualDescription = cfInspectorPage.getInternetDescriptionText();
        Assert.assertTrue(actualDescription.contains(expectedDescription), "Internet description text does not match");
    }

    @Test
    public void testNavigationToABTestingPageWorks() {
        ABTestPage abTestPage = cfInspectorPage.clickABTestingLink();
        assertNotNull("Add/Remove Elements Page is not loaded", abTestPage);
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium/cfinspector/abtest";
        Assert.assertEquals(cfInspectorPage.getCurrentUrl(), expectedUrl, "A/B Testing page did not load successfully");
    }

    @Test
    public void testIsCFInspectorPageLoaded() {
        // Verify that the CF Inspector page is loaded by checking for the project title
        assertTrue("CFInspector page is not loaded", cfInspectorPage.isCFInspectorPageLoaded());
    }

    @Test
    public void testGetProjectTitleText() {
        // Verify that the project title matches the expected title
        String projectTitle = cfInspectorPage.getProjectTitleText();
        assertEquals("Project: CFInspector", projectTitle);
    }

    @Test
    public void testGetInternetDescriptionText() {
        // Verify that the internet description is displayed correctly
        String internetDescription = cfInspectorPage.getInternetDescriptionText();
        assertTrue("Internet description is not correct", internetDescription.contains("This collection of resources is designed to help you practice automation testing"));
    }

    @Test
    public void testDisplayABTestingLink() {
        // Verify that the A/B Testing link is displayed
        assertTrue("A/B Testing link is not displayed", cfInspectorPage.displayABTestingLink());
    }

    @Test
    public void testDisplayAddRemoveLink() {
        // Verify that the Add/Remove Elements link is displayed
        assertTrue("Add/Remove Elements link is not displayed", cfInspectorPage.displayAddRemoveLink());
    }

    @Test
    public void testClickAddRemoveLink() {
        // Verify that clicking the Add/Remove Elements link navigates to the correct page
        AddRemoveElementsPage addRemoveElementsPage = cfInspectorPage.clickAddRemoveLink();
        assertNotNull("Add/Remove Elements Page is not loaded", addRemoveElementsPage);
    }

    @Test
    public void testBackLinkNavigation() {
        assertTrue("Returned page is not Selenium Projects page",cfInspectorPage.clickBackLinkToSeleniumProjects());
        assertNotNull("Back navigation failed", cfInspectorPage);
    }

    @Test
    public void testClickBasicAuthLink() {
        /*
            Key Objectives:
            1. Create a new ChromeDriver instance specifically for this test.
            2. Pass credentials using ChromeOptions to bypass the authentication prompt (for successful login).
            3. Validate invalid credentials by handling the Basic Auth pop-up in new test
            4. Close the WebDriver instance after the test execution.
        */
        String username = "admin";
        String password = "admin";
        String hostUrl = ConfigManager.getBaseUrl().split("//")[1];
        String expectedUrl = "http://" + username + ":" + password + "@" + hostUrl + "/resources/selenium/cfinspector/basicauth";

        WebDriver basicAuthDriver  = WebDriverFactory.getDriverThreadLocal();
        LoggerUtil.info("Basic Auth specific WebDriver instance initialized.");

        basicAuthDriver.get(expectedUrl);
        String actualUrl = basicAuthDriver.getCurrentUrl();
        LoggerUtil.info("Current URL in testClickBasicAuthLink: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation after clicking Basic Auth link failed");
        WebDriverFactory.quitDriver();
        LoggerUtil.info("Basic Auth specific WebDriver instance closed.");
    }

    @Test
    public void testBasicAuthWithInvalidCredentials() {
        String username = "$%^&";
        String password = "$%^&*";
        String hostUrl = ConfigManager.getBaseUrl().split("//")[1];
        String expectedUrl = "http://" + username + ":" + password + "@" +
                hostUrl + "/resources/selenium/cfinspector/basicauth";

        WebDriver basicAuthDriver  = WebDriverFactory.getDriverThreadLocal();
        LoggerUtil.info("Basic Auth specific WebDriver instance initialized.");

        basicAuthDriver.get(expectedUrl);

        try {
            /*If getPageSource() is null, the code skips the .contains() method and prevents the NullPointerException.*/
            String pageSource = basicAuthDriver.getPageSource();
            boolean errorMessageDisplayed = pageSource != null &&
                    (pageSource.contains("Invalid credentials")
                            || pageSource.contains("Unauthorized")
                            || pageSource.contains("Please authenticate using Basic Auth"));

            Assert.assertTrue(errorMessageDisplayed, "Expected error message not found on the page.");
        } catch (Exception e) {
            LoggerUtil.error("Error while checking the error message on the page", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        } finally {
            WebDriverFactory.quitDriver();
            LoggerUtil.info("Basic Auth specific WebDriver instance closed.");
        }
    }

}

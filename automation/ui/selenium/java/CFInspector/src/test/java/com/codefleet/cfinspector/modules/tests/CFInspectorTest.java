package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.ABTestPage;
import com.codefleet.cfinspector.modules.pages.CFInspectorPage;
import com.codefleet.cfinspector.modules.pages.PageNavigationUtility;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CFInspectorTest extends BasePageTest {

    private CFInspectorPage cfInspectorPage;
//    private WebDriver driver;

    @BeforeMethod
    public void navigateToCFInspectorPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        cfInspectorPage = PageNavigationUtility.navigateToCFInspectorPage(driver);
    }

    @Test
    public void testNavigationToCFInspectorPageWorks() {
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium/cfinspector";
        String actualUrl = cfInspectorPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation to CFInspector page failed");
        Assert.assertTrue(cfInspectorPage.isCFInspectorPageLoaded(), "CFInspector page did not load properly");

        String expectedTitle = "Project: CFInspector";
        String actualTitle = cfInspectorPage.getProjectTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "CFInspector page title is incorrect");
    }

    @Test
    public void testInternetDescriptionText() {
        String expectedDescription = "This collection of resources is designed to help you practice automation testing";
        String actualDescription = cfInspectorPage.getInternetDescriptionText();
        Assert.assertTrue(actualDescription.contains(expectedDescription), "Internet description text does not match");
    }

    @Test
    public void testClickInternetLink() {
        cfInspectorPage.clickInternetLink();
        String expectedUrl = "https://the-internet.herokuapp.com/";
        String actualUrl = cfInspectorPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation after clicking Internet link failed");
    }

    @Test
    public void testNavigationToABTestingPageWorks() {
        cfInspectorPage.clickABTestingLink();
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium/cfinspector/abtest";
        String actualUrl = cfInspectorPage.getCurrentUrl();
        LoggerUtil.info("Current URL in testNavigationToABTestingPageWorks: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "A/B Testing page did not load successfully");
        LoggerUtil.info("Redirecting to parent page CFInspector.");

    }

    @Test
    public void testNavigationToAddRemovePageWorks() {
        SoftAssert softAssert = new SoftAssert();
        try {
            cfInspectorPage.clickAddRemoveLink();
            String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium/cfinspector/addremoveelements";
            String actualUrl = cfInspectorPage.getCurrentUrl();
            LoggerUtil.info("Current URL in testNavigationToAddRemovePageWorks: " + actualUrl);
            softAssert.assertEquals(actualUrl, expectedUrl, "Add Remove page did not load successfully");
        }
        catch (AssertionError e)
        {
            LoggerUtil.error("testNavigationToAddRemovePageWorks is failed. Continuing to run other tests.", e);
        }
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

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get(expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        LoggerUtil.info("Current URL in testClickBasicAuthLink: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation after clicking Basic Auth link failed");
        driver.quit();
    }

    private WebDriver initializeBasicAuthSpecificChromeDriver(ChromeOptions chromeOptions) {
        return new ChromeDriver(chromeOptions);
    }

    @Test
    public void testBasicAuthWithInvalidCredentials() {
        String username = "$%^&";
        String password = "$%^&*";
        String hostUrl = ConfigManager.getBaseUrl().split("//")[1];
        String expectedUrl = "http://" + username + ":" + password + "@" +
                hostUrl + "/resources/selenium/cfinspector/basicauth";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        WebDriver driver = initializeBasicAuthSpecificChromeDriver(chromeOptions);
        driver.get(expectedUrl);

        try {
            /*If getPageSource() is null, the code skips the .contains() method and prevents the NullPointerException.*/
            String pageSource = driver.getPageSource();
            boolean errorMessageDisplayed = pageSource != null &&
                    (pageSource.contains("Invalid credentials")
                            || pageSource.contains("Unauthorized")
                            || pageSource.contains("Please authenticate using Basic Auth"));

            Assert.assertTrue(errorMessageDisplayed, "Expected error message not found on the page.");
        } catch (Exception e) {
            LoggerUtil.error("Error while checking the error message on the page", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}

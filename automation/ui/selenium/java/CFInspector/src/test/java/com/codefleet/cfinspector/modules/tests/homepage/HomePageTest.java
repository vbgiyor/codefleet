package com.codefleet.cfinspector.modules.tests.homepage;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.AutomationPage;
import com.codefleet.cfinspector.modules.pages.HomePage;
import com.codefleet.cfinspector.modules.tests.BasePageTest;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BasePageTest {

    @Test
    public void testHomePageLoadsSuccessfully() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        HomePage homePage = new HomePage(driver);

        String baseUrl = ConfigManager.getBaseUrl();
        homePage.navigateTo(baseUrl);

        String expectedUrl = baseUrl + "/";
        String actualUrl = homePage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Home page did not load successfully");

        String expectedTitle = "Welcome to Codefleet";
        String actualTitle = homePage.getTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Home page title is incorrect");

        String expectedDescription = "Your one-stop platform for learning Java, Python and Automation.";
        String actualDescription = homePage.getDescriptionText();
        Assert.assertEquals(actualDescription, expectedDescription, "Home page description is incorrect");
    }

    @Test(dependsOnMethods = "testHomePageLoadsSuccessfully")
    public void testNavigationToAutomationPageWorks() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        HomePage homePage = new HomePage(driver);

        String baseUrl = ConfigManager.getBaseUrl();
        homePage.navigateTo(baseUrl);
        AutomationPage automationPage = homePage.clickAutomationLink();

        String expectedUrl = baseUrl + "/automation";
        String actualUrl = automationPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Navigation to Automation page failed");

        Assert.assertTrue(automationPage.isAutomationPageLoaded(),
                "Automation page did not load properly after navigation");

        String expectedTitle = "Automation Testing";
        String actualTitle = automationPage.getTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Automation page title is incorrect");
    }
}
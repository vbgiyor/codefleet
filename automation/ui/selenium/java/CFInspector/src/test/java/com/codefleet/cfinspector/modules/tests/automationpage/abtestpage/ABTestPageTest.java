package com.codefleet.cfinspector.modules.tests.automationpage.abtestpage;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.ABTestPage;
import com.codefleet.cfinspector.modules.pages.AutomationPage;
import com.codefleet.cfinspector.modules.pages.HomePage;
import com.codefleet.cfinspector.modules.tests.BasePageTest;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ABTestPageTest extends BasePageTest {

    @Test
    public void testABTestPageLoadsFromAutomationPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        HomePage homePage = new HomePage(driver);
        AutomationPage automationPage;
        ABTestPage abTestPage ;

        String baseUrl = ConfigManager.getBaseUrl();
        homePage.navigateTo(baseUrl);
        automationPage = homePage.clickAutomationLink();
        abTestPage = automationPage.clickABTestingLink();

        String expectedUrl = baseUrl + "/case-studies/automation/abtest";
        String actualUrl = abTestPage.getCurrentUrl();
        LoggerUtil.info("Current URL in testABTestPageLoadsFromAutomationPage: " + abTestPage.getCurrentUrl());
        Assert.assertEquals(actualUrl, expectedUrl, "A/B Testing page did not load successfully from Automation page");
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testABTestPageHeadlineTextIsCorrect() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);

        LoggerUtil.info("Current URL in testABTestPageHeadlineTextIsCorrect: " + abTestPage.getCurrentUrl());

        if (abTestPage.isVersionA()) {
            String versionAHeadline = abTestPage.getVersionAHeadlineText();
            Assert.assertEquals(versionAHeadline, "Surprise!!!", "Version A headline text is incorrect. Found: " + versionAHeadline);
        } else if (abTestPage.isVersionB()) {
            String versionBHeadline = abTestPage.getVersionBHeadlineText();
            Assert.assertEquals(versionBHeadline, "Good News!!!", "Version B headline text is incorrect. Found: " + versionBHeadline);
        } else {
            Assert.fail("Neither Version A nor Version B headline is displayed.");
        }
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testABTestPageDescriptionTextIsCorrect() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);
        String descriptionText = abTestPage.getDescriptionText();
        Assert.assertTrue(descriptionText.contains("This is a simple A/B testing example"),
                "Description text does not contain 'This is a simple A/B testing example'. Found: " + descriptionText);
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testNavigationBackToAutomationPageWorks() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);
        AutomationPage returnedPage = abTestPage.clickBackLink();
        Assert.assertTrue(returnedPage.isAutomationPageLoaded(),
                "Navigation back to Automation page failed; A/B Testing link is not displayed.");
        String expectedUrl = ConfigManager.getBaseUrl() + "/case-studies/automation";
        String actualUrl = returnedPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Did not navigate back to Automation page URL.");
    }
}
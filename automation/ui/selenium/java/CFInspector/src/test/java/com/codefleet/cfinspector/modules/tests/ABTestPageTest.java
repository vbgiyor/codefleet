package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.*;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ABTestPageTest extends BasePageTest {

    private ABTestPage abTestPage;

    @BeforeMethod
    public void navigateToABTestPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        abTestPage = PageNavigationUtility.navigateToABTestPage(driver);
    }

    @Test
    public void testABTestPageLoads() {
        String expectedUrl = ConfigManager.getBaseUrl() + "/case-studies/automation/abtest";
        String actualUrl = abTestPage.getCurrentUrl();
        LoggerUtil.info("Current URL in testABTestPageLoads: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "A/B Testing page did not load successfully");
    }

    @Test
    public void testABTestPageHeadlineTextIsCorrect() {
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

    @Test
    public void testABTestPageDescriptionTextIsCorrect() {
        String descriptionText = abTestPage.getDescriptionText();
        Assert.assertTrue(descriptionText.contains("This is a simple A/B testing example"),
                "Description text does not contain 'This is a simple A/B testing example'. Found: " + descriptionText);
    }

    @Test
    public void testNavigationBackToAutomationPageWorks() {
        AutomationPage returnedPage = abTestPage.clickBackLink();
        Assert.assertTrue(returnedPage.isAutomationPageLoaded(),
                "Navigation back to Automation page failed; A/B Testing link is not displayed.");
        String expectedUrl = ConfigManager.getBaseUrl() + "/case-studies/automation";
        String actualUrl = returnedPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Did not navigate back to Automation page URL.");
    }
}
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
        AutomationPage automationPage = new AutomationPage(driver);
        ABTestPage abTestPage = new ABTestPage(driver);

        String baseUrl = ConfigManager.getBaseUrl();
        homePage.navigateTo(baseUrl);
        automationPage = homePage.clickAutomationLink();
        abTestPage = automationPage.clickABTestingLink();

        String expectedUrl = baseUrl + "/abtest";
        String actualUrl = abTestPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "A/B Testing page did not load successfully from Automation page");
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testABTestPageHeadlineTextIsCorrect() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);
        String headlineText = abTestPage.getHeadlineText();
        LoggerUtil.info("Current URL in testABTestPageHeadlineTextIsCorrect: " + abTestPage.getCurrentUrl());
        boolean isHeadlineCorrect = headlineText.equals("A/B Test Variation 1") || headlineText.equals("A/B Test Control");
        Assert.assertTrue(isHeadlineCorrect, "Headline text is neither 'A/B Test Variation 1' nor 'A/B Test Control'. Found: " + headlineText);
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testABTestPageParagraphTextContainsSplitTesting() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);
        String paragraphText = abTestPage.getParagraphText();
        Assert.assertTrue(paragraphText.contains("Also known as split testing"),
                "Paragraph text does not contain 'Also known as split testing...'. Found: " + paragraphText);
    }

    @Test(dependsOnMethods = "testABTestPageLoadsFromAutomationPage")
    public void testNavigationBackToAutomationPageWorks() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        ABTestPage abTestPage = new ABTestPage(driver);
        AutomationPage returnedPage = abTestPage.navigateBackToAutomationPage();
        Assert.assertTrue(returnedPage.isAutomationPageLoaded(),
                "Navigation back to Automation page failed; A/B Testing link is not displayed.");
        String expectedUrl = ConfigManager.getBaseUrl() + "/automation";
        String actualUrl = returnedPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Did not navigate back to Automation page URL.");
    }
}
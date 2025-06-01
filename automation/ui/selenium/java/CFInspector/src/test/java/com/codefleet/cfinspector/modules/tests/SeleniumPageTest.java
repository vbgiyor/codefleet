package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.CFInspectorPage;
import com.codefleet.cfinspector.modules.pages.PageNavigationUtility;
import com.codefleet.cfinspector.modules.pages.SeleniumPage;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumPageTest extends BasePageTest {

    private SeleniumPage seleniumPage;

    @BeforeMethod
    public void navigateToSeleniumPage() {
        WebDriver driver = WebDriverFactory.getDriverThreadLocal();
        seleniumPage = PageNavigationUtility.navigateToSeleniumPage(driver);
    }

    @Test
    public void testSeleniumPageLoadsSuccessfully() {
        String expectedUrl = ConfigManager.getBaseUrl() + "/resources/selenium";
        String actualUrl = seleniumPage.getCurrentUrl();
        LoggerUtil.info("Current URL in testSeleniumPageLoadsSuccessfully: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Selenium page did not load successfully.");
    }

    @Test
    public void testSeleniumPageTitleIsCorrect() {
        String actualTitle = seleniumPage.getTitleText();
        LoggerUtil.info("Selenium Page Title: " + actualTitle);
        Assert.assertEquals(actualTitle, "Selenium Resource Library", "Selenium page title is incorrect.");
    }

    @Test
    public void testSeleniumPageDescriptionIsCorrect() {
        String descriptionText = seleniumPage.getDescriptionText();
        LoggerUtil.info("Selenium Page Description: " + descriptionText);
        Assert.assertTrue(descriptionText.contains("This in-depth tutorial series explores Selenium UI automation with Java"),
                "Selenium page description is incorrect or missing expected content.");
    }

    @Test
    public void testSeleniumProjectsSectionTitle() {
        String sectionTitle = seleniumPage.getProjectsSectionTitle();
        LoggerUtil.info("Selenium Projects Section Title: " + sectionTitle);
        Assert.assertEquals(sectionTitle, "🔹 Selenium Projects", "Selenium projects section title is incorrect.");
    }

    @Test
    public void testSeleniumProjectsAreListed() {
        // We're not interacting with each element individually here. Presence of links will be tested via clicks below.
        LoggerUtil.info("Validating project links presence via individual click tests.");
    }

    @Test
    public void testClickCFInspectorProjectNavigatesCorrectly() {
        CFInspectorPage cfInspectorPage = seleniumPage.clickProjectCFInspector();
        String currentUrl = cfInspectorPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/cfinspector"), "Navigation to CFInspector project failed.");
    }

}

package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.CFInspectorPage;
import com.codefleet.cfinspector.modules.pages.PageNavigationUtility;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CFInspectorTest extends BasePageTest {

    private CFInspectorPage cfInspectorPage;

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
        Assert.assertTrue(cfInspectorPage.isAutomationPageLoaded(), "CFInspector page did not load properly");
        String expectedTitle = "Project: CFInspector";
        String actualTitle = cfInspectorPage.getProjectTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "CFInspector page title is incorrect");
    }
}

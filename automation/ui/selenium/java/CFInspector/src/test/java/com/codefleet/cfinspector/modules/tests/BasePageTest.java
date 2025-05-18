package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import com.codefleet.cfinspector.modules.utils.ScreenShotUtil;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class BasePageTest {

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional String browser, ITestContext context) {
        System.clearProperty("browser");
        if (browser != null && !browser.isEmpty()) {
            System.setProperty("browser", browser);
            LoggerUtil.info("Setting browser property: " + browser);
        } else {
            LoggerUtil.info("No browser parameter provided, will use data.properties");
        }
        WebDriverFactory.getDriverThreadLocal();
        LoggerUtil.info("WebDriver initialized.");
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName();
            LoggerUtil.info("Attempting to store failure screenshot for " +
                    (testName != null ? testName : "null"));
            try {
                ScreenShotUtil.captureScreenshot(testName);
            } catch (IOException e) {
                LoggerUtil.error("Failed to take screenshot for the test " +
                        (testName != null ? testName : "null"), e);
            }
        }
    }

    @AfterClass
    public void tearDown(ITestContext context) {
        WebDriverFactory.quitDriver();
        LoggerUtil.info("WebDriver instance is closed.");
        System.clearProperty("browser");
        // Add logic to write test results to CSV using ITestContext in the future
        // Example: String suiteName = context.getSuite().getName();
    }
}
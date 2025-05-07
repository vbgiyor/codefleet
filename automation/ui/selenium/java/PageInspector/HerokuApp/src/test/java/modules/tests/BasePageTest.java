package modules.tests;

import modules.core.WebDriverFactory;
import modules.utils.LoggerUtil;
import modules.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BasePageTest {

    @BeforeMethod
    public void setUp()
    {
        WebDriverFactory.getDriver();
        LoggerUtil.info("WebDriver initialised.");
    }

    @AfterMethod
    public void tearDown(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName();
            LoggerUtil.info("Attempting to capture screenshot for test: " + (testName != null ? testName : "null"));
            try {
                ScreenshotUtil.captureScreenshot(result.getName());
            }catch (IOException e)
            {
                LoggerUtil.error("Failed to capture screenshot for test " + (testName != null ? testName : "unknown_test"), e);
            }
        }
        WebDriverFactory.quitDriver();
        LoggerUtil.info("WebDriver instance is closed.");
    }
}

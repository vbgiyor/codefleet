package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import com.codefleet.cfinspector.modules.utils.ScreenShotUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BasePageTest {
    @BeforeMethod
    public void setUp()
    {
        WebDriverFactory.getDriverThreadLocal();
        LoggerUtil.info("WebDriver initialised.");
    }

    @AfterMethod
    public void tearDown(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String testName = result.getName();
            LoggerUtil.info("Attempting to store failure screenshot for " +
                    (testName != null ? testName : "null"));
            try {
                ScreenShotUtil.captureScreenshot(testName);
            }catch (IOException e){
                LoggerUtil.error("Failed to take screenshot for the test " +
                        (testName != null ? testName : "null"),e);
            }
        }
        WebDriverFactory.quitDriver();
        LoggerUtil.info("WebDriver instance is closed.");
    }
}

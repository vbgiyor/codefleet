package com.codefleet.cfinspector.modules.utils;

import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotUtil {

    public static void captureScreenshot (String testName) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) WebDriverFactory.getDriverThreadLocal();

        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());

        String fallbackTestName = (testName == null) || testName.trim().isEmpty()
                ? "fallback_testName_" : testName.trim();

        String screenShotDestination = "target/screenshots/" + fallbackTestName + "_" + timestamp + ".png";

        File saveScrAt = new File(screenShotDestination);

        File directory = saveScrAt.getParentFile();

        if (!directory.exists() && !directory.mkdirs()) {
            String errorMessage = "Failed to create screenshots directory: " + directory.getAbsolutePath();
            IOException e = new IOException(errorMessage);
            LoggerUtil.error(errorMessage, e);
            throw e;
        }
        LoggerUtil.info("Screenshots directory: " + directory.getAbsolutePath());

        try {
            FileUtils.copyFile(src, new File(screenShotDestination));
            LoggerUtil.info("Screenshot saved sucessfully at: "+ screenShotDestination);
        }catch (IOException e)
        {
            LoggerUtil.error("Unable to save screenshot.", e);
        }
    }
}

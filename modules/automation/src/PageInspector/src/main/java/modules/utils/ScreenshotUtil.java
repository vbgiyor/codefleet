package modules.utils;

import modules.core.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void captureScreenshot(String testName) throws IOException
    {
        TakesScreenshot scr = (TakesScreenshot) WebDriverFactory.getDriver();
        File src = scr.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddss_HHmmss").format(new Date());
        // Use fallback name if testname is empty
        String fallbackName = (testName == null) || testName.trim().isEmpty() ? "unknown_test" : testName.trim();
        String scrDestination = "target/screenshots/" + fallbackName + "_" + timestamp + ".png";
        File destinationFile = new File(scrDestination);

        File directory = destinationFile.getParentFile();
        if (!directory.mkdirs() && !directory.exists()) {
            String errorMessage = "Failed to create screenshots directory: " + directory.getAbsolutePath();
            IOException e = new IOException(errorMessage);
            LoggerUtil.error(errorMessage, e);
            throw e;
        }
        LoggerUtil.info("Created screenshots directory: " + directory.getAbsolutePath());

        try {
            FileUtils.copyFile(src, new File(scrDestination));
            LoggerUtil.info("Screenshot saved successfully at "+scrDestination);
        } catch (IOException e)
        {
            LoggerUtil.error("Unable to save screenshot.", e);
        }
    }
}

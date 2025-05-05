package modules.config;

import modules.utils.LoggerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("webapp.properties"))
            {
                if(input == null)
                    throw new IOException("Unable to load Web App Properties file.");
                properties.load(input);
            }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load web app properties file.", e);
        }
    }

    public static String getBaseURL()
    {
        return properties.getProperty("base.url");
    }

    public static String getBrowser()
    {
        return properties.getProperty("browser");
    }

    public static int getImplicitWaitTimeout()
    {
        return Integer.parseInt(properties.getProperty("implicit.wait.seconds"));
    }

    public static int getPageTimeoutInSeconds()
    {
        return Integer.parseInt(properties.getProperty("page.load.timeout.seconds"));
    }

    public static int getRetryAttempts()
    {
        /*
        * Retry Logic:
        *Each method will attempt to perform the operation up to "retry.attempts + 1" times.
        * Means, 1 Retry = 2 attempts: 1 initial attempt + 1 retry attempt.
        */
        return Integer.parseInt(properties.getProperty("retry.attempts"));
    }

    public static boolean isHeadless() {
        String headless = properties.getProperty("headless");
        if (headless == null || headless.trim().isEmpty()) {
            LoggerUtil.info("Headless property not specified in config, defaulting to false");
            return false;
        }
        return Boolean.parseBoolean(headless.trim());
    }
}

package com.codefleet.cfinspector.modules.core;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverFactory {

    public static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriverThreadLocal()
    {
        if(driverThreadLocal.get() == null)
        {
            String browser = ConfigManager.getBrowser();
            boolean headless = ConfigManager.isHeadless();

            switch(browser.toLowerCase())
            {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions  chromeOptions = new ChromeOptions();
                    if(headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                    }
                    driverThreadLocal.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if(headless)
                        firefoxOptions.addArguments("--headless");
                    driverThreadLocal.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "safari":
                    WebDriverManager.safaridriver().setup();
                    SafariOptions safariOptions = new SafariOptions();
                    if(headless)
                        LoggerUtil.info("Safari browser doesn't support headless mode, reverting to non-headless mode.");
                    driverThreadLocal.set(new SafariDriver(safariOptions));
                    break;
            }

            driverThreadLocal.get().manage().window().maximize();
            driverThreadLocal.get().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(ConfigManager.getImplicitTime()));
            driverThreadLocal.get().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(ConfigManager.getPageLoadTimeOut()));
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver()
    {
        if(driverThreadLocal.get() != null)
        {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}

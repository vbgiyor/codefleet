package modules.core;

import modules.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver()
    {
        if(driver.get() == null)
        {
            String browser = ConfigReader.getBrowser();
            boolean headless = ConfigReader.isHeadless();
            LoggerUtil.info("Specified headless mode: " + headless);

            switch (browser.toLowerCase())
            {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if(headless)
                    {
                        chromeOptions.addArguments("--headless=new");
                    }
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if(headless)
                    {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    if (headless) {
                        LoggerUtil.info("Safari does not support headless mode; running in non-headless mode");
                    }
                    driver.set(new SafariDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported Browser: " + browser);
            }
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getImplicitWaitTimeout()));
            driver.get().manage().timeouts().pageLoadTimeout((java.time.Duration.ofSeconds(ConfigReader.getPageTimeoutInSeconds())));
        }
        return driver.get();
    }

    public static void quitDriver()
    {
        if(driver.get()!=null)
        {
            driver.get().quit();
            driver.remove();
        }
    }


}

package modules.pages;

import modules.config.ConfigReader;
import modules.core.CoreActions;
import modules.core.WebDriverFactory;
import modules.utils.LoggerUtil;
import modules.utils.ScreenshotUtil;
import modules.utils.WaitUtil;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage implements CoreActions {
    protected WebDriver driver;

    public BasePage()
    {
        this.driver = WebDriverFactory.getDriver();
    }

    @Override
    public void click(WebElement element)
    {
        int attempts = 0;
        int max_attempts = ConfigReader.getRetryAttempts();

        while(attempts < max_attempts)
        {
            try {
                WaitUtil.waitUntilElementToBeClickable(driver, element);
                element.click();
                LoggerUtil.info("Successful element click.");
                return;
            }
            catch (TimeoutException e)
            {
                attempts++;
                if(attempts > max_attempts)
                {
                    LoggerUtil.error("Failed to click element after "+ attempts + " retries.", e);
                    throw e;
                }
                LoggerUtil.info("Element is not clickable. Retrying (" + attempts + "/" + max_attempts +".");
            }

        }
    }


    @Override
    public void inputText(WebElement element, String input)
    {
        WaitUtil.waitUntilElementIsVisible(driver, element);
        element.clear();
        element.sendKeys(input);
    }

    @Override
    public String getText(WebElement element)
    {
        WaitUtil.waitUntilElementIsVisible(driver, element);
        return element.getText();
    }

    @Override
    public boolean isDisplayed(WebElement element) {
        int attempts = 0;
        int max_attempts = ConfigReader.getRetryAttempts();
        while (attempts < max_attempts) {
            try {
                WaitUtil.waitUntilElementIsVisible(driver, element);
                boolean displayed = element.isDisplayed();
                LoggerUtil.info("Element display successful. ");
                return displayed;
            } catch (TimeoutException e)
            {
                attempts++;
                if (attempts > max_attempts) {
                    LoggerUtil.info("Element is not visible after " + max_attempts + "retries.");
                    return false;
                }
            }
            LoggerUtil.info("Element is not visible. Retrying (" + attempts + "/" + max_attempts + ".");
        }
        return false; //This will always be unreachable. Added to avoid compilation error.
    }
}

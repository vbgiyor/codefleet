package modules.pages;

import modules.config.ConfigReader;
import modules.utils.LoggerUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public class HerokuappHomePage extends BasePage{

    @FindBy(className = "heading")
    private WebElement heading;

    public HerokuappHomePage()
    {
        PageFactory.initElements(driver, this);
    }

    public void navigateTo()
    {
        LoggerUtil.info("Navigating to "+ ConfigReader.getBaseURL());
        driver.get(ConfigReader.getBaseURL());
    }

    public boolean isHeadingDisplayed()
    {
        LoggerUtil.info("Checking if heading is displayed...");
        return isDisplayed(heading);
    }

    public String getHeadingText()
    {
        LoggerUtil.info("Reading Heading...");
        return getText(heading);
    }


}

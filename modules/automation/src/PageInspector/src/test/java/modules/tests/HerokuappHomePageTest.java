package modules.tests;

import modules.config.ConfigReader;
import modules.pages.HerokuappHomePage;
import modules.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HerokuappHomePageTest extends BasePageTest{

    @Test
    public void testSuccessfulHomepageNavigation()
    {
        LoggerUtil.info("Starting Herokuapp Homepage tests...");
        HerokuappHomePage  herokuappHomePage = new HerokuappHomePage();
        herokuappHomePage.navigateTo();
        LoggerUtil.info("Verifying Home Page elements...");
        Assert.assertTrue(herokuappHomePage.isHeadingDisplayed(), "Homepage heading should be displayed.");
        String headingText = herokuappHomePage.getHeadingText();
        Assert.assertEquals(headingText, "Welcome to the-internet", "Heading text should match expected value.");
        LoggerUtil.info("Herokuapp Homepage tests passed.");
    }
}

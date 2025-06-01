package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;

public class PageNavigationUtility {
    private static final String baseUrl = ConfigManager.getBaseUrl();

    public static HomePage navigateToHomePage(WebDriver driver) {
        HomePage homePage;
        try {
            homePage = new HomePage(driver);
            homePage.navigateTo(baseUrl);
            LoggerUtil.info("Navigating to Home page: " + driver.getCurrentUrl());
            homePage.clickResourcesButton();
            if(homePage.getCurrentUrl().equalsIgnoreCase(baseUrl + "/"))
                return homePage.clickHomeButton();
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to Home page", e);
            throw new RuntimeException("Navigating to Home page failed.");
        }
        return homePage;
    }

    public static SeleniumPage navigateToSeleniumPage(WebDriver driver) {
        SeleniumPage seleniumPage;
        try {
            HomePage homePage =  navigateToHomePage(driver);
            seleniumPage = new SeleniumPage(driver);
            LoggerUtil.info("Navigating to Selenium Testing page: " + seleniumPage.getCurrentUrl());
            homePage.clickResourcesButton();
            if (homePage.displayResourcesDropdown()) {
                return homePage.clickSeleniumLink();
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to Selenium page", e);
            throw new RuntimeException("Navigating to Selenium page failed.");
        }
        return seleniumPage;
    }


    public static CFInspectorPage navigateToCFInspectorPage(WebDriver driver) {
        CFInspectorPage cfInspectorPage;
        try {
            SeleniumPage seleniumPage = navigateToSeleniumPage(driver);
            cfInspectorPage = new CFInspectorPage(driver);
            if (seleniumPage.displayProjectCFInspector()) {
                seleniumPage.clickProjectCFInspector();
                LoggerUtil.info("Navigating to CFInspector Testing page: " + cfInspectorPage.getCurrentUrl());
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to CFInspector page", e);
            throw new RuntimeException("Navigating to CFInspector page failed.");
        }
        return cfInspectorPage;
    }


    public static ABTestPage navigateToABTestPage(WebDriver driver) {
        ABTestPage abTestPage;
        try {
            CFInspectorPage CFInspectorPage = navigateToCFInspectorPage(driver);
            abTestPage = new ABTestPage(driver);
            LoggerUtil.info("Navigating to A/B Testing page: " + abTestPage.getCurrentUrl());
            abTestPage = CFInspectorPage.clickABTestingLink();
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to A/B Testing page", e);
            throw new RuntimeException("Navigation to A/B Testing page failed.", e);
        }
        return abTestPage;
    }

    public static AddRemoveElementsPage navigatToAddRemoveElementsPage(WebDriver driver)
    {
        AddRemoveElementsPage addRemoveElementsPage;
        try{
            CFInspectorPage CFInspectorPage = navigateToCFInspectorPage(driver);
            addRemoveElementsPage = new AddRemoveElementsPage(driver);
            LoggerUtil.info("Navigating to Add Remove Elements page: " + addRemoveElementsPage.getCurrentUrl());
            addRemoveElementsPage = CFInspectorPage.clickAddRemoveLink();
        }catch (RuntimeException e){
            LoggerUtil.error("Failed to navigate to Add Remove Elements page.", e);
            throw new RuntimeException("Navigating to Add Remove Elements page failed.", e);
        }
        return addRemoveElementsPage;
    }
}

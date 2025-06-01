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
            homePage.clickResourcesButton();
            if(homePage.getCurrentUrl().equalsIgnoreCase(baseUrl + "/"))
                return homePage.clickHomeButton();
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to Home page", e);
            throw new RuntimeException("Navigating to Home page failed.");
        }
        LoggerUtil.info("Navigated to Home page: " + driver.getCurrentUrl());
        return homePage;
    }

    public static SeleniumPage navigateToSeleniumPage(WebDriver driver) {
        SeleniumPage seleniumPage;
        try {
            HomePage homePage =  navigateToHomePage(driver);
            seleniumPage = new SeleniumPage(driver);
            homePage.clickResourcesButton();
            if (homePage.displayResourcesDropdown()) {
                return homePage.clickSeleniumLink();
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to Selenium page", e);
            throw new RuntimeException("Navigating to Selenium page failed.");
        }
        LoggerUtil.info("Navigated to Selenium Testing page: " + seleniumPage.getCurrentUrl());
        return seleniumPage;
    }


    public static CFInspectorPage navigateToCFInspectorPage(WebDriver driver) {
        CFInspectorPage cfInspectorPage;
        try {
            SeleniumPage seleniumPage = navigateToSeleniumPage(driver);
            cfInspectorPage = new CFInspectorPage(driver);
            if (seleniumPage.displayProjectCFInspector()) {
                return seleniumPage.clickProjectCFInspector();
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to CFInspector page", e);
            throw new RuntimeException("Navigating to CFInspector page failed.");
        }
        LoggerUtil.info("Navigated to CFInspector Testing page: " + cfInspectorPage.getCurrentUrl());
        return cfInspectorPage;
    }

    public static ABTestPage navigateToABTestPage(WebDriver driver) {
        ABTestPage abTestPage;
        try {
            CFInspectorPage cfInspectorPage = navigateToCFInspectorPage(driver);
            abTestPage = new ABTestPage(driver);
            if (cfInspectorPage.displayABTestingLink()) {
                return cfInspectorPage.clickABTestingLink();
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to A/B Testing page", e);
            throw new RuntimeException("Navigation to A/B Testing page failed.", e);
        }
        LoggerUtil.info("Navigated to A/B Testing page: " + abTestPage.getCurrentUrl());
        return abTestPage;
    }

    public static AddRemoveElementsPage navigatToAddRemoveElementsPage(WebDriver driver)
    {
        AddRemoveElementsPage addRemoveElementsPage;
        try{
            CFInspectorPage cfInspectorPage = navigateToCFInspectorPage(driver);
            addRemoveElementsPage = new AddRemoveElementsPage(driver);
            if (cfInspectorPage.displayAddRemoveLink()) {
                return cfInspectorPage.clickAddRemoveLink();
            }
        }catch (RuntimeException e){
            LoggerUtil.error("Failed to navigate to Add Remove Elements page.", e);
            throw new RuntimeException("Navigation to Add Remove Elements page failed.", e);
        }
        LoggerUtil.info("Navigated to Add Remove Elements page: " + addRemoveElementsPage.getCurrentUrl());
        return addRemoveElementsPage;
    }
}

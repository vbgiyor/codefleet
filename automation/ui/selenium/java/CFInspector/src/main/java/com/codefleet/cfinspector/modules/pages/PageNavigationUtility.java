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
            homePage.clickCaseStudiesButton();
            if(homePage.getCurrentUrl().equalsIgnoreCase(baseUrl + "/"))
                return homePage.clickHomeButton();
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to Home page", e);
            throw new RuntimeException("Navigating to Home page failed.");
        }
        return homePage;
    }


    public static AutomationPage navigateToAutomationPage(WebDriver driver) {
        AutomationPage automationPage;
        try {
            HomePage homePage =  navigateToHomePage(driver);
            automationPage = new AutomationPage(driver);
            LoggerUtil.info("Navigating to Automation Testing page: " + automationPage.getCurrentUrl());
            homePage.clickCaseStudiesButton();
            if (homePage.displayCaseStudiesDropdown()) {
                return homePage.clickAutomationLink();
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("Failed to navigate to A/B Testing page", e);
            throw new RuntimeException("Navigating to Automation page failed.");
        }
        return automationPage;
    }


    public static ABTestPage navigateToABTestPage(WebDriver driver) {
        ABTestPage abTestPage;
        try {
            AutomationPage automationPage = navigateToAutomationPage(driver);
            abTestPage = new ABTestPage(driver);
            LoggerUtil.info("Navigating to A/B Testing page: " + abTestPage.getCurrentUrl());
            abTestPage = automationPage.clickABTestingLink();
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
            AutomationPage automationPage = navigateToAutomationPage(driver);
            addRemoveElementsPage = new AddRemoveElementsPage(driver);
            LoggerUtil.info("Navigating to Add Remove Elements page: " + addRemoveElementsPage.getCurrentUrl());
            addRemoveElementsPage = automationPage.clickAddRemoveLink();
        }catch (RuntimeException e){
            LoggerUtil.error("Failed to navigate to Add Remove Elements page.", e);
            throw new RuntimeException("Navigating to Add Remove Elements page failed.", e);
        }
        return addRemoveElementsPage;
    }
}

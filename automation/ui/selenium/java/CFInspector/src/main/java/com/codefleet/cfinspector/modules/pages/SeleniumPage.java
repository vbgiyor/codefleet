package com.codefleet.cfinspector.modules.pages;

import com.codefleet.cfinspector.modules.utils.WaitForElementsUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumPage extends BasePage {

    public SeleniumPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSeleniumPageLoaded() {
        WebElement getTitleElement = getTitleElement();
        return isElementDisplayed(getTitleElement);
    }

    public WebElement getTitleElement() {
        return driver.findElement(locatorParser.getElementLocator("selenium.title"));
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("selenium.title"));
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, title);
        return getText(title);
    }

    public String getDescriptionText() {
        WebElement description = driver.findElement(locatorParser.getElementLocator("selenium.description"));
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, description);
        return getText(description);
    }

    public String getProjectsSectionTitle() {
        WebElement sectionTitle = driver.findElement(locatorParser.getElementLocator("selenium.projects.title"));
        WaitForElementsUtil.waitUntilElementToBeVisible(driver, sectionTitle);
        return getText(sectionTitle);
    }

    public boolean displayProjectCFInspector()
    {
        WebElement projectLink = driver.findElement(locatorParser.getElementLocator("selenium.project.cfinspector"));
        return isElementDisplayed(projectLink);
    }

    public CFInspectorPage clickProjectCFInspector() {
        WebElement projectLink = driver.findElement(locatorParser.getElementLocator("selenium.project.cfinspector"));
        click(projectLink);
        return new CFInspectorPage(driver);
    }

    public void clickProjectGithubRepoListingDemo() {
        WebElement projectLink = driver.findElement(locatorParser.getElementLocator("selenium.project.githubrepo"));
        click(projectLink);
    }

    public void clickProjectPageInspector() {
        WebElement projectLink = driver.findElement(locatorParser.getElementLocator("selenium.project.pageinspector"));
        click(projectLink);
    }
}


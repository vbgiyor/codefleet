# Selenium Test Independence Guide for TestNG

## Overview
This guide outlines how to refactor Selenium tests using TestNG to ensure test independence, particularly for parallel execution, while respecting application navigation flows. It addresses the `ABTestPageTest` class, which currently uses `dependsOnMethods`, and provides a refactored version with independent tests.

## Why Test Independence Matters
- **Parallel Execution**: Independent tests avoid state-sharing issues across threads or browsers.
- **Reliability**: Prevents cascading failures where one test failure skips dependent tests.
- **Maintainability**: Simplifies debugging and scaling of test suites.

## Issues with Dependent Tests
The original `ABTestPageTest` uses `dependsOnMethods`, creating a dependency chain:
- `testABTestPageLoadsFromAutomationPage` navigates to the A/B Testing page.
- Other tests (`testABTestPageHeadlineTextIsCorrect`, `testABTestPageDescriptionTextIsCorrect`, `testNavigationBackToAutomationPageWorks`) depend on it, assuming the browser is on the A/B Testing page.
- Risks:
  - **Parallel execution failures**: Tests may run in different threads/browsers, breaking state assumptions.
  - **Skipped tests**: Failure in the first test skips all dependents, reducing coverage.
  - **Fragility**: Tight coupling complicates maintenance.

## Refactored Independent Test Example
Below is the refactored `ABTestPageTest` class, making each test independent by moving navigation to `@BeforeMethod`.

```java
package com.codefleet.cfinspector.modules.tests;

import com.codefleet.cfinspector.modules.config.ConfigManager;
import com.codefleet.cfinspector.modules.core.WebDriverFactory;
import com.codefleet.cfinspector.modules.pages.ABTestPage;
import com.codefleet.cfinspector.modules.pages.AutomationPage;
import com.codefleet.cfinspector.modules.pages.CaseStudiesPage;
import com.codefleet.cfinspector.modules.pages.HomePage;
import com.codefleet.cfinspector.modules.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ABTestPageTest extends BasePageTest {

    private WebDriver driver;
    private ABTestPage abTestPage;

    @BeforeMethod
    public void navigateToABTestPage() {
        driver = WebDriverFactory.getDriverThreadLocal();
        HomePage homePage = new HomePage(driver);
        CaseStudiesPage caseStudiesPage = new CaseStudiesPage(driver);
        AutomationPage automationPage;
        abTestPage = new ABTestPage(driver);

        String baseUrl = ConfigManager.getBaseUrl();
        homePage.navigateTo(baseUrl);
        homePage.clickCaseStudiesButton();
        boolean displayDropdown = caseStudiesPage.displayCaseStudiesDropdown();
        LoggerUtil.info("displayDropdown: " + displayDropdown);
        try {
            if (displayDropdown) {
                automationPage = caseStudiesPage.clickAutomationLink();
                LoggerUtil.info("Clicked Automation link. Redirecting to Automation page.");
                abTestPage = automationPage.clickABTestingLink();
            } else {
                LoggerUtil.error("Case Studies dropdown not displayed.");
                Assert.fail("Failed to navigate to A/B Testing page: Case Studies dropdown not displayed.");
            }
        } catch (Exception e) {
            LoggerUtil.error("Error navigating to A/B Testing page.", e);
            Assert.fail("Failed to navigate to A/B Testing page: " + e.getMessage());
        }
    }

    @Test
    public void testABTestPageLoads() {
        String expectedUrl = ConfigManager.getBaseUrl() + "/case-studies/automation/abtest";
        String actualUrl = abTestPage.getCurrentUrl();
        LoggerUtil.info("Current URL in testABTestPageLoads: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "A/B Testing page did not load successfully");
    }


    @Test
    public void testABTestPageDescriptionTextIsCorrect() {
        String descriptionText = abTestPage.getDescriptionText();
        Assert.assertTrue(descriptionText.contains("This is a simple A/B testing example"),
                "Description text does not contain 'This is a simple A/B testing example'. Found: " + descriptionText);
    }
}
```

### Key Changes
- **@BeforeMethod**: Centralizes navigation to the A/B Testing page, ensuring each test starts from the correct page.
- **Removed `dependsOnMethods`**: Tests are independent, relying on setup for preconditions.
- **Driver Management**: Uses `WebDriverFactory.getDriverThreadLocal()` for thread-safe WebDriver instances.
- **Error Handling**: Fails tests early if navigation fails, with clear error messages.

## Best Practices for New Tests
1. **Independence**: Set up preconditions in `@BeforeMethod` or within tests. Avoid `dependsOnMethods` unless sequential logic is required (e.g., server-side state changes).
2. **Centralize Navigation**: Use utility methods or page objects to encapsulate navigation logic:
   ```java
   public class NavigationUtil {
       public static ABTestPage navigateToABTestPage(WebDriver driver) {
           HomePage homePage = new HomePage(driver);
           CaseStudiesPage caseStudiesPage = new CaseStudiesPage(driver);
           AutomationPage automationPage;
           ABTestPage abTestPage = new ABTestPage(driver);
           String baseUrl = ConfigManager.getBaseUrl();
           homePage.navigateTo(baseUrl);
           homePage.clickCaseStudiesButton();
           if (caseStudiesPage.displayCaseStudiesDropdown()) {
               automationPage = caseStudiesPage.clickAutomationLink();
               return automationPage.clickABTestingLink();
           }
           throw new RuntimeException("Failed to navigate to A/B Testing page");
       }
   }
   ```
3. **Optimize Navigation**: Use direct URLs if valid (e.g., `driver.get(baseUrl + "/case-studies/automation/abtest")`) or optimize page object methods.
4. **Test One Behavior**: Each test should verify a single feature (e.g., headline text, button visibility).
5. **Parallel Execution**:
   - Use thread-local WebDriver.
   - Configure `testng.xml` for parallel runs:
     ```xml
     <suite name="Suite" parallel="methods" thread-count="4">
         <test name="ABTestPageTests">
             <classes>
                 <class name="com.codefleet.cfinspector.modules.tests.ABTestPageTest"/>
             </classes>
         </test>
     </suite>
     ```
   - Clean up WebDriver in `@AfterMethod`:
     ```java
     @AfterMethod
     public void tearDown() {
         if (driver != null) {
             driver.quit();
         }
     }
     ```

## When to Use `dependsOnMethods`
- For tests requiring strict sequential logic (e.g., create, update, delete a resource).
- When setup is prohibitively expensive and direct navigation isn’t feasible.
- Note: UI tests like these rarely need dependencies, as navigation can be repeated.

## Example New Test
```java
@Test
public void testABTestPageButtonVisibility() {
    boolean isButtonVisible = abTestPage.isCallToActionButtonVisible();
    Assert.assertTrue(isButtonVisible, "Call to Action button is not visible on A/B Testing page.");
}
```

## Conclusion
- **Avoid `dependsOnMethods`** for UI tests like `ABTestPageTest` to ensure independence and parallel compatibility.
- **Use `@BeforeMethod` or utilities** to set up navigation, respecting application flow.
- **Optimize navigation** to balance realism and efficiency.
- **Design new tests** to be independent, focused, and scalable.
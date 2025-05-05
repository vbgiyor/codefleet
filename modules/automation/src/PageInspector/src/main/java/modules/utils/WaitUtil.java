package modules.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {
    private static final int DEFAULT_WAIT = 10;

    public static void waitUntilElementToBeClickable(WebDriver driver, WebElement element)
    {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilElementIsVisible(WebDriver driver, WebElement element)
    {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.visibilityOf(element));
    }
}

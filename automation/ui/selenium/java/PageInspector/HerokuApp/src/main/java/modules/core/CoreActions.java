package modules.core;

import org.openqa.selenium.WebElement;

public interface CoreActions {
    void click(WebElement element);
    void inputText(WebElement element, String txt);
    String getText(WebElement element);
    boolean isDisplayed(WebElement element);

//    TO DO; Add more common functions later.
}

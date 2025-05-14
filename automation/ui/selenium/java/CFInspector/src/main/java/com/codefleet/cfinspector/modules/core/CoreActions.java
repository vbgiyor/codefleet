package com.codefleet.cfinspector.modules.core;

import org.openqa.selenium.WebElement;

public interface CoreActions {

    void click(WebElement element);
    void insetText(WebElement element, String text);
    String getText(WebElement element);
    boolean isElementDisplayed(WebElement element);
}

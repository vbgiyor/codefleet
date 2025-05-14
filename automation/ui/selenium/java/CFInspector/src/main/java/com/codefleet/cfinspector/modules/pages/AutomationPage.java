package com.codefleet.cfinspector.modules.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AutomationPage extends BasePage {

    public AutomationPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(locatorParser.getElementLocator("automation.title"));
        return getText(title);
    }

    public String getInternetDescriptionText() {
        WebElement internetDescription = driver.findElement(locatorParser.getElementLocator("automation.internet_description"));
        return getText(internetDescription);
    }

    public void clickInternetLink() {
        WebElement internetLink = driver.findElement(locatorParser.getElementLocator("automation.internet_link"));
        click(internetLink);
    }

    public ABTestPage clickABTestingLink() {
        WebElement abTestingLink = driver.findElement(locatorParser.getElementLocator("automation.ab_testing_link"));
        click(abTestingLink);
        return new ABTestPage(driver);
    }

    public AddRemoveElementsPage clickAddRemoveLink() {
        WebElement addRemoveLink = driver.findElement(locatorParser.getElementLocator("automation.add_remove_link"));
        click(addRemoveLink);
        return new AddRemoveElementsPage(driver);
    }

    public BasicAuthPage clickBasicAuthLink() {
        WebElement basicAuthLink = driver.findElement(locatorParser.getElementLocator("automation.basic_auth_link"));
        click(basicAuthLink);
        return new BasicAuthPage(driver);
    }

    public void clickBrokenImagesLink() {
        WebElement brokenImagesLink = driver.findElement(locatorParser.getElementLocator("automation.broken_images_link"));
        click(brokenImagesLink);
    }

    public void clickChallengingDomLink() {
        WebElement challengingDomLink = driver.findElement(locatorParser.getElementLocator("automation.challenging_dom_link"));
        click(challengingDomLink);
    }

    public void clickCheckboxesLink() {
        WebElement checkboxesLink = driver.findElement(locatorParser.getElementLocator("automation.checkboxes_link"));
        click(checkboxesLink);
    }

    public void clickContextMenuLink() {
        WebElement contextMenuLink = driver.findElement(locatorParser.getElementLocator("automation.context_menu_link"));
        click(contextMenuLink);
    }

    public void clickDigestAuthLink() {
        WebElement digestAuthLink = driver.findElement(locatorParser.getElementLocator("automation.digest_auth_link"));
        click(digestAuthLink);
    }

    public void clickDisappearingElementsLink() {
        WebElement disappearingElementsLink = driver.findElement(locatorParser.getElementLocator("automation.disappearing_elements_link"));
        click(disappearingElementsLink);
    }

    public void clickDragAndDropLink() {
        WebElement dragAndDropLink = driver.findElement(locatorParser.getElementLocator("automation.drag_and_drop_link"));
        click(dragAndDropLink);
    }

    public void clickDropdownLink() {
        WebElement dropdownLink = driver.findElement(locatorParser.getElementLocator("automation.dropdown_link"));
        click(dropdownLink);
    }

    public void clickDynamicControlsLink() {
        WebElement dynamicControlsLink = driver.findElement(locatorParser.getElementLocator("automation.dynamic_controls_link"));
        click(dynamicControlsLink);
    }

    public void clickDynamicLoadingLink() {
        WebElement dynamicLoadingLink = driver.findElement(locatorParser.getElementLocator("automation.dynamic_loading_link"));
        click(dynamicLoadingLink);
    }

    public void clickEntryAdLink() {
        WebElement entryAdLink = driver.findElement(locatorParser.getElementLocator("automation.entry_ad_link"));
        click(entryAdLink);
    }

    public void clickExitIntentLink() {
        WebElement exitIntentLink = driver.findElement(locatorParser.getElementLocator("automation.exit_intent_link"));
        click(exitIntentLink);
    }

    public void clickFileDownloadLink() {
        WebElement fileDownloadLink = driver.findElement(locatorParser.getElementLocator("automation.file_download_link"));
        click(fileDownloadLink);
    }

    public void clickFileUploadLink() {
        WebElement fileUploadLink = driver.findElement(locatorParser.getElementLocator("automation.file_upload_link"));
        click(fileUploadLink);
    }

    public void clickFloatingMenuLink() {
        WebElement floatingMenuLink = driver.findElement(locatorParser.getElementLocator("automation.floating_menu_link"));
        click(floatingMenuLink);
    }

    public void clickForgotPasswordLink() {
        WebElement forgotPasswordLink = driver.findElement(locatorParser.getElementLocator("automation.forgot_password_link"));
        click(forgotPasswordLink);
    }

    public void clickFormAuthenticationLink() {
        WebElement formAuthenticationLink = driver.findElement(locatorParser.getElementLocator("automation.form_authentication_link"));
        click(formAuthenticationLink);
    }

    public void clickFramesLink() {
        WebElement framesLink = driver.findElement(locatorParser.getElementLocator("automation.frames_link"));
        click(framesLink);
    }

    public void clickGeolocationLink() {
        WebElement geolocationLink = driver.findElement(locatorParser.getElementLocator("automation.geolocation_link"));
        click(geolocationLink);
    }

    public void clickHorizontalSliderLink() {
        WebElement horizontalSliderLink = driver.findElement(locatorParser.getElementLocator("automation.horizontal_slider_link"));
        click(horizontalSliderLink);
    }

    public void clickHoversLink() {
        WebElement hoversLink = driver.findElement(locatorParser.getElementLocator("automation.hovers_link"));
        click(hoversLink);
    }

    public void clickInfiniteScrollLink() {
        WebElement infiniteScrollLink = driver.findElement(locatorParser.getElementLocator("automation.infinite_scroll_link"));
        click(infiniteScrollLink);
    }

    public void clickInputsLink() {
        WebElement inputsLink = driver.findElement(locatorParser.getElementLocator("automation.inputs_link"));
        click(inputsLink);
    }

    public void clickJQueryUILink() {
        WebElement jqueryuiLink = driver.findElement(locatorParser.getElementLocator("automation.jqueryui_link"));
        click(jqueryuiLink);
    }

    public void clickJavascriptAlertsLink() {
        WebElement javascriptAlertsLink = driver.findElement(locatorParser.getElementLocator("automation.javascript_alerts_link"));
        click(javascriptAlertsLink);
    }

    public void clickJavascriptErrorLink() {
        WebElement javascriptErrorLink = driver.findElement(locatorParser.getElementLocator("automation.javascript_error_link"));
        click(javascriptErrorLink);
    }

    public void clickKeyPressesLink() {
        WebElement keyPressesLink = driver.findElement(locatorParser.getElementLocator("automation.key_presses_link"));
        click(keyPressesLink);
    }

    public void clickLargeLink() {
        WebElement largeLink = driver.findElement(locatorParser.getElementLocator("automation.large_link"));
        click(largeLink);
    }

    public void clickLoginLink() {
        WebElement loginLink = driver.findElement(locatorParser.getElementLocator("automation.login_link"));
        click(loginLink);
    }

    public void clickMultipleWindowsLink() {
        WebElement multipleWindowsLink = driver.findElement(locatorParser.getElementLocator("automation.multiple_windows_link"));
        click(multipleWindowsLink);
    }

    public void clickNestedFramesLink() {
        WebElement nestedFramesLink = driver.findElement(locatorParser.getElementLocator("automation.nested_frames_link"));
        click(nestedFramesLink);
    }

    public void clickNotificationMessageLink() {
        WebElement notificationMessageLink = driver.findElement(locatorParser.getElementLocator("automation.notification_message_link"));
        click(notificationMessageLink);
    }

    public void clickRedirectLink() {
        WebElement redirectLink = driver.findElement(locatorParser.getElementLocator("automation.redirect_link"));
        click(redirectLink);
    }

    public void clickSecureFileDownloadLink() {
        WebElement secureFileDownloadLink = driver.findElement(locatorParser.getElementLocator("automation.secure_file_download_link"));
        click(secureFileDownloadLink);
    }

    public void clickShadowDomLink() {
        WebElement shadowDomLink = driver.findElement(locatorParser.getElementLocator("automation.shadow_dom_link"));
        click(shadowDomLink);
    }

    public void clickShiftingContentLink() {
        WebElement shiftingContentLink = driver.findElement(locatorParser.getElementLocator("automation.shifting_content_link"));
        click(shiftingContentLink);
    }

    public void clickSlowLink() {
        WebElement slowLink = driver.findElement(locatorParser.getElementLocator("automation.slow_link"));
        click(slowLink);
    }

    public void clickSortableDataTablesLink() {
        WebElement sortableDataTablesLink = driver.findElement(locatorParser.getElementLocator("automation.sortable_data_tables_link"));
        click(sortableDataTablesLink);
    }

    public void clickStatusCodesLink() {
        WebElement statusCodesLink = driver.findElement(locatorParser.getElementLocator("automation.status_codes_link"));
        click(statusCodesLink);
    }

    public void clickTablesLink() {
        WebElement tablesLink = driver.findElement(locatorParser.getElementLocator("automation.tables_link"));
        click(tablesLink);
    }

    public void clickTinymceLink() {
        WebElement tinymceLink = driver.findElement(locatorParser.getElementLocator("automation.tinymce_link"));
        click(tinymceLink);
    }

    public void clickTyposLink() {
        WebElement typosLink = driver.findElement(locatorParser.getElementLocator("automation.typos_link"));
        click(typosLink);
    }

    public void clickWindowsLink() {
        WebElement windowsLink = driver.findElement(locatorParser.getElementLocator("automation.windows_link"));
        click(windowsLink);
    }

    public String getFooterNoteText() {
        WebElement footerNote = driver.findElement(locatorParser.getElementLocator("automation.footer_note"));
        return getText(footerNote);
    }

    public void clickOriginalInternetLink() {
        WebElement originalInternetLink = driver.findElement(locatorParser.getElementLocator("automation.original_internet_link"));
        click(originalInternetLink);
    }

    public boolean isAutomationPageLoaded() {
        WebElement abTestingLink = driver.findElement(locatorParser.getElementLocator("automation.ab_testing_link"));
        return isElementDisplayed(abTestingLink);
    }
}
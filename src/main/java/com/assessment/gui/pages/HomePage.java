package com.assessment.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the "the-internet" home page (list of example links).
 * Exposes fluent navigation methods that return the target page object,
 * so tests can chain: homePage.goToFileUpload().uploadFile(...)
 */
public class HomePage extends BasePage {

    private static final By FILE_UPLOAD_LINK = By.linkText("File Upload");
    private static final By DYNAMIC_LOADING_LINK = By.linkText("Dynamic Loading");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage navigateToHome() {
        open("/");
        return this;
    }

    public FileUploadPage goToFileUpload() {
        click(FILE_UPLOAD_LINK);
        return new FileUploadPage(driver);
    }

    public DynamicLoadingListPage goToDynamicLoading() {
        click(DYNAMIC_LOADING_LINK);
        return new DynamicLoadingListPage(driver);
    }
}

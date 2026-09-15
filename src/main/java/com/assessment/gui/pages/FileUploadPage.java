package com.assessment.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the /upload (File Upload) page.
 */
public class FileUploadPage extends BasePage {

    private static final By FILE_INPUT = By.id("file-upload");
    private static final By SUBMIT_BUTTON = By.id("file-submit");
    private static final By UPLOADED_FILES_LABEL = By.id("uploaded-files");
    private static final By PAGE_HEADER = By.tagName("h3");

    public FileUploadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects the file to upload. The absolute path is supplied by the caller
     * (built from test data / a resources folder), never hard-coded here.
     */
    public FileUploadPage selectFile(String absoluteFilePath) {
        driver.findElement(FILE_INPUT).sendKeys(absoluteFilePath);
        return this;
    }

    public FileUploadPage submit() {
        click(SUBMIT_BUTTON);
        return this;
    }

    public String getUploadedFileName() {
        return getText(UPLOADED_FILES_LABEL);
    }

    public String getResultHeaderText() {
        return getText(PAGE_HEADER);
    }
}

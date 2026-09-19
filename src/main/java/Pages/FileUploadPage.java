package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import BasesAndConfig.ElementActions;
import io.qameta.allure.Step;

// upload page - pick a file, hit submit, check the confirmation.

public class FileUploadPage {

    private final WebDriver driver;

    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    locators
     */
    private final By fileInput = By.id("file-upload");
    private final By submitButton = By.id("file-submit");
    private final By uploadedFileNameLabel = By.id("uploaded-files");
    private final By resultHeader = By.tagName("h3");

    /*
    methods
     */
    @Step("Select file for upload: {absoluteFilePath}")
    public FileUploadPage selectFile(String absoluteFilePath) {
        ElementActions.sendData(driver, fileInput, absoluteFilePath);
        return this;
    }

    @Step("Submit the selected file")
    public FileUploadPage submit() {
        ElementActions.clickElement(driver, submitButton);
        return this;
    }

    @Step("Read the uploaded file name shown on the result page")
    public String getUploadedFileName() {
        return ElementActions.getText(driver, uploadedFileNameLabel);
    }

    @Step("Read the result page header text")
    public String getResultHeaderText() {
        return ElementActions.getText(driver, resultHeader);
    }
}
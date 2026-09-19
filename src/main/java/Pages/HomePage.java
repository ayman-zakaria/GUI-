package Pages;

import BasesAndConfig.ConfigManager;
import BasesAndConfig.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Landing page - just the two links we care about

public class HomePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    locators
     */
    private final By fileUploadLink = By.linkText("File Upload");
    private final By dynamicLoadingLink = By.linkText("Dynamic Loading");

    /*
    methods
     */
    @Step("Open the home page")
    public HomePage open() {
        driver.get(ConfigManager.baseUrl());
        return this;
    }

    @Step("Navigate to File Upload page")
    public FileUploadPage goToFileUpload() {
        ElementActions.clickElement(driver, fileUploadLink);
        return new FileUploadPage(driver);
    }

    @Step("Navigate to Dynamic Loading page")
    public DynamicLoadingListPage goToDynamicLoading() {
        ElementActions.clickElement(driver, dynamicLoadingLink);
        return new DynamicLoadingListPage(driver);
    }
}

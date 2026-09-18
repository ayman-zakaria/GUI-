package Pages;

import BasesAndConfig.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the /dynamic_loading listing page which links to Example 1 and Example 2.
 */
public class DynamicLoadingListPage {

    private final WebDriver driver;

    public DynamicLoadingListPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    locators
     */
    private final By example2Link = By.linkText("Example 2: Element rendered after the fact");

    /*
    methods
     */
    @Step("Open Dynamic Loading Example 2")
    public DynamicLoadingExamplePage goToExample2() {
        ElementActions.clickElement(driver, example2Link);
        return new DynamicLoadingExamplePage(driver);
    }
}

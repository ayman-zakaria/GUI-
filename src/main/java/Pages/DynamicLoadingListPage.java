package Pages;

import BasesAndConfig.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Just the list page with the Example 1 / Example 2 links - Example 2 is the one we need.
public class DynamicLoadingListPage {

    private final WebDriver driver;

    public DynamicLoadingListPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    locators
     */
    private final By example1Link = By.linkText("Example 1: Element on page that is hidden");
    private final By example2Link = By.linkText("Example 2: Element rendered after the fact");

    /*
    methods
     */
    @Step("Open Dynamic Loading Example 1")
    public DynamicLoadingExamplePage goToExample1() {
        ElementActions.clickElement(driver, example1Link);
        return new DynamicLoadingExamplePage(driver);
    }

    @Step("Open Dynamic Loading Example 2")
    public DynamicLoadingExamplePage goToExample2() {
        ElementActions.clickElement(driver, example2Link);
        return new DynamicLoadingExamplePage(driver);
    }
}
package Pages;

import BasesAndConfig.ElementActions;
import BasesAndConfig.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for a single Dynamic Loading example page (Example 2).
 * Handles the click on "Start" and proper synchronization while the loading
 * indicator is shown, then exposes the finished text once it is rendered.
 */
public class DynamicLoadingExamplePage {

    private final WebDriver driver;

    public DynamicLoadingExamplePage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    locators
     */
    private final By startButton = By.cssSelector("#start button");
    private final By loadingIndicator = By.id("loading");
    private final By finishText = By.cssSelector("#finish h4");

    /*
    methods
     */
    @Step("Click Start on the dynamic loading example")
    public DynamicLoadingExamplePage clickStart() {
        ElementActions.clickElement(driver, startButton);
        return this;
    }

    @Step("Wait for loading to finish and read the result text")
    public String waitForResultText() {
        Waits.waitForElementInvisible(driver, loadingIndicator);
        return ElementActions.getText(driver, finishText);
    }
}

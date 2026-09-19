package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import BasesAndConfig.ElementActions;
import BasesAndConfig.Waits;
import io.qameta.allure.Step;

// Example 2 - the element only shows up after the loading spinner disappears,
// so waitForResultText() waits on the spinner instead of the text itself.
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

    /* for the edge-case test - checks right now, no waiting. Example 2 doesn't just hide
    the element with CSS, it's not even in the DOM until the JS inserts it after loading,
    */
    @Step("Check whether the result text is present yet")
    public boolean isResultTextPresent() {
        return !driver.findElements(finishText).isEmpty();
    }
}
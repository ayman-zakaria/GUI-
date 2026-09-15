package com.assessment.gui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for a single Dynamic Loading example page (Example 2).
 * Handles the click on "Start" and proper synchronization while the loading
 * indicator is shown, then exposes the finished text once it is rendered.
 */
public class DynamicLoadingExamplePage extends BasePage {

    private static final By START_BUTTON = By.cssSelector("#start button");
    private static final By LOADING_INDICATOR = By.id("loading");
    private static final By FINISH_TEXT = By.cssSelector("#finish h4");

    public DynamicLoadingExamplePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click Start on the dynamic loading example")
    public DynamicLoadingExamplePage clickStart() {
        click(START_BUTTON);
        return this;
    }

    /**
     * Waits for the loading spinner to disappear, i.e. explicit synchronization
     * rather than a hard-coded Thread.sleep(), then returns the finished text.
     */
    @Step("Wait for loading to finish and read the result text")
    public String waitForResultText() {
        waitForInvisible(LOADING_INDICATOR);
        return getText(FINISH_TEXT);
    }
}

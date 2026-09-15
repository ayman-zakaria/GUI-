package com.assessment.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the /dynamic_loading listing page which links to Example 1 and Example 2.
 */
public class DynamicLoadingListPage extends BasePage {

    private static final By EXAMPLE_2_LINK = By.linkText("Example 2: Element rendered after the fact");

    public DynamicLoadingListPage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingExamplePage goToExample2() {
        click(EXAMPLE_2_LINK);
        return new DynamicLoadingExamplePage(driver);
    }
}

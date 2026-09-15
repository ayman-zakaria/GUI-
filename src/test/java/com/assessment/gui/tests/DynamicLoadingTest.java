package com.assessment.gui.tests;

import com.assessment.gui.base.BaseTest;
import com.assessment.gui.utils.PropertiesReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Verifies the "Dynamic Loading" > "Example 2" scenario: clicking Start renders
 * an element only after the AJAX-style loading indicator finishes.
 */
@Epic("The Internet - GUI Assessment")
@Feature("Dynamic Loading")
public class DynamicLoadingTest extends BaseTest {

    private static final PropertiesReader TEST_DATA = new PropertiesReader("testdata.properties");

    @Test(description = "Start Example 2 dynamic loading and verify the finished text")
    @Severity(SeverityLevel.NORMAL)
    public void shouldDisplayExpectedTextAfterLoadingFinishes() {
        String expectedText = TEST_DATA.get("dynamicLoading.example2.expectedText");

        String actualText = homePage()
                .goToDynamicLoading()
                .goToExample2()
                .clickStart()
                .waitForResultText();

        Assert.assertEquals(actualText, expectedText,
                "The text displayed after loading finishes should match the expected value");
    }
}

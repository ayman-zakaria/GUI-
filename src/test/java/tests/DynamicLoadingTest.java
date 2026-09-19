package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import BasesAndConfig.ConfigReader;
import Pages.DynamicLoadingExamplePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


@Epic("The Internet - GUI Assessment")
@Feature("Dynamic Loading")
public class DynamicLoadingTest extends BaseTest {

    private static final ConfigReader TEST_DATA = new ConfigReader("testdata.properties");

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

    // edge case - if this ever came back true, it'd mean our wait in waitForResultText()
    // isn't actually doing anything and the test above could be passing for the wrong
    // reason (e.g. the text was already there from a previous run/page state).
    @Test(description = "Verify the result text is not present before Start is clicked")
    @Severity(SeverityLevel.MINOR)
    public void shouldNotShowResultTextBeforeStartIsClicked() {
        DynamicLoadingExamplePage example2 = homePage()
                .goToDynamicLoading()
                .goToExample2();

        Assert.assertFalse(example2.isResultTextPresent(),
                "The result text shouldn't exist in the DOM until after Start finishes loading");
    }

    // edge case, the other kind - Example 1 hides the result with CSS instead of leaving
    // it out of the DOM entirely, so it should already be present (just not visible yet).
    @Test(description = "Verify Example 1's result element exists but is hidden before Start is clicked")
    @Severity(SeverityLevel.MINOR)
    public void shouldHaveResultElementPresentButHiddenOnExample1BeforeStart() {
        String expectedText = TEST_DATA.get("dynamicLoading.example1.expectedText");

        DynamicLoadingExamplePage example1 = homePage()
                .goToDynamicLoading()
                .goToExample1();

        Assert.assertTrue(example1.isResultTextPresent(),
                "Unlike Example 2, Example 1's element should already be in the DOM, just hidden");

        String actualText = example1.clickStart().waitForResultText();

        Assert.assertEquals(actualText, expectedText,
                "Example 1 should show the same text as Example 2 once loading finishes");
    }
}
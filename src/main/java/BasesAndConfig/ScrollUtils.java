package BasesAndConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Scrolls an element into view before interacting with it, so elements below the fold
 */
public class ScrollUtils {

    private ScrollUtils() {
    }

    public static void scrollToElement(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                driver.findElement(locator));
    }
}

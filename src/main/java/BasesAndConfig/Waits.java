package BasesAndConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Static explicit-wait helpers used by {@link ElementActions} and page objects.
 * Centralizing synchronization here means no page object or test needs to build its
 * own WebDriverWait or rely on a fixed Thread.sleep().
 */
public class Waits {

    private Waits() {
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.explicitWaitSeconds()));
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForElementInvisible(WebDriver driver, By locator) {
        wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}

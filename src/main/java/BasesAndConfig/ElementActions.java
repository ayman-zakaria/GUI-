package BasesAndConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Every element interaction used by page objects goes through here, so that
 * synchronization (wait) and visibility (scroll into view) are never duplicated
 * inside individual Page Object methods.
 */
public class ElementActions {

    private ElementActions() {
    }

    public static void sendData(WebDriver driver, By locator, String data) {
        Waits.waitForElementVisible(driver, locator);
        ScrollUtils.scrollToElement(driver, locator);
        driver.findElement(locator).sendKeys(data);
    }

    public static void clickElement(WebDriver driver, By locator) {
        Waits.waitForElementClickable(driver, locator);
        ScrollUtils.scrollToElement(driver, locator);
        driver.findElement(locator).click();
    }

    public static String getText(WebDriver driver, By locator) {
        Waits.waitForElementVisible(driver, locator);
        return driver.findElement(locator).getText();
    }
}

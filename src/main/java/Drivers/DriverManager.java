package Drivers;

import BasesAndConfig.LogUtil;
import org.openqa.selenium.WebDriver;

/**
 * Owns the WebDriver instance per test thread, so tests can run safely in parallel
 * without sharing (or accidentally overwriting) each other's driver.
 */
public class DriverManager {

    private DriverManager() {
    }

    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    public static WebDriver createInstance(String browserName) {
        WebDriver driver = BrowserFactory.getBrowser(browserName);
        setDriver(driver);
        return getDriver();
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver == null) {
            LogUtil.warn("getDriver() called before a driver was created on this thread");
        }
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }
}

package tests;

import BasesAndConfig.ConfigManager;
import Drivers.DriverManager;
import Listeners.TestListener;
import Pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Base class for all GUI test classes.
 * Creates a fresh WebDriver per test method (via DriverManager/BrowserFactory) and
 * quits it afterwards, so individual test classes never manage the driver directly.
 * {@link TestListener} is wired here once so every subclass gets logging, Allure
 * result cleanup and failure screenshots without repeating the annotation.
 */
@Listeners(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.createInstance(ConfigManager.browser());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected HomePage homePage() {
        return new HomePage(driver).open();
    }
}

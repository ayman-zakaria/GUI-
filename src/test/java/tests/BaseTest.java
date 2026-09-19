package tests;

import BasesAndConfig.ConfigManager;
import Drivers.DriverManager;
import Listeners.TestListener;
import Pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

// Spins up a fresh driver before each test and kills it after 

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

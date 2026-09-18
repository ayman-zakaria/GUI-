package Drivers;

import BasesAndConfig.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Builds a fully configured WebDriver instance for the requested browser.
 * Browser choice, headless mode and timeouts are all read from configuration
 * rather than hard-coded, and driver binaries are resolved automatically via
 * WebDriverManager (no fixed local path to a chromedriver/geckodriver executable).
 */
public class BrowserFactory {

    private BrowserFactory() {
    }

    public static WebDriver getBrowser(String browserName) {
        WebDriver driver;

        switch (browserName.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (ConfigManager.headless()) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (ConfigManager.headless()) {
                    chromeOptions.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.implicitWaitSeconds()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.pageLoadTimeoutSeconds()));
        driver.manage().window().maximize();
        return driver;
    }
}

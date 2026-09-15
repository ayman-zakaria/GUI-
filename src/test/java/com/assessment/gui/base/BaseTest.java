package com.assessment.gui.base;

import com.assessment.gui.pages.HomePage;
import com.assessment.gui.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

/**
 * Base class for all GUI test classes.
 * Owns the WebDriver lifecycle (create before each test, quit after) so that
 * individual test classes never manage the driver directly, and captures a
 * screenshot automatically whenever a test fails.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshotOnFailure(result.getName());
        }
        DriverFactory.quitDriver();
    }

    protected HomePage homePage() {
        return new HomePage(driver).navigateToHome();
    }

    private void captureScreenshotOnFailure(String testName) {
        try {
            org.openqa.selenium.TakesScreenshot ts = (org.openqa.selenium.TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
            File targetDir = new File("target/screenshots");
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            File output = new File(targetDir, testName + ".png");
            try (ByteArrayInputStream in = new ByteArrayInputStream(screenshot)) {
                Files.copy(in, output.toPath());
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}

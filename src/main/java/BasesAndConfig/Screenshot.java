package BasesAndConfig;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Captures a screenshot from the current WebDriver, saves it under target/screenshots
 * and attaches it to the Allure report in the same step.
 */
public class Screenshot {

    private final WebDriver driver;

    public Screenshot(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Capture screenshot: {name}")
    public void takeScreenshot(String name) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));

        try {
            File targetDir = new File("target/screenshots");
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            Files.write(new File(targetDir, name + ".png").toPath(), screenshotBytes);
        } catch (IOException e) {
            LogUtil.error("Failed to save screenshot to disk: " + e.getMessage());
        }
    }
}

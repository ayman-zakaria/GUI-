package tests;

import java.io.File;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import BasesAndConfig.ConfigReader;
import Pages.FileUploadPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

/**
 * Verifies that a file can be selected, submitted, and that the application
 * confirms a successful upload, per the "File Upload" scenario.
 */
@Epic("The Internet - GUI Assessment")
@Feature("File Upload")
public class FileUploadTest extends BaseTest {

    private static final ConfigReader TEST_DATA = new ConfigReader("testdata.properties");

    @Test(description = "Upload a small image file and verify it was uploaded successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldUploadFileSuccessfully() {
        String fileName = TEST_DATA.get("fileUpload.uploadFileName");
        String expectedHeader = TEST_DATA.get("fileUpload.successHeaderText");
        String absolutePath = resolveTestFilePath(fileName);

        FileUploadPage fileUploadPage = homePage()
                .goToFileUpload()
                .selectFile(absolutePath)
                .submit();

        Assert.assertEquals(fileUploadPage.getResultHeaderText(), expectedHeader,
                "The page header should confirm a successful upload");
        Assert.assertEquals(fileUploadPage.getUploadedFileName(), fileName,
                "The uploaded file name displayed on the page should match the file that was submitted");
    }

    // negative case - just landing on the page shouldn't ever show the success state.
    @Test(description = "Verify navigating to the upload page directly shows the form, not a success state")
    @Severity(SeverityLevel.MINOR)
    public void shouldShowUploadFormOnDirectNavigation() {
        String expectedFormHeader = TEST_DATA.get("fileUpload.formHeaderText");

        String actualHeader = homePage()
                .goToFileUpload()
                .getResultHeaderText();

        Assert.assertEquals(actualHeader, expectedFormHeader,
                "Just opening the upload page should show the empty form, not a leftover success message");
    }

    
    private String resolveTestFilePath(String fileName) {
        URL resource = getClass().getClassLoader().getResource("testfiles/" + fileName);
        if (resource == null) {
            throw new IllegalStateException("Test file not found on classpath: " + fileName);
        }
        return new File(resource.getFile()).getAbsolutePath();
    }
}
package com.assessment.gui.tests;

import com.assessment.gui.base.BaseTest;
import com.assessment.gui.pages.FileUploadPage;
import com.assessment.gui.utils.PropertiesReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;

/**
 * Verifies that a file can be selected, submitted, and that the application
 * confirms a successful upload, per the "File Upload" scenario.
 */
@Epic("The Internet - GUI Assessment")
@Feature("File Upload")
public class FileUploadTest extends BaseTest {

    private static final PropertiesReader TEST_DATA = new PropertiesReader("testdata.properties");

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

    /**
     * Resolves the absolute filesystem path of a bundled test file from the classpath,
     * avoiding any hard-coded absolute path in the test itself.
     */
    private String resolveTestFilePath(String fileName) {
        URL resource = getClass().getClassLoader().getResource("testfiles/" + fileName);
        if (resource == null) {
            throw new IllegalStateException("Test file not found on classpath: " + fileName);
        }
        return new File(resource.getFile()).getAbsolutePath();
    }
}

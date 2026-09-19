package Listeners;

import BasesAndConfig.LogUtil;
import BasesAndConfig.Screenshot;
import Drivers.DriverManager;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

// Hooked up once on BaseTest via @Listeners, so every test gets this for free:
// screenshot attached automatically instead of relying on someone.
public class TestListener implements IExecutionListener, ITestListener {

    private static final String ALLURE_RESULTS_DIR = "target/allure-results";

    @Override
    public void onExecutionStart() {
        clearDirectory(ALLURE_RESULTS_DIR);
        LogUtil.info("Test execution starting");
    }

    @Override
    public void onExecutionFinish() {
        LogUtil.info("Test execution finished");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtil.info("PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtil.error("FAILED: " + result.getName() + " - " + result.getThrowable());
        if (DriverManager.getDriver() != null) {
            new Screenshot(DriverManager.getDriver()).takeScreenshot(result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtil.warn("SKIPPED: " + result.getName());
    }

    private void clearDirectory(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}

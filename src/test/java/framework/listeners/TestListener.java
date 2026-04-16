package framework.listeners;

import framework.utils.ScreenshotUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            try {
                ScreenshotUtils.takeScreenshot();
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
}
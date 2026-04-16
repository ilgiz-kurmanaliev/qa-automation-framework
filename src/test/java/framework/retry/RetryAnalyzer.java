package framework.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int currentRetry = 0;
    private static final int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (currentRetry < maxRetryCount) {
            currentRetry++;
            return true;
        }
        return false;
    }
}
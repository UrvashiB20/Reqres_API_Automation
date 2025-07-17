package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class ExtentReportListener implements ITestListener {

        private static ExtentReports extentReports;

    static {
        try {
            extentReports = ExtentReportManager.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extentReports.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().info("Starting "+result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        if (result.getStatus()==ITestResult.SUCCESS){
            test.get().pass(result.getMethod().getMethodName()+"Test case passed successfully");
        }
    }

    public void onTestFailure(ITestResult result) {
        if (result.getStatus()==ITestResult.FAILURE){
            test.get().fail(result.getMethod().getMethodName()+"Test case failed"+result.getThrowable());
        }
    }

    public void onTestSkipped(ITestResult result) {
        if (result.getStatus()==ITestResult.SKIP){
            test.get().skip(result.getMethod().getMethodName()+"Test case skipped");
        }
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
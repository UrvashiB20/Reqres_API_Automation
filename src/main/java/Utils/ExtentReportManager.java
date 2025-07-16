package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extentReports;

    private ExtentReportManager() {}

    public static ExtentReports getInstance() throws IOException {

        if (extentReports==null){
            createInstance();
        }
        return extentReports;
    }

    public static void createInstance() throws IOException {

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportFilepath = System.getProperty("user.dir")+"/Reports/TestcaseReport"+timeStamp+".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFilepath);
        reporter.config().setDocumentTitle("API Automation Report");
        reporter.config().setReportName("Reqres API");
        reporter.config().setTheme(Theme.DARK);
        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Framework","RestAssured+testNG");
        extentReports.setSystemInfo("Environment","QA");
        extentReports.setSystemInfo("OS",System.getProperty("os.name"));
        extentReports.setSystemInfo("JAVA Version",System.getProperty("java.version"));
        extentReports.setSystemInfo("Base URL",PropertyReader.readProperty("serverAddress"));
    }

}
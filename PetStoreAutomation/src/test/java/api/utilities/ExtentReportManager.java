package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    // Called at the start of the test execution
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        // Specify the location of the report
        sparkReporter = new ExtentSparkReporter("./reports/" + repName);

        // Report Configurations
        sparkReporter.config().setDocumentTitle("RestAssured Automation Project"); // Title of the report
        sparkReporter.config().setReportName("Pet Store Users API"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK); // Set the report theme to dark

        // Initialize ExtentReports and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set System Info
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
    }

    // Called when a test case starts
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName()); // Create a new test in the report with the test name
    }

    // Called when a test case passes
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    // Called when a test case fails
    public void onTestFailure(ITestResult result) {
        test.fail("Test Failed: " + result.getThrowable());
    }

    // Called when a test case is skipped
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped: " + result.getThrowable());
    }

    // Called after the test execution finishes
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush(); // Ensure all the reports are written out to the file
        }
    }
}

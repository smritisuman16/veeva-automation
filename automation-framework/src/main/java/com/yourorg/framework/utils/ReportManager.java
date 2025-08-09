package com.yourorg.framework.utils;

import com.yourorg.framework.driver.DriverFactory;
import com.yourorg.framework.reporting.ExtentManager;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ReportManager {

    private static Scenario scenario;

    public static void setScenario(Scenario sc) {
        scenario = sc;
    }

    public static void logWithScreenshot(String message) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null && scenario != null) {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            // Attach to cucumber report
            scenario.attach(screenshotBytes, "image/png", message);

            // Attach to extent report
            ExtentManager.getTest().info(message)
                    .addScreenCaptureFromBase64String(base64Screenshot, message);
        } else {
            ExtentManager.getTest().info(message + " (No screenshot, driver or scenario is null)");
        }
    }
}

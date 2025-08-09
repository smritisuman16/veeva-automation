package com.yourorg.framework.hooks;

import com.aventstack.extentreports.Status;
import com.yourorg.framework.driver.DriverFactory;
import com.yourorg.framework.reporting.ExtentManager;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ReportingHooks {

    private WebDriver driver; // Inject via dependency injection if using PicoContainer/Guice

    public ReportingHooks() {
        // You can get driver instance from your driver manager
        this.driver = DriverFactory.getDriver();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentManager.startTest(scenario.getName(), "Scenario Execution");
        ExtentManager.getTest().log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            // Screenshot for failed step
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");

            // Attach screenshot to ExtentReport
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            ExtentManager.getTest().addScreenCaptureFromBase64String(base64Screenshot);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentManager.getTest().log(Status.FAIL, "Scenario failed: " + scenario.getName());
        } else {
            ExtentManager.getTest().log(Status.PASS, "Scenario passed: " + scenario.getName());
        }
        ExtentManager.endTest();
    }
}

package com.yourorg.framework.hooks;
import com.yourorg.framework.driver.DriverFactory;
import com.yourorg.framework.config.ConfigReader;
import com.yourorg.framework.reporting.ExtentManager;
import com.yourorg.framework.utils.ReportManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class CucumberHooks {
    @Before
    public void beforeScenario(Scenario scenario){
        String browser = System.getProperty("browser", ConfigReader.get("browser"));
        DriverFactory.initDriver(browser);
        ReportManager.setScenario(scenario);
        ExtentManager.startTest(scenario.getName(), "");
    }

    @After
    public void afterScenario(io.cucumber.java.Scenario scenario){
        DriverFactory.quitDriver();
    }
}

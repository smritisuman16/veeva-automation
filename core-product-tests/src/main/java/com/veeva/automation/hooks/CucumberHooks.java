package com.veeva.automation.hooks;
import com.veeva.automation.driver.DriverFactory;
import com.veeva.automation.config.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class CucumberHooks {
    @Before
    public void beforeScenario(){
        String browser = System.getProperty("browser", ConfigReader.get("browser"));
        DriverFactory.initDriver(browser);
    }

    @After
    public void afterScenario(io.cucumber.java.Scenario scenario){
        // Add screenshot capture / report attach on failure here
        DriverFactory.quitDriver();
    }
}

package com.yourorg.framework.hooks;
import com.yourorg.framework.driver.DriverFactory;
import com.yourorg.framework.config.ConfigReader;
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

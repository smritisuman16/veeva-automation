package com.yourorg.cp.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:features",
        glue = {
                "com.yourorg.framework.hooks",
                "com.yourorg.cp.steps"
        },
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-html-report.html"
        },
        tags=""


)
public class CPTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

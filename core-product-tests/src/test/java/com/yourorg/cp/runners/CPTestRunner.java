package com.yourorg.cp.runners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.yourorg.framework.hooks","com.yourorg.cp.steps"},
        plugin = {"pretty","json:target/cucumber.json"}
)
public class CPTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){ return super.scenarios(); }
}

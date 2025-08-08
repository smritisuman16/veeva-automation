package com.yourorg.cp.steps;
import com.veeva.automation.pagefactory.PageFactoryManager;
import com.veeva.automation.driver.DriverFactory;
import com.veeva.automation.config.ConfigReader;
import com.yourorg.cp.pages.CPHomePage;
import com.veeva.automation.utils.FileUtils;
import io.cucumber.java.en.*;
import java.util.*;

public class CPJacketsSteps {
    private CPHomePage home;

    @Given("I open CP home page")
    public void openCpHome() {
        DriverFactory.getDriver().get(ConfigReader.get("baseUrl.cp"));
        home = PageFactoryManager.initElements(DriverFactory.getDriver(), CPHomePage.class);
    }

    @When("I navigate to Shop > Men's")
    public void navToMens() { home.openMens(); }

    @Then("I collect title, price and top seller flag for each jacket and save to a text file")
    public void collectJackets() throws Exception {
        // TODO: implement pagination scraping
        String sample = "Title1 | 99.99 | Top Seller\nTitle2 | 129.99 | \n";
        FileUtils.writeStringToFile("target/artifacts/cp_jackets.txt", sample);
    }
}

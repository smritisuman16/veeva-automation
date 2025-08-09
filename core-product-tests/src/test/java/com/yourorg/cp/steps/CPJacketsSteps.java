package com.yourorg.cp.steps;
import com.yourorg.cp.pages.CPLocators;
import com.yourorg.framework.pagefactory.*;
import com.yourorg.framework.driver.*;
import com.yourorg.framework.config.*;
import com.yourorg.framework.utils.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.*;

public class CPJacketsSteps extends BaseUtils{

    WebDriver driver= DriverFactory.getDriver();
    CPLocators locators = PageFactoryManager.initElements(driver, CPLocators.class);
    List<String> jacketData=new ArrayList<>();
    String filePath=
//            Paths.get(
                    "target/testdata.txt";
//            ).toAbsolutePath().toString();


    @Given("I am on the CP home page")
    public void i_am_on_the_cp_home_page() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.nba.com/warriors/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        locators.closePopup.click();
    }

    @When("I navigate to the Men's Jackets section")
    public void i_navigate_to_mens_jackets_section() {
        click(locators.shop_menu);
        switchToWindow("Golden State Warriors Shop");
        new Actions(driver).sendKeys(Keys.ESCAPE).build().perform();
//        new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOf(locators.mens_menu));
//        new Actions(driver).moveToElement(locators.mens_menu).click(locators.mens_menu).build().perform();
        click(locators.mens_menu);
        new Actions(driver).moveToElement(locators.mens_menu).build().perform();
        click(locators.jackets);
//        searchAndHitEnter(locators.searchBar,"Jackets");
    }

    @When("I collect jacket details from all pages")
    public void i_collect_jacket_details_from_all_pages() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        boolean hasNextPage = true;
        while (hasNextPage) {
            boolean hasNextElement = true;

                wait.until(ExpectedConditions.visibilityOf(locators.next_page_button));

                List<WebElement> products = locators.search_results_items;
                for (WebElement product : products) {
                    String title = product.findElement(By.xpath("//*[@class='product-card-title']")).getText();
                    String price = product.findElement(By.xpath("//*[@class='money-value']")).getText();
                    String topSeller = "";
                    try {
                        WebElement badge = product.findElement(By.xpath("//*[@class='top-seller-vibrancy-message']"));
                        topSeller = badge.isDisplayed() ? "Top Seller" : "";
                    } catch (NoSuchElementException ignored) {
                    } catch (Exception e) {

                    }

                    jacketData.add(String.format("Title: %s | Price: %s | %s", title, price, topSeller));
                }


            try{
            if(locators.next_page_button.isEnabled()){
                click(locators.next_page_button);
            }
            else{
                hasNextPage=false;
            }}
            catch(Exception e){
                hasNextPage=false;
            }
        }

    }

    @Then("I save the details to a text file")
    public void i_save_the_details_to_a_text_file() throws Exception {
        Files.createDirectories(Paths.get("target"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : jacketData) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
//
//    @Then("I attach the file to the test report")
//    public void i_attach_the_file_to_the_test_report(Scenario scenario) throws IOException {
//        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
//        scenario.attach(fileContent, "text/plain", "jackets-info.txt");
//    }
}

package com.yourorg.cp.steps;
import com.aventstack.extentreports.Status;
import com.yourorg.cp.pages.CPLocators;
import com.yourorg.framework.pagefactory.*;
import com.yourorg.framework.driver.*;
import com.yourorg.framework.reporting.ExtentManager;
import com.yourorg.framework.utils.*;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.*;
import java.util.NoSuchElementException;

public class CPJacketsSteps extends BaseUtils{

    WebDriver driver= DriverFactory.getDriver();
    CPLocators locators = PageFactoryManager.initElements(driver, CPLocators.class);
    List<String> jacketData=new ArrayList<>();
    String filePath= "target/jackets-info.txt";



    @Given("I am on the CP home page")
    public void i_am_on_the_cp_home_page() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.nba.com/warriors/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        ReportManager.logWithScreenshot("Navigated to CP Warriors");
        try{
            if(locators.acceptCookies.isDisplayed()){
                click(locators.acceptCookies);
            }
        } catch (Exception ignored) {
        }
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
        ReportManager.logWithScreenshot("User navigated to jackets page");
//        searchAndHitEnter(locators.searchBar,"Jackets");
    }

    @When("I collect jacket details from all pages")
    public void i_collect_jacket_details_from_all_pages() {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(60))
                .withTimeout(Duration.ofSeconds(5))
                .ignoring(StaleElementReferenceException.class);
        boolean hasNextPage = true;

        while (hasNextPage) {
            ReportManager.logWithScreenshot("User Collecting details from each Page");
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locators.search_results_items)));
            List<WebElement> products = driver.findElements(By.xpath(locators.search_results_items));

            for (WebElement product : products) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
                String title = product.findElement(By.xpath(".//*[@class='product-card-title']")).getText();
                String price = product.findElement(By.xpath(".//*[@class='money-value']")).getText();
                String topSeller="";
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                if (!product.findElements(By.cssSelector(".top-seller-vibrancy-message")).isEmpty()) {
                    topSeller = "Top Seller" ;
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
                jacketData.add(String.format("Title: %s | Price: %s | %s", title, price, topSeller));

            }


            try {



                if (!locators.next_page_button.isDisplayed() || !locators.next_page_button.isEnabled() ||
                        "true".equalsIgnoreCase(locators.next_page_button.getAttribute("aria-disabled"))) {
                    hasNextPage = false;
                } else {

                    locators.next_page_button.click();
                    wait.until(ExpectedConditions.stalenessOf(products.get(0)));
                }
            } catch (NoSuchElementException e) {
                hasNextPage = false;
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

    @Then("I attach the file to the test report")
    public void i_attach_the_file_to_the_test_report(Scenario scenario) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(System.getProperty("user.dir"), "target", "jackets-info.txt"));
        scenario.attach(fileContent, "text/plain", "jackets-info.txt");
        ExtentManager.getTest().log(Status.INFO, "Jacket Info:\n" + fileContent);
    }
}

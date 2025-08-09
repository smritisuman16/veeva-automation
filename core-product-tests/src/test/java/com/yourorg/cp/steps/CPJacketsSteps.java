package com.yourorg.cp.steps;
import com.yourorg.framework.pagefactory.*;
import com.yourorg.framework.driver.*;
import com.yourorg.framework.config.*;
import com.yourorg.cp.pages.CPHomePage;
import com.yourorg.framework.utils.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

public class CPJacketsSteps {
    private CPHomePage home;
    WebDriver driver=DriverFactory.getDriver();

    @Given("I am on the CP home page")
    public void i_am_on_the_cp_home_page() {
        driver = DriverFactory.getDriver();
        driver.get("https://your-cp-home-url.com");
    }

    @When("I navigate to the Men's Jackets section")
    public void i_navigate_to_mens_jackets_section() {
        driver.findElement(By.linkText("Shop Menu")).click();
        driver.findElement(By.linkText("Men’s")).click();
        driver.findElement(By.linkText("Jackets")).click();
    }

    @When("I collect jacket details from all pages")
    public void i_collect_jacket_details_from_all_pages() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        boolean hasNextPage = true;
        while (hasNextPage) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-item")));

            List<WebElement> products = driver.findElements(By.cssSelector(".product-item"));
            for (WebElement product : products) {
                String title = product.findElement(By.cssSelector(".product-title")).getText();
                String price = product.findElement(By.cssSelector(".price")).getText();
                String topSeller = "";
                try {
                    WebElement badge = product.findElement(By.cssSelector(".top-seller"));
                    topSeller = badge.isDisplayed() ? "Top Seller" : "";
                } catch (NoSuchElementException ignored) {}

                jacketData.add(String.format("Title: %s | Price: %s | %s", title, price, topSeller));
            }

            try {
                WebElement nextBtn = driver.findElement(By.cssSelector(".pagination .next"));
                if (nextBtn.isEnabled()) {
                    nextBtn.click();
                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                hasNextPage = false;
            }
        }
    }

    @Then("I save the details to a text file")
    public void i_save_the_details_to_a_text_file() throws IOException {
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
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        scenario.attach(fileContent, "text/plain", "jackets-info.txt");
    }
}

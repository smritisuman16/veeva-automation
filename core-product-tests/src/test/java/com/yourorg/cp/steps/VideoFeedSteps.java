package com.yourorg.cp.steps;

import com.yourorg.cp.pages.CPLocators;
import com.yourorg.framework.driver.DriverFactory;
import com.yourorg.framework.pagefactory.PageFactoryManager;
import com.yourorg.framework.utils.BaseUtils;
import com.yourorg.framework.utils.ReportManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class VideoFeedSteps extends BaseUtils {

    private WebDriver driver= DriverFactory.getDriver();
    CPLocators locators = PageFactoryManager.initElements(driver, CPLocators.class);



    @When("I hover over the menu item and click on New & Featured")
    public void i_hover_over_menu_item_and_click_new_featured() {

        Actions actions = new Actions(driver);
        actions.moveToElement(locators.menu_item).perform();
        ReportManager.logWithScreenshot("User clicks on News & Features");
        click(locators.news_and_features);
    }

    @Then("I count total number of video feeds")
    public void i_count_total_number_of_video_feeds() {
        System.out.println("Total Video Feeds: " + locators.videoFeeds.size());
        ReportManager.logWithScreenshot("Total Video Feeds: "+locators.videoFeeds.size());
    }

    @Then("I count video feeds older than or equal to 3 days")
    public void i_count_video_feeds_older_than_equal_3_days() {

        int countOld = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);
        LocalDate today = LocalDate.now();

        for (WebElement videoFeed : locators.videoFeeds) {
            String dateText = videoFeed.findElement(By.xpath(".//time")).getAttribute("datetime");
            LocalDate feedDate = LocalDate.parse(dateText, formatter);
            if (ChronoUnit.DAYS.between(feedDate, today) >= 3) {
                countOld++;
            }
        }

        System.out.println("Video Feeds older than or equal to 3 days: " + countOld);
        ReportManager.logWithScreenshot("Video Feeds older than or equal to 3 days: " + countOld);
    }
}

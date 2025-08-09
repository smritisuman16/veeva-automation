package com.yourorg.framework.utils;

import com.yourorg.framework.driver.DriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class BaseUtils {
    WebDriver driver= DriverFactory.getDriver();
    public void click(WebElement button){
        try{
        new WebDriverWait(driver, Duration.ofSeconds(60)).
                until(ExpectedConditions.elementToBeClickable(button)).click();}
        catch(Exception e){
            try{
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", button);
            } catch (Exception ex) {
                log.error("Not able to click on the button");
            }

        }
    }
    public void switchToWindow(String windowTitle) {
        try {

            new WebDriverWait(driver, Duration.ofSeconds(60))
                    .until(driver -> driver.getWindowHandles().size() > 1);

            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().contains(windowTitle)) {
                    log.info("Switched to window with title: {}", windowTitle);
//                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
                    return;
                }
            }

            log.warn("No window found with title: {}", windowTitle);

        } catch (Exception e) {
            log.error("Unable to switch to window with title: {}", windowTitle, e);
        }
    }
    public void searchAndHitEnter(WebElement searchBar, String text) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(120))
                    .until(ExpectedConditions.visibilityOf(searchBar));

            element.clear();
            element.sendKeys(text, Keys.ENTER);

            log.info("Entered '{}' in the search bar and pressed Enter", text);
        } catch (Exception e) {
            log.error("Not able to enter '{}' in the search bar and press Enter", text, e);
        }
    }


}

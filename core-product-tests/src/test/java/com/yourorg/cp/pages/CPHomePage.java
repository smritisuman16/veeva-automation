package com.yourorg.cp.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.veeva.automation.driver.DriverFactory;

public class CPHomePage {
    @FindBy(css = "nav .shop") private WebElement shopMenu;
    @FindBy(css = "a[href*='mens']") private WebElement mensLink;

    public void openMens() {
        new org.openqa.selenium.interactions.Actions(DriverFactory.getDriver()).moveToElement(shopMenu).perform();
        mensLink.click();
    }
}

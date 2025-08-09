package com.yourorg.cp.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.yourorg.framework.driver.*;

import java.util.List;

public class CPLocators {
    @FindBy(xpath = "//div[text()='x']")
    public WebElement closePopup;
    @FindBy(xpath = "//*[text()='Shop']/parent::a")
    public WebElement shop_menu;
    @FindBy(xpath = "//a[text()='Men']")
    public WebElement mens_menu;
    @FindBy(xpath = "//div[@class='search-container']//input[@type='text' and contains(@placeholder,'What can we help you find?')]")
    public WebElement searchBar;
    @FindBy(xpath = "//div[@class='columns small-12 medium-12'][.//div[@class='product-card-title']]")
    public List<WebElement> search_results_items;
    @FindBy (xpath="(//a[@*='next page'])[1]")
    public WebElement next_page_button;
    @FindBy(xpath = "(//div[text()='Jackets']/parent::a)[1]")
    public WebElement jackets;





}

package com.yourorg.cp.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.yourorg.framework.driver.*;
import org.openqa.selenium.support.FindBys;

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
    public String search_results_items = "//div[@class='columns small-12 medium-12'][.//div[@class='product-card-title']]";
    @FindBy (xpath="(//a[@*='next page'])[1]")
    public WebElement next_page_button;
    @FindBy(xpath = "(//div[text()='Jackets']/parent::a)[1]")
    public WebElement jackets;
    @FindBy(xpath="//li[@class='menu-item']/a[.//span[text()='...']]")
    public WebElement menu_item;
    @FindBy (xpath="//li[@*='menuitem']/a[text()='News & Features']")
    public WebElement news_and_features;
    @FindBy(xpath="//*[@class='brand-font tile-article-link-wrapper TileArticle_tileArticleLinkWrapper__5XFdV']")
    public List<WebElement> videoFeeds;





}

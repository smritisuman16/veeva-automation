package com.yourorg.framework.pagefactory;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

public class PageFactoryManager {
    public static <T> T initElements(WebDriver driver, Class<T> pageClass) {
        try {
            T page = pageClass.getDeclaredConstructor().newInstance();
            PageFactory.initElements(driver, page);
            return page;
        } catch(Exception e){ throw new RuntimeException(e); }
    }
}

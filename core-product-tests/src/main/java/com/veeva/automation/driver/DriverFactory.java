package com.veeva.automation.driver;
import com.veeva.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class DriverFactory {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static WebDriver getDriver(){ return tlDriver.get(); }

    public static void initDriver(String browser) {
        if (browser == null) browser = ConfigReader.get("browser");
        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opts = new ChromeOptions();
            if(ConfigReader.get("headless").equalsIgnoreCase("true")) opts.addArguments("--headless=new");
            tlDriver.set(new ChromeDriver(opts));
        } else {
            // add firefox logic if needed
        }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        getDriver().manage().window().maximize();
    }

    public static void quitDriver(){
        if(getDriver()!=null){ getDriver().quit(); tlDriver.remove(); }
    }
}

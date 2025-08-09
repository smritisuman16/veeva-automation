package com.yourorg.framework.driver;

import com.yourorg.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void initDriver(String browser) {
        if (browser == null || browser.trim().isEmpty()) {
            browser = ConfigReader.get("browser"); // fallback
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                if ("true".equalsIgnoreCase(ConfigReader.get("headless"))) {
                    chromeOptions.addArguments("--headless=new");
                }
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if ("true".equalsIgnoreCase(ConfigReader.get("headless"))) {
                    ffOptions.addArguments("--headless");
                }

                tlDriver.set(new FirefoxDriver(ffOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if ("true".equalsIgnoreCase(ConfigReader.get("headless"))) {
                    edgeOptions.addArguments("--headless=new");
                }
                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        getDriver().manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getInt("implicitWait"))
        );
        getDriver().manage().window().maximize();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}

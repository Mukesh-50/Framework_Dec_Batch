package factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import dataproviders.ConfigReader;

public class BrowserFactory {

    static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver startBrowser(String browserName, String applicationURL) {

        System.out.println("Application will be running on " + browserName + " with url " + applicationURL);

        String isCloud = ConfigReader.getValue("cloud");
        String huburl = ConfigReader.getValue("huburl") + ":" + ConfigReader.getValue("hubport") + "/wd/hub";

        // Flag for GitHub Actions
        String githubFlag = ConfigReader.getValue("githuaction");
        boolean isHeadless = githubFlag != null && githubFlag.equalsIgnoreCase("true");

        if (isCloud.equalsIgnoreCase("true")) {

            System.out.println("**** Running Test On Selenium Grid ****");

            if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("gc")
                    || browserName.equalsIgnoreCase("google")) {

                ChromeOptions opt = new ChromeOptions();

                if (isHeadless) {
                    opt.addArguments("--headless=new");
                    opt.addArguments("--no-sandbox");
                    opt.addArguments("--disable-dev-shm-usage");
                    opt.addArguments("--window-size=1920,1080");
                    opt.addArguments("--high-dpi-support=1");
                    opt.addArguments("--force-device-scale-factor=1");
                }

                try {
                    driver = new RemoteWebDriver(new URL(huburl), opt);

                } catch (MalformedURLException e) {
                    System.out.println("Could not connect to Grid");
                }

            } else if (browserName.equalsIgnoreCase("firefox") || browserName.equalsIgnoreCase("mozila")
                    || browserName.equalsIgnoreCase("ff")) {

                driver = new FirefoxDriver();

            } else if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("msedge")
                    || browserName.equalsIgnoreCase("microsoft edge")) {

                driver = new EdgeDriver();

            } else {
                System.out.println("Sorry - currently our framework does not support " + browserName + " browser");
            }

        } else {

            System.out.println("**** Running Test On Local Machine ****");

            if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("gc")
                    || browserName.equalsIgnoreCase("google")) {

                if (isHeadless) {
                    ChromeOptions opt = new ChromeOptions();
                    opt.addArguments("--headless=new");
                    opt.addArguments("--no-sandbox");
                    opt.addArguments("--disable-dev-shm-usage");
                    opt.addArguments("--window-size=1920,1080");
                    opt.addArguments("--high-dpi-support=1");
                    opt.addArguments("--force-device-scale-factor=1");

                    driver = new ChromeDriver(opt);
                } else {
                    driver = new ChromeDriver();
                }

            } else if (browserName.equalsIgnoreCase("firefox") || browserName.equalsIgnoreCase("mozila")
                    || browserName.equalsIgnoreCase("ff")) {

                driver = new FirefoxDriver();

            } else if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("msedge")
                    || browserName.equalsIgnoreCase("microsoft edge")) {

                driver = new EdgeDriver();

            } else if (browserName.equalsIgnoreCase("safari") || browserName.equalsIgnoreCase("apple safari")) {

                driver = new SafariDriver();

            } else {
                System.out.println("Sorry - currently our framework does not support " + browserName + " browser");
            }
        }

        // 👇 IMPORTANT PART
        if (isHeadless) {
            // maximize() headless me kaam nahi karta, isliye size hard-set karo
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getValue("pageloadtimeout"))));

        driver.get(applicationURL);

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getValue("implicitwait"))));

        driver.manage().timeouts().scriptTimeout(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getValue("scripttimeout"))));

        System.out.println("Application is up and running");

        return driver;
    }

}

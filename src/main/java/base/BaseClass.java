package base;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import dataproviders.ConfigReader;
import factory.BrowserFactory;

public class BaseClass {

    protected WebDriver driver;

    @BeforeClass
    public void setup() 
    {
        Reporter.log("**** Running Before Class", true);

        // 1. Browser from Jenkins (fallback -> config.properties)
        String browserName = System.getProperty("browser");
        if (browserName == null || browserName.isEmpty()) {
            browserName = ConfigReader.getValue("browser");   // default
        }

        // 2. Env from Jenkins (dev/qa/stage/prod etc.)
        String env = System.getProperty("env");
        if (env == null || env.isEmpty()) {
            env = "qa";   // default env
        }

        // 3. Env ke hisaab se URL pick karo from config.properties
        String urlKey;
        switch (env.toLowerCase()) {
            case "dev":
                urlKey = "devURL";
                break;
            case "stage":
            case "stg":
                urlKey = "stageURL";
                break;
            case "prod":
                urlKey = "prodURL";
                break;
            case "qa":
            default:
                urlKey = "qaURL";
                break;
        }

        String url = ConfigReader.getValue(urlKey);

        System.out.println("Running on browser: " + browserName + " | env: " + env + " | url: " + url);

        driver = BrowserFactory.startBrowser(browserName, url);

        System.out.println("**** Application is up and running");
    }

    @AfterClass
    public void teardown() 
    {
        System.out.println("**** Running After Class ");

        if (driver != null) {
            driver.quit();
        }

        System.out.println("**** Closing the application ");
    }
}

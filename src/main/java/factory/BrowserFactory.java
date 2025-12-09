package factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import dataproviders.ConfigReader;

public class BrowserFactory 
{
	
	static WebDriver driver;
	
	public static WebDriver getDriver()
	{
		return driver;
	}
	

	public static WebDriver startBrowser(String browserName, String applicationURL) 
	{

		System.out.println("Application will be running on " + browserName + " with url " + applicationURL);
		
		String isCloud = ConfigReader.getValue("cloud");
		String huburl = ConfigReader.getValue("huburl") + ":" + ConfigReader.getValue("hubport") + "/wd/hub";

		// NEW: Flag for GitHub Actions
		String isGithubAction = ConfigReader.getValue("githuaction");
		
		
		if(isCloud.equalsIgnoreCase("true"))
		{
			
			System.out.println("**** Running Test On Selenium Grid ****");
			
			if (browserName.toLowerCase().equals("chrome") || browserName.toLowerCase().equals("gc")
					|| browserName.toLowerCase().equals("google")) 
			{
				ChromeOptions opt = new ChromeOptions();
				
				// Use headless in GitHub Actions
				if(isGithubAction != null && isGithubAction.equalsIgnoreCase("true"))
				{
					opt.addArguments("--headless");
					opt.addArguments("--no-sandbox");
					opt.addArguments("--disable-dev-shm-usage");
				}
				
				try 
				{
					driver = new RemoteWebDriver(new URL(huburl), opt);
					
				} 
				catch (MalformedURLException e) 
				{
					System.out.println("Could not connect to Grid");
				}
				
			} 
			else if (browserName.toLowerCase().equals("firefox") || browserName.toLowerCase().equals("mozila")
					|| browserName.toLowerCase().equals("ff")) 
			{
				driver = new FirefoxDriver();
			
			} 
			else if (browserName.toLowerCase().equals("edge") || browserName.toLowerCase().equals("msedge")
					|| browserName.toLowerCase().equals("microsoft edge")) 
			{
				driver = new EdgeDriver();
			}  
			else 
			{
				System.out.println("Sorry - currently our framework does not support " + browserName + " browser");
			}
			
			
		}
		else
		{
			
			System.out.println("**** Running Test On Local Machine ****");
			
			if (browserName.toLowerCase().equals("chrome") || browserName.toLowerCase().equals("gc")
					|| browserName.toLowerCase().equals("google")) 
			{
				// If running in GitHub Actions -> headless
				if(isGithubAction != null && isGithubAction.equalsIgnoreCase("true"))
				{
					ChromeOptions opt = new ChromeOptions();
					opt.addArguments("--headless");
					opt.addArguments("--no-sandbox");
					opt.addArguments("--disable-dev-shm-usage");
					opt.addArguments("--window-size=1920,1080");
					driver = new ChromeDriver(opt);
				}
				else
				{
					// Normal local run
					driver = new ChromeDriver();
				}
			} 
			else if (browserName.toLowerCase().equals("firefox") || browserName.toLowerCase().equals("mozila")
					|| browserName.toLowerCase().equals("ff")) 
			{
				driver = new FirefoxDriver();
			
			} 
			else if (browserName.toLowerCase().equals("edge") || browserName.toLowerCase().equals("msedge")
					|| browserName.toLowerCase().equals("microsoft edge")) 
			{
				driver = new EdgeDriver();
			} 
			else if (browserName.toLowerCase().equals("safari") || browserName.toLowerCase().equals("apple safari")) 
			{
				driver = new SafariDriver();
				
			} 
			else 
			{
				System.out.println("Sorry - currently our framework does not support " + browserName + " browser");
			}

		}
		
		driver.manage().window().maximize();
		
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

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helper.Utility;

public class DashboardPage 
{
	WebDriver driver;
	
	public DashboardPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	private By welcomeMsg=By.xpath("//h4[@class='welcomeMessage']");
	
	private By menu=By.xpath("//img[@alt='menu']");
	
	private By signOut=By.xpath("//button[normalize-space()='Sign out']");
	
	public String getWelcomeMessage()
	{
		return Utility.highLightElement(driver, driver.findElement(welcomeMsg)).getText();
	}
	
	public void logoutFromApplication()
	{
		Utility.highLightElement(driver, driver.findElement(menu)).click();
		
		Utility.highLightElement(driver, driver.findElement(signOut)).click();
		
	}
	
	
	
}

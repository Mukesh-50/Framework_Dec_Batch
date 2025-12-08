package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helper.Utility;

public class LoginPage 
{
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	private By username=By.id("email1");

	private By password=By.id("password1");

	private By signInButton=By.className("submit-btn");
	
	private By signInHeader=By.xpath("//h2[normalize-space()='Sign In']");
	
	public void loginToApplication(String uname,String pass)
	{
				
		Utility.highLightElement(driver, driver.findElement(username)).sendKeys(uname);
	
		Utility.highLightElement(driver, driver.findElement(password)).sendKeys(pass);

		driver.findElement(signInButton).click();
	}

	public boolean isSignInHeaderPresent()
	{	
		return Utility.highLightElement(driver, driver.findElement(signInHeader)).isDisplayed();
	}
	

}

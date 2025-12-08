package helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import dataproviders.ConfigReader;
import factory.BrowserFactory;

public class Utility {

	public static WebElement highLightElement(WebDriver driver, WebElement element) 
	{
		

		if(ConfigReader.getValue("Highlight").equalsIgnoreCase("true"))
		{
		
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e) 
			{

				System.out.println(e.getMessage());
			}

			js.executeScript("arguments[0].setAttribute('style','border: solid 2px ');", element);
			
			return element;
		}
		else
		{
			return element;
		}
		
	
		
	}

	

	public static String captureScreenshot() {

		WebDriver driver = BrowserFactory.getDriver();

		TakesScreenshot ts = (TakesScreenshot) driver;

		String str = ts.getScreenshotAs(OutputType.BASE64);

		return str;

	}

	public static Object captureScreenshot(String type) {
		Object result = null;

		WebDriver driver = BrowserFactory.getDriver();

		TakesScreenshot ts = (TakesScreenshot) driver;

		if (type.equalsIgnoreCase("file")) {
			result = ts.getScreenshotAs(OutputType.FILE);
		} else if (type.equalsIgnoreCase("base64")) {
			result = ts.getScreenshotAs(OutputType.BASE64);
		} else if (type.equalsIgnoreCase("byte")) {
			result = ts.getScreenshotAs(OutputType.BYTES);
		}

		return result;

	}

}

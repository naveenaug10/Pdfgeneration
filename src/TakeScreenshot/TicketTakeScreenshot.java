package TakeScreenshot;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TicketTakeScreenshot {

	@Test
	public void testGuru99TakeScreenShot() throws Exception{
		 System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
		 WebDriver driver=new ChromeDriver();
		//goto url
		 driver.manage().window().maximize();
		 driver.get("http://newtours.demoaut.com");
		 driver.findElement(By.name("userName")).sendKeys("sepusers12");
		  driver.findElement(By.name("password")).sendKeys("test@123");
		  driver.findElement(By.name("login")).click();
		//Call take screenshot function
		TicketTakeScreenshot.takeSnapShot(driver, "D://test.png");
		
	}
	
	
	/**
	 * This function will take screenshot
	 * @param webdriver
	 * @param fileWithPath
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		//Call getScreenshotAs method to create image file
				File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			//Move image file to new destination 
				File DestFile=new File(fileWithPath);
				//Copy file at destination
				FileUtils.copyFile(SrcFile, DestFile);
			
	}

}

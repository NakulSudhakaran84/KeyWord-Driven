package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

	public WebDriver driver;
	public Properties prop;
	
	
	public WebDriver init_Driver(String browserName)
	{
		if(browserName.equals("firefox")) {
			
			System.setProperty("webdriver.gecko.driver", "E:\\Q Spiders\\softwares\\Firefox driver\\geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "browser log");
			if(prop.getProperty("headless").equals("yes")) {
				ChromeOptions options=new ChromeOptions();
				options.addArguments("--headless");
				driver=new ChromeDriver(options);
			}
			else {
				driver=new FirefoxDriver();
			}
		}
		return driver;
	}
	
	public Properties init_properties()
	{
		prop=new Properties();
		try {
			FileInputStream ip=new FileInputStream("C:\\Users\\Nakul-PC\\git\\KeyWordDriven\\keyword\\src\\main\\java\\config\\config.properties");
			
				prop.load(ip);
				
		}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return prop;
		
	}
	
	
}

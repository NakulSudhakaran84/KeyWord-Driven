package keywordengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Base;

public class KeyWordEngine {
	
	public WebDriver driver;
	public Properties prop;
	public static Workbook book;
	public static Sheet sheet;
	public WebElement element;
	
	public Base base;
	
	
	public final  String XL_SHEET_PATH="./input_keywords/logindata.xlsx";
	
	public void startExecution(String sheetName) throws InterruptedException
	{
		String locatorName=null;
		String locatorValue=null;
		
		FileInputStream file=null;
		try {
			file=new FileInputStream(XL_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			book=WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet=book.getSheet(sheetName);
		
		int k=0;
		for(int i=0;i<sheet.getLastRowNum();i++)
		{
			String locatorColumValue=String.valueOf((sheet.getRow(i+1).getCell(k+1))).trim();//id=username
			if(!locatorColumValue.equalsIgnoreCase("NA")) 
			{
				locatorName=locatorColumValue.split(":")[0].trim();//id
				locatorValue=locatorColumValue.split(":")[1].trim();//username
			}
			else {
				locatorName="NA";}
			String action=String.valueOf((sheet.getRow(i+1).getCell(k+2))).trim();
			String value=String.valueOf((sheet.getRow(i+1).getCell(k+3))).trim();
			base=new Base();
			prop=base.init_properties();
			switch (action) {
			case "open browser":
					
					if(value.isEmpty()||value.equals("NA")) {
						driver=base.init_Driver(prop.getProperty("browser"));
					}else {
						driver=base.init_Driver(value);
					}		
					value=null;
				break;
				
			case "enter url":
				if(value.isEmpty()||value.equals("NA")) {
					driver.get(prop.getProperty("url"));
				}else {
					driver.get(value);
					Thread.sleep(5000);
				}
				value=null;
				break;
				
			case "quit"	:
				driver.quit();
				break;
			default:
				break;
			}
			
			switch (locatorName) {
			case "id":
					element=driver.findElement(By.id(locatorValue));
					if(action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					}
					locatorName=null;
				break;
				
			case "name":
					element=driver.findElement(By.name(locatorValue));
					if(action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					}
					locatorName=null;
					break;
			case "xpath":	
					element=driver.findElement(By.xpath(locatorValue));
					if(action.equalsIgnoreCase("click")) {
						element.click();
					}else if(action.equalsIgnoreCase("capture logo")) {
						File srcfile=element.getScreenshotAs(OutputType.FILE);
						try {
							FileUtils.copyFile(srcfile, new File("logo.png"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						locatorName=null;
						break;
						
			case "css selector":
					element=driver.findElement(By.cssSelector(locatorValue));
					element.click();
					locatorValue=null;
					break;
			default:
				break;
			}
	}
  }
}

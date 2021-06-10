package com.crm.comcast.VtigerTest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.comcast.GenericUtils.ExcelUtility;
import com.crm.comcast.GenericUtils.JavaUtility;
import com.crm.comcast.GenericUtils.PropertyFileUtility;
import com.crm.comcast.GenericUtils.WebDriverUtility;

public class CreatemultipleOrganization {
	
	WebDriver driver;
	PropertyFileUtility pLib = new PropertyFileUtility();
	JavaUtility jLib = new JavaUtility();
	WebDriverUtility wLib = new WebDriverUtility();
	ExcelUtility eLib = new ExcelUtility();
	
	@DataProvider
    public Object[][] getData() throws Throwable
    {
		return eLib.getExcelData("sheet3");
    }
	
	@Test(dataProvider = "getData")
	public void createMulipleOrg(String OrgName, String IndustryType) throws Throwable
	{
	
	String URL = pLib.readDataFromPropertyFile("url");
    String USERNAME = pLib.readDataFromPropertyFile("username");
    String PASSWORD = pLib.readDataFromPropertyFile("password");
    String BROWSER = pLib.readDataFromPropertyFile("browser");
    
    //launch browser
    if(BROWSER.equals("chrome")){
    	driver = new ChromeDriver();
    }else if(BROWSER.equals("firefox")){
    	driver = new FirefoxDriver();
    }else {
    	driver = new InternetExplorerDriver();
    }
    
    //navigate to the url
    wLib.waitForPageToLoad(driver);
    driver.get(URL);
    wLib.maximiseWin(driver);
    
    //login to the application
    driver.findElement(By.name("user_name")).sendKeys(USERNAME);
    driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
    driver.findElement(By.id("submitButton")).click();
    
    //navigate to organizations
    driver.findElement(By.linkText("Organizations")).click();
    
    //navigate to create organization
    driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
    
    //enter mandatory fields and create organization
    driver.findElement(By.name("accountname")).sendKeys(OrgName);
    driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
    
    //select industry dropdrown
    WebElement element = driver.findElement(By.name("industry"));
	wLib.select(element, IndustryType);

    //validate
    String successMsg = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
    Assert.assertTrue(successMsg.contains(OrgName));
    System.out.println(successMsg);

}
}

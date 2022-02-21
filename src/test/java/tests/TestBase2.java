package tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import data.loadProperties;
import utilities.Helper;

public class TestBase2 {
	
	public static final String USERNAME=loadProperties.sauceLabsData.getProperty("username");	
	public static final String ACCESS_KEY=loadProperties.sauceLabsData.getProperty("accesskey");
	public static final String SAUCEURL="https://"+USERNAME+":"+ACCESS_KEY
			+loadProperties.sauceLabsData.getProperty("selenuimURL");
	
	public static String BaseURL="https://demo.nopcommerce.com/";
	protected ThreadLocal<RemoteWebDriver> driver = null;

	@org.testng.annotations.BeforeClass
	@Parameters(value={"browser"})
	public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
		driver =new ThreadLocal<>();		
		DesiredCapabilities caps =new DesiredCapabilities();
		caps.setCapability("browserName", browser);
		//driver.set(new RemoteWebDriver(new URL(SAUCEURL), caps));
		driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
		getDriver().navigate().to(BaseURL);
	}
	public WebDriver getDriver() {
		return driver.get();}
	
	@org.testng.annotations.AfterClass
	public void stopDriver() {
		getDriver().quit();
		driver.remove();
	}
	@AfterMethod
	public void screenshotOnFailure(ITestResult result) 
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			System.out.println("Failed!");
			System.out.println("Taking Screenshot....");
			Helper.capturescreenshot(getDriver(), result.getName());
		}
	}
	
}
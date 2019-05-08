package br.com.testfilipe.core.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class Browser {
	
	final static Logger logger = Logger.getLogger(Browser.class);

	final static String FIREFOX = "firefox";
	final static String CHROME = "chrome";

	
	
	private static WebDriver webDriver = null;
	public static String downloadDir = "./evidences/downloads";
	
	public static WebDriver seleniumStarted(){
		return webDriver ;
	}
	
	public static byte[] screenshot() throws Exception {
		byte[] pictureData = null ;
		if (webDriver.toString().toLowerCase().contains("htmlunit")){
			logger.warn("Screenshot not available in HtmlUnit");
		} else {
			pictureData = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BYTES);
		}
		
		return pictureData;
	}
	/**
	 * Start Browser, not valid to Appium
	 * @param browserType
	 * @param addressSelenium
	 * @return
	 * @throws Exception
	 */
	public static WebDriver getWebDriver(String browserType, String addressSelenium ) throws Exception {
		
		if(!addressSelenium.toLowerCase().contains("localhost")){
			webDriver = getRemoteWebDriver(browserType, addressSelenium);
			 return webDriver;
		}
		
		switch (browserType.toLowerCase()) {
		 	case FIREFOX:
		 		webDriver =  getFirefoxDriver();
		 		return webDriver;
		    case CHROME:
		    		webDriver =  getChromeDriver();
		    		return webDriver;
	    default:
		    	String e = "Browser not supported. Please check!";
		    	logger.error(e);
			throw new Exception(e);
		}
		
		
	}

	/**Open remote in remote desktop
	 * 
	 * @param BrowserType
	 * @param addressSelenium
	 * @return
	 * @throws Exception
	 */
	private static WebDriver getRemoteWebDriver(String BrowserType, String addressSelenium) throws Exception{
		
		try {	
			switch (BrowserType.toLowerCase()) {
		 	case FIREFOX:
		 		return new RemoteWebDriver(new URL("http://" + addressSelenium + ":4444/wd/hub"),getCapabilitiesFirefoxDriver());  		    
		    case CHROME:
		    		return new RemoteWebDriver(new URL("http://" + addressSelenium + ":4444/wd/hub"),getCapabilitiesChromeDriver()); 
		    default:
		    		String e = "Browser not supported. Check!";
				throw new Exception (e);
			}
		} catch (MalformedURLException e) {
			throw new Exception("Fail connecting to Selenium HUB " + addressSelenium);
		}
		
	}
	/**Close Browser
	 * 
	 * @param webDriver
	 * @return
	 * @throws Exception
	 */
	public void closeWebDriver(WebDriver webDriver) throws Exception{
		
		if (webDriver == null) { return; }
		webDriver.quit();
		logger.info("Stoped Selenium");
		
	}
	
	/**
	 * Open Firefox
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("null")
	private static WebDriver getFirefoxDriver() throws Exception{
		logger.info("Starting Firefox");

		String hostOS = System.getProperty("os.name").toLowerCase();
		WebDriver webDriver = null;
		//TODO: Create settings for each OS
		switch (hostOS) {
		case "windows":
			
			break;
		case "mac":

			break;
		default:
			break;
		}
		
		webDriver.manage().window().maximize();
		logger.info("Started FireFox");
		return webDriver;
	}
	
	/**
	 * Open Chrome
	 * @return
	 */
	private static WebDriver getChromeDriver() {
		logger.info("Starting  Chrome");
		System.setProperty("webdriver.chrome.driver", "./resources/selenium/chromedriver.exe");
		@SuppressWarnings("deprecation")
		WebDriver webDriver = new ChromeDriver(getCapabilitiesChromeDriver());
		webDriver.manage().window().maximize();
		logger.info("Started Chrome");
		return webDriver;
	}
	
	/**
	 * Get capabilities for firefox
	 * @return
	 */
	private static DesiredCapabilities getCapabilitiesFirefoxDriver(){
		
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return capabilities;
	}
	
	/**
	 * Get capabilities for chrome
	 * @return
	 */
	private static DesiredCapabilities getCapabilitiesChromeDriver() {
	    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	
	    Map<String, Object> preferences = new Hashtable<String, Object>();
	    preferences.put("profile.default_content_settings.popups", 0);
	    preferences.put("download.prompt_for_download", "false");
	    preferences.put("download.default_directory", downloadDir);
	
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("prefs", preferences);
	
	    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	   
	    return capabilities;
	}
	
}

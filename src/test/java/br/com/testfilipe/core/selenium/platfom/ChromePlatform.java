package br.com.testfilipe.core.selenium.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromePlatform extends AbstractBrowserPlatform {
	
	final static Logger logger = Logger.getLogger(ChromePlatform.class);
	
	private static String OS ;
	private static String GETBINARYPATH;
	
	public static ChromePlatform StartWebDriver() {
		
		return new ChromePlatform();
	}
	
	public ChromePlatform() {
		OS = System.getProperty("os.name").toLowerCase();
		
		GETBINARYPATH = "src/test/resources/chromeDriver/";
		GETBINARYPATH =  GETBINARYPATH + (	OS.contains("windows")	? "win_chromedriver.exe" :
											OS.contains("mac")		? "mac_chromedriver" :
																	  "lin_chromedriver") ;

	}
	
	
	
	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Local WebDriver");
		
		
		String userhome = System.getProperty("user.home");
		if (OS.contains("windows")) {
			userhome = userhome + "\\AppData\\Local\\Google\\Chrome\\User Data";
		}else {
			userhome = userhome + "/.config/google-chrome";
		}
		
		ChromeOptions chromeOptions = new ChromeOptions(); 
		chromeOptions.addArguments("--user-data-dir", userhome);
		//chromeOptions.addArguments("--headless"); 
		chromeOptions.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", GETBINARYPATH);
		WebDriver webDriver = new ChromeDriver(chromeOptions);

		return webDriver;
				//return super.getLocalWebDriver();
	}
	
	@Override
	public WebDriver getRemoteWebDriver(String host, int port)  {
		logger.info("Starting Remote WebDriver");
		return super.getRemoteWebDriver(host, port);
	}
	
	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		return null;
		
	}

	public void cleanUpResources() {
		// TODO Auto-generated method stub
		
	}

}

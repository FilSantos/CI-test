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
		System.setProperty("webdriver.chrome.driver", GETBINARYPATH);
		ChromeOptions chromeOptions = new ChromeOptions(); 
		
/*		String currentPath = null;
		try {
			currentPath = new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String userhome = System.getProperty("user.home");
		if (OS.contains("windows")) {
			userhome = userhome + "\\AppData\\Local\\Google\\Chrome\\User Data";
		}else {
			userhome = userhome + "/.config/google-chrome";
		}
		
		
		currentPath = currentPath + "/profile";
		chromeOptions.addArguments("user-data-dir="+userhome);*/
		chromeOptions.setHeadless(false);
		WebDriver webDriver = new ChromeDriver(chromeOptions);
		webDriver.manage().window().maximize();
		
		return webDriver;
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

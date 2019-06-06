package br.com.portoseguro.core.selenium.platfom;

import java.io.IOException;

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
		String currentPath = "";
		try {
			currentPath = new java.io.File( "." ).getCanonicalPath() + "/downloads";
		} catch (IOException e) {
			logger.error("Erro ao acessar o diretorio de Download",e);
		}
		
		System.setProperty("webdriver.chrome.driver", GETBINARYPATH);
		ChromeOptions chromeOptions = new ChromeOptions(); 
		chromeOptions.setAcceptInsecureCerts(true);
		chromeOptions.addArguments("download.default_directory="+currentPath);
		chromeOptions.addArguments("profile.default_content_settings.popups=0");
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

	@Override
	public void cleanUpResources() {
		// TODO Auto-generated method stub
		
	}

}

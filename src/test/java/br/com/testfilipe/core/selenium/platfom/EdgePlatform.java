package br.com.testfilipe.core.selenium.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class EdgePlatform extends AbstractBrowserPlatform {
	
	final static Logger logger = Logger.getLogger(EdgePlatform.class);
	
	private static String OS ;
	private static String GETBINARYPATH;
	
	public static EdgePlatform StartWebDriver() {
		
		return new EdgePlatform();
	}
	
	public EdgePlatform() {
		OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("windows")){
			GETBINARYPATH = "src/test/resources/edgeDriver/MicrosoftWebDriver.exe";
		}

	}
	
	
	
	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Local WebDriver");

		System.setProperty("webdriver.edge.driver", GETBINARYPATH);

		
		WebDriver webDriver = new EdgeDriver();
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

package br.com.testfilipe.core.selenium.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HTMLUnitPlatform extends AbstractBrowserPlatform {
	
	final static Logger logger = Logger.getLogger(HTMLUnitPlatform.class);
	
	private static String OS ;
	
	public static HTMLUnitPlatform StartWebDriver() {
		
		return new HTMLUnitPlatform();
	}
	
	public HTMLUnitPlatform() {
		OS = System.getProperty("os.name").toLowerCase();
	}
	

	@Override
	public WebDriver getLocalWebDriver() {

		DesiredCapabilities caps = new DesiredCapabilities().htmlUnit();
		
		logger.info("Starting Local WebDriver");
		WebDriver webDriver = new HtmlUnitDriver(true);
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

package br.com.testfilipe.core.selenium.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


public abstract class AbstractBrowserPlatform extends AbstractPlatform {
	
	final static Logger logger = Logger.getLogger( AbstractBrowserPlatform.class);
	
	@Override
	public WebDriver getLocalWebDriver() {
		return null;
	}
	
}

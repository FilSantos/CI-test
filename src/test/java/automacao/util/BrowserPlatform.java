package automacao.util;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.browser.platfom.ChromePlatform;
import automacao.framework.iteraction.browser.platfom.PhantomJSPlatform;
import automacao.framework.iteraction.common.platform.Platform;
import automacao.framework.runner.testng.ListenerTestNG;

public class BrowserPlatform {
	
	public BrowserPlatform() {
		
	}
	
	public WebDriver getDriver() {
		
		Platform platform;
		if (ListenerTestNG.modoDev()) {
			platform = new ChromePlatform();
		}else {
			platform = new PhantomJSPlatform();
		}
		return  platform.getLocalWebDriver();
	}
	
}

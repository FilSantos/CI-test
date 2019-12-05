package automacao.step;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.common.platform.Platform;
import automacao.framework.iteraction.device.platform.AndroidPlatform;

public class ConstantsMobile {
	
	private static WebDriver appiumBRQ = null;
	private static  Platform appium;
	
	public static void startAppiumBRQLocal () {
		
	    appium = AndroidPlatform.simulator(true, "/home/filipe/git/CI-test/brq.apk", new Locale("pt","br"), "4.4");
	    appiumBRQ = appium.getLocalWebDriver();

	}
	
	public static WebDriver getAppiumBRQ() {
		
		return appiumBRQ;
		
	}
	public static void unloadScenario() {
		appiumBRQ.quit();
		appium.cleanUpResources();
	}

	public static void startAppiumBRQRemote() throws Exception {
	    appium = AndroidPlatform.simulator(true, "/home/filipe/git/CI-test/brq.apk", new Locale("pt","br"), "emulator-5554");
	    appiumBRQ = appium.getRemoteWebDriver("127.0.0.1",4723);
	    System.out.print("teste");
		
	}

}

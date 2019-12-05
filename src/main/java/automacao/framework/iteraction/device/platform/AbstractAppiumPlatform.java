package automacao.framework.iteraction.device.platform;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.framework.iteraction.common.platform.AbstractPlatform;
import automacao.framework.runner.LogConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;


public abstract class AbstractAppiumPlatform extends AbstractPlatform {
	
	final static Logger logger = Logger.getLogger( AbstractAppiumPlatform.class);
	
	private AppiumDriverLocalService localAppiumService;
	
	private class WebDriverAgentPort implements ServerArgument {
		@Override
		public String getArgument() {
			return "--webdriveragent-port";
		}
	}
	
	private synchronized void startLocalAppiumServiceIfNotRunning() {
		if (localAppiumService == null || !localAppiumService.isRunning()) {
			Map<String, String> env = new HashMap<>(System.getenv());
			
			String port = String.valueOf(ThreadLocalRandom.current().nextInt(8100, 8201));
			File logAppium = new File(LogConstants.LOGFOLDER + "appium-port-" + port + ".log");
			try {
				logAppium.createNewFile();
			} catch (IOException e) {
				logger.error("Log file error for Appim local server",e);
			}
			
			AppiumServiceBuilder builder = new AppiumServiceBuilder()
					.withEnvironment(env)
					.withLogFile(logAppium)
					.withArgument(new WebDriverAgentPort(), port)
//					.withIPAddress("127.0.0.1")
					//choices: ['info', 'info:debug', 'info:info', 'info:warn', 'info:error'
					.withArgument(GeneralServerFlag.LOG_LEVEL, "info:error")
					.usingAnyFreePort();
			
			
			localAppiumService = AppiumDriverLocalService.buildService(builder);
			localAppiumService.start();
		}
	}
	
	/**
	 * Creates a local {@code AppiumDriver} with the desired capabilities and instantiates
	 * an AppiumDriverLocalService to support it.
	 * 
	 * @Warning This instance of AbstractAppiumPlatform is responsible for maintaining and cleaning
	 * up all initiated services at the point of its destruction. Keep this in mind when instantiating
	 * AbstractAppiumPlatform if you intend to use local drivers.
	 */
	@Override
	public WebDriver getLocalWebDriver() {
		startLocalAppiumServiceIfNotRunning();
		AppiumDriver<WebElement> driver = new AppiumDriver<WebElement>(localAppiumService, getDesiredCapabilities());
		return driver;
	}
	
//	@Override
//	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
//		
//		String trimmedHost = host.trim().toLowerCase();
//		if ( trimmedHost.equals("127.0.0.1") || trimmedHost.equals("localhost") ) {
//			return getLocalWebDriver();
//		}
//		
//		return new AppiumDriver<IOSElement>(urlForRemoteHost(trimmedHost, port),
//				getDesiredCapabilities());
//		
////		return super.getRemoteWebDriver(host, port);
//	}
	
	@Override
	public synchronized void cleanUpResources() {
		if (localAppiumService != null && localAppiumService.isRunning()) {
			localAppiumService.stop();
			localAppiumService = null;
		}
	}
}

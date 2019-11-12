package automacao.framework.browser.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxPlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(FirefoxPlatform.class);

	private static String OS;
	private static String GETBINARYPATH;

	public static FirefoxPlatform StartWebDriver() {

		return new FirefoxPlatform();
	}

	public FirefoxPlatform() {
		OS = System.getProperty("os.name").toLowerCase();

		GETBINARYPATH = "src/test/resources/firefoxDriver/";
		GETBINARYPATH = GETBINARYPATH + (OS.contains("windows") ? "win_geckodriver.exe"
				: OS.contains("mac") ? "mac_geckodriver" : "lin_geckodriver");
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Local WebDriver");

		System.setProperty("webdriver.gecko.driver", GETBINARYPATH);
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true"); 
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setAcceptInsecureCerts(true);
		firefoxOptions.setHeadless(false);
		WebDriver webDriver = new FirefoxDriver(firefoxOptions);
		webDriver.manage().window().maximize();

		return webDriver;
	}

	@Override
	public WebDriver getRemoteWebDriver(String host, int port) {
		logger.info("Starting Remote WebDriver");
		return super.getRemoteWebDriver(host, port);
	}

	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		return null;
	}

	@Override
	public void cleanUpResources() {
	}
}
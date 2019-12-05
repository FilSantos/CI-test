package automacao.framework.iteraction.browser.platfom;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import automacao.framework.files.FilesAction;

public class FirefoxPlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(FirefoxPlatform.class);

	private static String OS;
	private static String GETBINARYPATH;

	public static FirefoxPlatform StartWebDriver() {

		return new FirefoxPlatform();
	}

	public FirefoxPlatform() {
		OS = System.getProperty("os.name").toLowerCase();
		GETBINARYPATH = "firefoxDriver/" + (OS.contains("windows") ? "win_geckodriver.exe"
				: OS.contains("mac") ? "mac_geckodriver" : "lin_geckodriver");
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Local WebDriver");
		
		FilesAction file = new FilesAction();
		if (!file.extractFromMainResources(GETBINARYPATH)){
			logger.error("Erro ao extrair o driver, nao é possivel continuar!");
			System.exit(-1);
		}

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
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
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
package automacao.framework.iteraction.browser.platfom;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import automacao.framework.files.FilesAction;

public class IEPlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(IEPlatform.class);

	private static String GETBINARYPATH;

	public static IEPlatform StartWebDriver() {

		return new IEPlatform();
	}

	public IEPlatform() {
		GETBINARYPATH = "ieDriver/IEDriverServer.exe";
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting IE Local WebDriver");
		
		FilesAction file = new FilesAction();
		if (!file.extractFromMainResources(GETBINARYPATH)){
			logger.error("Erro ao extrair o driver, nao é possivel continuar!");
			System.exit(-1);
		}

		System.setProperty("webdriver.ie.driver", GETBINARYPATH);
		System.setProperty("webdriver.ie.driver.silent", "true");

		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		@SuppressWarnings("deprecation")
		WebDriver webDriver = new InternetExplorerDriver(capabilities);
		webDriver.manage().window().maximize();

		return webDriver;
	}

	@Override
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
		logger.info("Starting IE Remote WebDriver");
		return super.getRemoteWebDriver(host, port);
	}

	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		return null;
	}

	public void cleanUpResources() {
	}
}
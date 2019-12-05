package automacao.framework.iteraction.browser.platfom;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import automacao.framework.files.FilesAction;

public class PhantomJSPlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(PhantomJSPlatform.class);

	private static String OS;
	private static String GETBINARYPATH;

	public static PhantomJSPlatform StartWebDriver() {
		return new PhantomJSPlatform();
	}

	public PhantomJSPlatform() {

		OS = System.getProperty("os.name").toLowerCase();
		GETBINARYPATH = "phantomJSDriver/" + (OS.contains("windows") ? "win_phantomjs.exe"
				: OS.contains("mac") ? "mac_phantomjs" : "lin_phantomjs");
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting PhantomJS Local WebDriver");

		FilesAction file = new FilesAction();
		if (!file.extractFromMainResources(GETBINARYPATH)){
			logger.error("Erro ao extrair o driver, nao é possivel continuar!");
			System.exit(-1);
		}
		
		System.setProperty("phantomjs.binary.path", GETBINARYPATH);
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		String[] phantomArgs = new String[] { "--webdriver-loglevel=NONE" };
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
		WebDriver webDriver = new PhantomJSDriver(caps);
		webDriver.manage().window().setSize(new Dimension(1366, 768));
		return webDriver;
	}

	@Override
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
		logger.info("Starting PhantomJS Remote WebDriver");
		return super.getRemoteWebDriver(host, port);
	}

	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		return null;

	}

	public void cleanUpResources() {
	}
}
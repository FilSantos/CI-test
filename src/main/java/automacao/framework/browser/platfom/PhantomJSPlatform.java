package automacao.framework.browser.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJSPlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(PhantomJSPlatform.class);

	private static String OS;
	private static String GETBINARYPATH;

	public static PhantomJSPlatform StartWebDriver() {
		return new PhantomJSPlatform();
	}

	public PhantomJSPlatform() {

		OS = System.getProperty("os.name").toLowerCase();
		GETBINARYPATH = "src/test/resources/phantomJSDriver/" + (OS.contains("windows") ? "win_phantomjs.exe"
				: OS.contains("mac") ? "mac_phantomjs" : "lin_phantomjs");
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting PhantomJS Local WebDriver");

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
	public WebDriver getRemoteWebDriver(String host, int port) {
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
package automacao.framework.browser.platfom;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class AbstractPlatform implements Platform {

	final static Logger logger = Logger.getLogger(AbstractPlatform.class);

	final static String downloadDir = "./downloads";
	
	@Override
	protected void finalize() throws Throwable {
		cleanUpResources();
		super.finalize();
	}

	protected abstract DesiredCapabilities getDesiredCapabilities();

	protected URL urlForRemoteHost(String host, int port) throws MalformedURLException {
		return new URL("http://" + host + ":" + port + "/wd/hub");
	}

	public abstract WebDriver getLocalWebDriver();

	public WebDriver getRemoteWebDriver(String host) {
		return getRemoteWebDriver(host, 4444);
	}

	public WebDriver getRemoteWebDriver(String host, int port) {
		String trimmedHost = host.trim().toLowerCase();
		if (trimmedHost.equals("localhost")) {
			return getLocalWebDriver();
		}
		try {
			RemoteWebDriver remoteWebDriver = new RemoteWebDriver(urlForRemoteHost(host, port),
					getDesiredCapabilities());
			logger.info("webDriver session: " + remoteWebDriver.getSessionId());
			System.out.println(remoteWebDriver.getSessionId());
			return remoteWebDriver;
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
package automacao.framework.iteraction.browser.platfom;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import automacao.framework.files.FilesAction;

public class EdgePlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(EdgePlatform.class);

	private static String GETBINARYPATH;

	public static EdgePlatform StartWebDriver() {

		return new EdgePlatform();
	}

	public EdgePlatform() {
			GETBINARYPATH = "edgeDriver/MicrosoftWebDriver.exe";
		
	}

	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Edge Local WebDriver");

		FilesAction file = new FilesAction();
		if (!file.extractFromMainResources(GETBINARYPATH)){
			logger.error("Erro ao extrair o driver, nao é possivel continuar!");
			System.exit(-1);
		}
		try{
			logger.info("Fechado instancias de driver do Edge abertas");
			Process process = Runtime. getRuntime(). exec("taskkill /F /IM MicrosoftWebDriver.exe");
			for (int i = 0; i < 10; i++) {
				if (process.isAlive()) {
					Thread.sleep(1000);
				}
			}
			process.destroy();
		}catch (Exception e) {
			// TODO: handle exception
		}

		
		
		System.setProperty("webdriver.edge.driver", GETBINARYPATH);

		WebDriver webDriver = new EdgeDriver();
		webDriver.manage().window().maximize();

		return webDriver;
	}

	@Override
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
		logger.info("Starting Edge Remote WebDriver");
		return super.getRemoteWebDriver(host, port);
	}

	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		return null;

	}

	public void cleanUpResources() {
	}
}
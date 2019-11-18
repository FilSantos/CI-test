package automacao.framework.browser.platfom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import automacao.framework.files.FilesAction;

public class ChromePlatform extends AbstractBrowserPlatform {

	final static Logger logger = Logger.getLogger(ChromePlatform.class);

	private static String OS;
	private static String GETBINARYPATH;

	public static ChromePlatform StartWebDriver() {
		return new ChromePlatform();
	}

	public ChromePlatform() {
		OS = System.getProperty("os.name").toLowerCase();
		GETBINARYPATH = "chromeDriver/" + (OS.contains("windows") ? "win_chromedriver.exe"
				: OS.contains("mac") ? "mac_chromedriver" : "lin_chromedriver");
	}

	@Override
	public WebDriver getLocalWebDriver() {
		
		FilesAction file = new FilesAction();
		if (!file.extractFromMainResources(GETBINARYPATH)){
			logger.error("Erro ao extrair o driver, nao é possivel continuar!");
			System.exit(-1);
		}
		
	    if(OS.toLowerCase().contains("windows")) {
			try {
				logger.info("Fechado instancias de Chrome abertas - Windows");
				List<Process> process = new ArrayList<Process>();
				process.add(Runtime. getRuntime(). exec("taskkill /F /IM win_chromedriver.exe"));
				//process.add(Runtime. getRuntime(). exec("taskkill /F /IM chrome.exe"));
				waitProcess(process);
				
			} catch (Exception e) {
				logger.fatal("Erro ao finalizar Chrome");
			} 
	    }
		
		
		logger.info("Inciando o Chrome local");
		String currentPath = "";
		try {
			currentPath = new java.io.File(".").getCanonicalPath() + "/downloads";
		} catch (IOException e) {
			logger.error("Erro ao acessar o diretorio de Download", e);
		}

		System.setProperty("webdriver.chrome.driver", GETBINARYPATH);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("download.default_directory=" + currentPath);

		String profileChrome =  System.getProperty("user.home") + (OS.contains("windows") ? "\\AppData\\Local\\Google\\Chrome\\User Data" :
																	"");

//		chromeOptions.addArguments("--user-data-dir=" + profileChrome);
//		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--no-sandbox");
//		chromeOptions.addArguments("--disable-gpu");
		
//		chromeOptions.setHeadless(!ListenerTest.modoDev());
        
		WebDriver webDriver = new ChromeDriver(chromeOptions);
		webDriver.manage().window().maximize();

		return webDriver;
	}

	private void waitProcess(List<Process> process) throws InterruptedException {
		for (Process processItem : process) {
			for (int i = 0; i < 10; i++) {
				if (processItem.isAlive()) {
					Thread.sleep(1000);
				}
			}
			processItem.destroy();	
		}
		
	}

	@Override
	public WebDriver getRemoteWebDriver(String host, int port) {
		logger.info("Iniciando Chrome Remote WebDriver");

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
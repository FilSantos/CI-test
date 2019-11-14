package automacao.test.testng;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automacao.framework.browser.platfom.FirefoxPlatform;
import automacao.framework.browser.platfom.Platform;
import automacao.framework.runner.LogConstants;
import automacao.framework.runner.testng.ListenerTestNG;
import automacao.model.Login;
import automacao.model.MenuSuperior;
import automacao.util.PropertiesUtil;


@Listeners(ListenerTestNG.class)
public class TestPOC {

	final static Logger LOGGER = Logger.getLogger(TestPOC.class);
	private WebDriver webDriver;
	MenuSuperior menuSuperior;
	
	@BeforeSuite
	public void setup() throws Exception{
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		Platform platform = new FirefoxPlatform();
		webDriver =  platform.getLocalWebDriver();
		webDriver.navigate().to(PropertiesUtil.getProp("web.url"));
		ListenerTestNG.setWebdriver(webDriver);
		Login login = new Login(webDriver);
		login.acessar(PropertiesUtil.getProp("usuario.usuario"), PropertiesUtil.getProp("usuario.senha"));
		menuSuperior = new MenuSuperior(webDriver);
	}
	
	@BeforeTest
	public void configureTest() throws Exception{
		menuSuperior.paginaPrincipal();
		
	}

	@Test
	public void enviaProposta() throws Exception{
		menuSuperior.takeScreenshot();
	}
	
	@AfterSuite
	public void tearDown() throws Exception{
		menuSuperior.efetuarLogOff();
		webDriver.quit();
		webDriver.close();
	}
}

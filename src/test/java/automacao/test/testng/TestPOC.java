package automacao.test.testng;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automacao.framework.browser.platfom.ChromePlatform;
import automacao.framework.browser.platfom.Platform;
import automacao.framework.database.H2sql;
import automacao.framework.runner.LogConstants;
import automacao.framework.runner.testng.ListenerTestNG;
import automacao.model.CriarProposta;
import automacao.model.Login;
import automacao.model.MenuSuperior;
import automacao.util.DadosUtils;
import automacao.util.PropertiesUtil;


@Listeners(ListenerTestNG.class)
public class TestPOC {

	final static Logger LOGGER = Logger.getLogger(TestPOC.class);
	private WebDriver webDriver;
	MenuSuperior menuSuperior;
	
	@BeforeSuite
	public void setup() throws Exception{
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		H2sql.openConnection();
		Platform platform = new ChromePlatform();
		webDriver =  platform.getLocalWebDriver();
		webDriver.navigate().to(PropertiesUtil.getProp("web.url"));
		ListenerTestNG.setWebdriver(webDriver);
		Login login = new Login(webDriver);
		login.acessar(PropertiesUtil.getProp("usuario.usuario"), PropertiesUtil.getProp("usuario.senha"));
		menuSuperior = new MenuSuperior(webDriver);
	}
	
	@BeforeMethod
	public void configureTest() throws Exception{
		menuSuperior.paginaPrincipal();
		menuSuperior.criaProposta();
	}

	@Test(dataProvider = "enviaProposta", dataProviderClass = DadosUtils.class, priority = 1)
	public void enviaProposta(String cpfCliente) throws Exception{
		
		CriarProposta proposta = new CriarProposta(webDriver);
		
		proposta.simulacaoValorCompra("100000", "10000", null, "CDC", "476 - CARNÊ", 0, "Filipe", cpfCliente, "teste automatizado");
	}
	
	@AfterSuite
	public void tearDown() throws Exception{
		menuSuperior.efetuarLogOff();
		webDriver.quit();
		
	}
}

package br.com.portoseguro.test.salesforce;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import br.com.portoseguro.core.database.H2sql;
import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.PhantomJSPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.test.Iteracao.SalesForceLogin;

public class PesquisaProposta {
	
	final static Logger logger = Logger.getLogger(PesquisaProposta.class);
	private static WebDriver webDriver; 
	private static SalesForceLogin login; 
	
	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = PhantomJSPlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();			
				
	}
	
	@Test
	
	public void test () throws Exception{
		
		login = new SalesForceLogin(webDriver);
		login.iniciaAutenticação();
		login.preencheLoginSF();
		login.tapAcessar();	
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriver.close();
		webDriver.quit();
				
	}
	
	

}
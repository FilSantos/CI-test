package br.com.testfilipe.test.salesforce;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.core.selenium.platfom.ChromePlatform;
import br.com.testfilipe.core.selenium.platfom.Platform;
import br.com.testfilipe.test.Iteracao.LoginSalesForce;

public class PesquisaProposta {
	
	final static Logger logger = Logger.getLogger(PesquisaProposta.class);
	private static WebDriver webDriver; 
	private static LoginSalesForce login; 
	
	@BeforeSuite
	public static void iniciate() throws Exception {
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = ChromePlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();		
		login = new LoginSalesForce(webDriver);
		login.iniciaAutenticação();
		login.preencheLoginSF();
		login.tapAcessar();	
		
				
	}
	
	@Test
	
	public void test (){
		
		
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriver.quit();
		webDriver.close();
		
				
	}
	
	

}
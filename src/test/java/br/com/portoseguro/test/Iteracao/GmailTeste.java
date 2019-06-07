package br.com.portoseguro.test.Iteracao;

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
import br.com.portoseguro.test.Iteracao.LoginSalesForce;

public class GmailTeste {
	
	final static Logger logger = Logger.getLogger(GmailTeste.class);
	private static WebDriver webDriver; 
	private static LoginContaGmail loginGmail; 
	private static LoginAutenticacaoPorto loginAutenticacao;
	private static HorarioEmails emails;
	
	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = PhantomJSPlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();			
				
	}
	
	@Test
	
	public void test () throws Exception{
		
		loginGmail = new LoginContaGmail(webDriver);
		loginAutenticacao = new LoginAutenticacaoPorto(webDriver);
		emails = new HorarioEmails(webDriver);
		
		loginGmail.iniciaAutenticação();
		loginGmail.preencheLoginGmail();
		loginGmail.nextButton();
		loginAutenticacao.preencheUserPasswordPorto();
		loginAutenticacao.signIn();
		
		
		
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriver.close();
		webDriver.quit();		
				
	}
}

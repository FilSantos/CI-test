package br.com.portoseguro.test.Iteracao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import br.com.portoseguro.core.database.H2sql;
import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.ChromePlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;

public class GmailTeste {
	
	final static Logger logger = Logger.getLogger(GmailTeste.class);
	private static WebDriver webDriver; 
	private static LoginContaGmail loginGmail; 
	private static LoginAutenticacaoPorto loginAutenticacao;
	private static HorarioEmails emails;
	
	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = ChromePlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();			
				
	}
	
	@Test
	
	public void test () throws Exception{
		String remetente = "Google"; //coloque um email valido que tenha no gmail
		String assunto = "Google";  // coloque um assunto que exista no gmail
		String data = " 1 jun";  // coloque a data correta no gmail
		
		SimpleDateFormat formato = new SimpleDateFormat("dd MMM");
		Date horario = formato.parse(data);
		
		loginGmail = new LoginContaGmail(webDriver);
		loginAutenticacao = new LoginAutenticacaoPorto(webDriver);
		emails = new HorarioEmails(webDriver);
		
		loginGmail.iniciaAutenticação();
		loginGmail.preencheLoginGmail();
		loginGmail.nextButton();
		loginGmail.preencheLoginSenha();
		loginGmail.nextButton();
		//loginAutenticacao.preencheUserPasswordPorto();
		//loginAutenticacao.signIn();
		emails.Selecionaemail(remetente, assunto ,horario);
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriver.close();
		webDriver.quit();		
				
	}
}

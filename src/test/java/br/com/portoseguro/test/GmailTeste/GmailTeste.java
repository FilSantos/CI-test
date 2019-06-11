package br.com.portoseguro.test.GmailTeste;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import br.com.portoseguro.core.database.H2sql;
import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.ChromePlatform;
import br.com.portoseguro.core.selenium.platfom.PhantomJSPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.pageobject.salesforce.AppsMenu;
import br.com.portoseguro.pageobject.salesforce.AppsMenuItem;
import br.com.portoseguro.pageobject.salesforce.MainMenu;
import br.com.portoseguro.pageobject.salesforce.MainMenuItem;
import br.com.portoseguro.test.Iteracao.GmailInbox;
import br.com.portoseguro.test.Iteracao.GmailLogin;
import br.com.portoseguro.test.Iteracao.InserirToken;
import br.com.portoseguro.test.Iteracao.PortoLogin;
import br.com.portoseguro.test.Iteracao.SalesForceLogin;

public class GmailTeste {
	
	final static Logger logger = Logger.getLogger(GmailTeste.class);
	private static WebDriver webDriver;
	private static WebDriver webDriverGmail;
	private static GmailLogin loginGmail; 
	private static PortoLogin loginAutenticacao;
	private static GmailInbox emails;
	private static SalesForceLogin loginSF;
	private static InserirToken token;
	private static AppsMenu apps;
	private static MainMenu menu;

	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform salesforce = ChromePlatform.StartWebDriver();
		Platform auth = PhantomJSPlatform.StartWebDriver();
		webDriver = salesforce.getLocalWebDriver();
		webDriverGmail = auth.getLocalWebDriver();	
		H2sql.openConnection();			
				
	}
	
	@BeforeTest
	public void startTest(){
		loginSF = new SalesForceLogin(webDriver);
		loginGmail = new GmailLogin(webDriverGmail);
		loginAutenticacao = new PortoLogin(webDriverGmail);
		emails = new GmailInbox(webDriverGmail);
		apps = new AppsMenu(webDriver);
		menu = new MainMenu(webDriver);
	}
	
	@Test
	public void test () throws Exception{
	
		loginSF.iniciaAutenticação();
		loginSF.preencheLoginSF();
		loginSF.tapAcessar();
		loginSF.erroMessage();
		loginGmail.iniciaAutenticação();
		loginGmail.preencheLoginGmail();
		loginGmail.nextButton();
		loginAutenticacao.preencheUserPasswordPorto();
		loginAutenticacao.signIn();
		loginGmail.btnContinuar();	
		emails.selecionaEmail("noreply@salesforce", "Sandbox: Verifique sua identidade no Salesforce" ,new Date ());
		token.tokenPorto(emails.recuperaToken());
		emails.excluirEmailGmail();
		apps.openMenu();
		apps.navigateMenuApp(AppsMenuItem.VIDA_APP);
		menu.navigateMenu(MainMenuItem.CONTRTACAO_TAB);
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriverGmail.close();
		webDriverGmail.quit();
		webDriver.close();
		webDriver.quit();		
				
	}
}

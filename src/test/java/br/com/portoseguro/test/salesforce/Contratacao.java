package br.com.portoseguro.test.salesforce;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.ChromePlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.pageobject.salesforce.AppsMenu;
import br.com.portoseguro.pageobject.salesforce.AppsMenuItem;
import br.com.portoseguro.pageobject.salesforce.MainMenu;
import br.com.portoseguro.pageobject.salesforce.MainMenuItem;
import br.com.portoseguro.test.Iteracao.LoginIntegradoSFGmail;

public class Contratacao {
	
	final static Logger logger = Logger.getLogger(Contratacao.class);
	private static WebDriver webDriver;

	private static AppsMenu apps;
	private static MainMenu menu;
	
	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform salesforce = ChromePlatform.StartWebDriver();
		webDriver = salesforce.getLocalWebDriver();
		LoginIntegradoSFGmail login = new LoginIntegradoSFGmail(webDriver);
		login.login();
		apps = new AppsMenu(webDriver);
		menu = new MainMenu(webDriver);
				
	}
	
	@BeforeTest
	public void startTest() throws Exception{
		apps.openMenu();
		apps.navigateMenuApp(AppsMenuItem.VIDA_APP);
		menu.navigateMenu(MainMenuItem.CONTRTACAO_TAB);
	}
	
	@Test
	public void familia () throws Exception{
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	@Test
	public void todos () throws Exception{
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	@Test
	public void voce () throws Exception{
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	
	
	@AfterSuite
	public static void teardown() {

		webDriver.close();
		webDriver.quit();		
				
	}
}

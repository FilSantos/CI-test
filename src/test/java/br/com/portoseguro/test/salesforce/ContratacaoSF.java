package br.com.portoseguro.test.salesforce;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.ChromePlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.core.selenium.searchelements.SearchElement;
import br.com.portoseguro.pageobject.salesforce.AppsMenu;
import br.com.portoseguro.pageobject.salesforce.AppsMenuItem;
import br.com.portoseguro.pageobject.salesforce.Autenticacao2Fatores;
import br.com.portoseguro.pageobject.salesforce.MainMenu;
import br.com.portoseguro.pageobject.salesforce.MainMenuItem;
import br.com.portoseguro.test.Iteracao.Contratacao;
import br.com.portoseguro.test.Iteracao.LoginIntegradoSFGmail;

public class ContratacaoSF {
	
	final static Logger logger = Logger.getLogger(ContratacaoSF.class);
	private static WebDriver webDriver;

	private static AppsMenu apps;
	private static MainMenu menu;
	private static Contratacao contract;
	
	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform salesforce = ChromePlatform.StartWebDriver();
		webDriver = salesforce.getLocalWebDriver();
		LoginIntegradoSFGmail login = new LoginIntegradoSFGmail(webDriver);
		login.login();
		apps = new AppsMenu(webDriver);
		menu = new MainMenu(webDriver);
		contract = new Contratacao(webDriver);
		
				
	}
	
	@BeforeTest
	public void startTest() throws Exception{
		apps.openMenu();
		apps.navigateMenuApp(AppsMenuItem.VIDA_APP);
		menu.navigateMenu(MainMenuItem.CONTRTACAO_TAB);
	}
	
	@Test
	public void familia () throws Exception{
		
		contract.selecaoCorretor();
		contract.btnEntrar();
		contract.planoFamilia();
		contract.proximaEtapa();
		contract.nomeCompleto();
		contract.dataNascimento();
		contract.sexoMasculino();
		contract.fumanteSim();
		contract.estadoViuvo();
		contract.profissao();
		contract.proximaEtapa();
		contract.acima16000();
		contract.btnEnviar();
		contract.planoVivaSuperior();
		contract.btnConfirmar();		
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	@Test
	public void todos () throws Exception{
		
		contract.selecaoCorretor();
		contract.btnEntrar();
		contract.planoTodos();
		contract.proximaEtapa();
		contract.nomeCompleto();
		contract.dataNascimento();
		contract.sexoFeminino();
		contract.fumanteNao();
		contract.estadoCasado();
		contract.profissao();
		contract.proximaEtapa();
		contract.renda4000();
		contract.btnEnviar();
		contract.planoVivaInferior();
		contract.btnConfirmar();
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	@Test
	public void voce () throws Exception{
		
		contract.selecaoCorretor();
		contract.btnEntrar();
		contract.planoVoce();
		contract.proximaEtapa();
		contract.nomeCompleto();
		contract.dataNascimento();
		contract.sexoMasculino();
		contract.fumanteSim();
		contract.estadoViuvo();
		contract.profissao();
		contract.proximaEtapa();
		contract.renda4000();
		contract.btnEnviar();
		contract.planoVivaSuperior();
		contract.btnConfirmar();
		
		System.out.println("aqui vou colocar um teste para ser executado");

	}
	
	
	@AfterSuite
	public static void teardown() {

		webDriver.close();
		webDriver.quit();		
				
	}
}

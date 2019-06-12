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
import br.com.portoseguro.core.selenium.platfom.IEPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.pageobject.salesforce.AppsMenuItem;
import br.com.portoseguro.pageobject.salesforce.MainMenuItem;
import br.com.portoseguro.test.iteracao.AppsMenu;
import br.com.portoseguro.test.iteracao.Contratacao;
import br.com.portoseguro.test.iteracao.LoginIntegradoSFGmail;
import br.com.portoseguro.test.iteracao.MainMenu;
import br.com.portoseguro.test.iteracao.enums.ContratacaoPlano;
import br.com.portoseguro.test.iteracao.enums.ContratacaoPlanoVivaTranquilo;
import br.com.portoseguro.test.iteracao.enums.EstadoCivil;
import br.com.portoseguro.test.iteracao.enums.RendaMensal;

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
		menu.navigateMenu(MainMenuItem.CONTRATACAO_TAB);
	}
	
	@AfterSuite
	public static void teardown() {

		webDriver.close();
		webDriver.quit();		
				
	}
	
	@Test
	public void familia () throws Exception{
		
		contract.selecaoCanal(true);
		contract.btnEntrar();
		contract.selecionarPlano(ContratacaoPlano.FAMILIA);
		contract.proximaEtapa();
		contract.nomeCompleto("Bruno de Melo Silva");
		contract.dataNascimento("07081992");
		contract.sexo(true);
		//contract.fumanteSim();
		contract.estadoCivil(EstadoCivil.SOLTEIRO);
		contract.profissao("Analista QA");
		contract.proximaEtapa();
		contract.rendaMensal(RendaMensal.ACIMA16000);
		contract.btnEnviar();
		contract.planoVivaTranquilo(ContratacaoPlanoVivaTranquilo.VIVATRANQUILOSUPERIOR);
		contract.btnConfirmar();

	}
	/*@Test
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
	*/
}

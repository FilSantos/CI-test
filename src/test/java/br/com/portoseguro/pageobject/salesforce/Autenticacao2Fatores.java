package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;



/**
 * Page Object da pagina de Autenticacao 2 fatores do SalesForce
 * @author Bruno Silva(Cognizant)
 *
 */
public class Autenticacao2Fatores extends BaseWebPage{

	public Autenticacao2Fatores(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By CODIGO_VERIFICADOR = By.id("emc");
	private static By BOTAO_VERIFICAR = By.id("save");
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Campo de inserir o token
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement inserirToken() throws Exception{
		
		return searchElement.findElementBy(CODIGO_VERIFICADOR, "Código do token");
	}
	
	
	
	/**
	 * Botão de Verificar token
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement btnVerificar() throws Exception{
		
		return searchElement.findElementBy(BOTAO_VERIFICAR, "Botao verificar token");
	}
}
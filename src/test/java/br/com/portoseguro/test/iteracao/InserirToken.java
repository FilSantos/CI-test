package br.com.portoseguro.test.iteracao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.Autenticacao2Fatores;

public class InserirToken extends BaseWebPage {

	
	private Autenticacao2Fatores token;

		
		public InserirToken(WebDriver webDriver) {
		super(webDriver);
		token = new Autenticacao2Fatores (webDriver);
		
		}			
		
		@Override
		public boolean isDisplayed() {
			return token.isDisplayed();
		}
		
			
		/**
		 * Validar inserção do token da porto na autenticacao de 2 fatores
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void tokenPorto(String tokenPort) throws Exception {
					
			WebElement tokenPorto = token.inserirToken();
			command.send(tokenPorto, tokenPort);
			WebElement btnVerificar = token.btnVerificar();
			command.click(btnVerificar);
		}
}

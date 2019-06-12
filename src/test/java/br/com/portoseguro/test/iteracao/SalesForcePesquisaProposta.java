package br.com.portoseguro.test.iteracao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.Propostas;

public class SalesForcePesquisaProposta extends BaseWebPage {
	
	private Propostas propostas;
	
	
	
	public SalesForcePesquisaProposta(WebDriver webDriver) {		
		super(webDriver);
		propostas = new Propostas (webDriver);		
	}
	
	@Override
	public boolean isDisplayed() {
		
		return false;
	}
	
	public void preenchePesquisaProposta(String numeroProposta) throws Exception {
				
		WebElement pesquisaProposta = propostas.pesquisaPropostas();
		command.send(pesquisaProposta, numeroProposta);
			
	}
	
}

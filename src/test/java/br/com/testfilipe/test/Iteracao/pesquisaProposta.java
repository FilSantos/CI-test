package br.com.testfilipe.test.Iteracao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.test.pageobject.salesforce.Propostas;

public class pesquisaProposta extends BaseWebPage {
	
	private Propostas propostas;
	
	
	
	public pesquisaProposta(WebDriver webDriver) {		
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

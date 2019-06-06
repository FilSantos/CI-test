package br.com.portoseguro.pageobject.salesforce;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

/**
 * Page Object para manipulacao dos Resultados de Pesquisa
 * @author filipe
 * 
 */
public class SearchResults extends BaseWebPage {
	
	private By TABLELISTCONTRACTS = By.xpath("//table[@summary='Contratos']/../following-sibling::div[@class='pbBody']//tr[contains(@class,'dataRow')]/th/a");
	
	
	public SearchResults(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	

	/**
	 * Clica no contrato na lista de contratos
	 * @author filipe cognizant
	 * @param contract
	 * @throws Exception 
	 */
	public void tapContractTable(String contract) throws Exception {
		
		List<WebElement> contractList = searchElement.findElementsBy(TABLELISTCONTRACTS, "Lista de Contratos");
		for (WebElement contractItem : contractList) {
			if ( contractItem.getText().equals(contract) ) {
				contractItem.click();
				break;
			}
		}
		throw new Exception("Contrato nao localizado na lista");
	}
	

}

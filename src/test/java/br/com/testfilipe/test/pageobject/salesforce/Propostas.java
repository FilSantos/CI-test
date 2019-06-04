package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.core.selenium.searchelements.SearchElement;

/**
 * 
 * @author everton cognizant
 *
 */
@SuppressWarnings("unused")
public class Propostas extends BaseWebPage {

	private static By SEARCHGRID_TEXTBOX = By.xpath("//nav[@role='navigation']//li//span[text()='Propostas']/../../../../../../../..//input");
	
	
	public Propostas(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	
	public WebElement pesquisaPropostas() throws Exception {
		
		return searchElement.findElementBy(SEARCHGRID_TEXTBOX, "pesquisaPropostas");	
		
	}
	
	

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
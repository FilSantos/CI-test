package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

/**
 * 
 * @author everton cognizant
 *
 */
@SuppressWarnings("unused")
public class Propostas extends BaseWebPage {

	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Opportunity-search-input']");
	
	
	public Propostas(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
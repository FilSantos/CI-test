package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @autor everton cognizant
 */

public class ContratantesDetalhes extends BaseWebPage {
    public ContratantesDetalhes(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By QUANTITYPARCEL_TEXTBOX = By.xpath("//div[@id='00N0j000000jbgQ_ileinner']");

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Retorna o número de parcelas
	 * @author everton cognizant
	 * @return String
	 * @throws Exception
	 */
	private String getQuantityParcel() throws Exception{

		WebElement text = searchElement.findElementBy(QUANTITYPARCEL_TEXTBOX, "Quantidade de Parcelas");

		return text.getText();
	}
}

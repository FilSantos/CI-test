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
public class ConfirmacaoGmail extends BaseWebPage{

	public ConfirmacaoGmail(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By BTN_CONTINUAR = By.xpath("//span[text() = 'Continuar']");
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Botao Continuar
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement btnContinuar() throws Exception{
		
		return searchElement.findElementBy(BTN_CONTINUAR, "Botao Continuar");
	}

}

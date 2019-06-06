package br.com.portoseguro.pageobject.gmail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

/**
 * Page Object da pagina de login de autenticação Porto Seguro
 * @author Bruno Silva(Cognizant)
 *
 */
public class GmailInbox extends BaseWebPage{
	private static By emailList = By.xpath("//div[@class='AO']//tbody/tr");
	private static By emailDetail = By.xpath("//td[contains(@class,'xW xY')]");
	
	public GmailInbox(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		return false;
	}
	
	/**
	 * Campo de user name Porto
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public List<WebElement> emailLista() throws Exception{
		
		return searchElement.findElementsBy(emailList, "Lista de e-mail");
	}
	
	public WebElement emailDetalheHorario(WebElement emailItem) throws Exception{
		
		return searchElement.findChildElementBy(emailItem, emailDetail, "Horário do e-mail");
	}
	
	public WebElement emailDetalheRemetente(WebElement emailItem) throws Exception{
		
		return searchElement.findChildElementBy(emailItem, emailDetail, "remetente do e-mail");
	}
	
	
	
	
	
	
}

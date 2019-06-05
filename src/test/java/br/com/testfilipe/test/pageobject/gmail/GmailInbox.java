package br.com.testfilipe.test.pageobject.gmail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.core.selenium.searchelements.SearchElementForWEB;

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
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
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
		
		return searchElement.findChildElementBy(emailItem, emailDetail, "Horário de e-mails");
	}
	
	
	
	
}

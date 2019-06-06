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
	private static By emailList = By.xpath("//div[@class='AO']//tbody/tr[contains(@class,'zA')]");				
	private static By emailDateTime = By.xpath("//td[9]/span[1]/span[1]");
	private static By emailName = By.xpath("//td[5]/div[2]/span[1]/span[1]");
	private static By emailContent = By.xpath("//div/div/span/span[not(@class='Zt')]");
	
	public GmailInbox(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		return false;
	}
	
	/**
	 * Lista de e-mail porto
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public List<WebElement> emailLista() throws Exception{
		
		return searchElement.findElementsBy(emailList, "Lista de e-mail");
	}
	
	/**
	 * Lista de e-mail porto com detalhes de data e horário
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement emailDetalheHorario(WebElement emailItem) throws Exception{
		
		return searchElement.findChildElementBy(emailItem, emailDateTime, "Horário do e-mail");
	}
	
	/**
	 * Nome do remetente do e-mail
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement emailName(WebElement emailItem) throws Exception{
		
		return searchElement.findChildElementBy(emailItem, emailName, "Nome do remetente do e-mail");
	}
	
	/**
	 * Conteúdo do e-mail no título
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement emailContent(WebElement emailItem) throws Exception{
		
		return searchElement.findChildElementBy(emailItem, emailContent, "Conteúdo do e-mail no título");
	}
	
	
	
	
	
	
}

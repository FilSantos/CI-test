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
public class GmailMailbox extends BaseWebPage{
	private static By emailList = By.xpath("//div[@class='Cp']//tbody/tr | //table[@class='th']//tbody/tr");	
	private static By emailContent = By.xpath(".//*[@class='ii gt' and not(@style)] | //div[@class='msg']");
	private static By excluirEmail = By.xpath("//div[contains(@data-tooltip,'Excluir')]");
	
	public GmailMailbox(WebDriver webDriver) {
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

	public WebElement getEmailContent() throws Exception {
		return searchElement.findElementBy(emailContent, "Conteudo email");
	}
	
	
	public List<WebElement> getExluirEmail() throws Exception {
		return searchElement.findElementsBy(excluirEmail, "Exclusao de email");
	}
	
	
	
}
package br.com.testfilipe.test.pageobject.gmail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

/**
 * Page Object da pagina de login no gmail
 * @author Bruno Silva(Cognizant)
 *
 */
public class LoginGmail extends BaseWebPage{
	
	
	private static By Gmail_Email = By.id("identifierId");
	private static By Next_Button = By.className("gmail.usuario");

	public LoginGmail(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	} 
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Campo de e-mail gmail
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement email() throws Exception {
		
		return searchElement.findElementBy(Gmail_Email, "Endereço de e-mail");
	}
	
	/**
	 * Campo de e-mail gmail
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement nextButton() throws Exception {
		
		return searchElement.findElementBy(Next_Button, "Botão Próximo");
	}
	
	
	
	

	
	
	
		
	

}

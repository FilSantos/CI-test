package br.com.testfilipe.test.pageobject.gmail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

/**
 * Page Object da pagina de login de autenticação Porto Seguro
 * @author Bruno Silva(Cognizant)
 *
 */
public class AutenticacaoPorto extends BaseWebPage{
	
	
	private static By Porto_User = By.id("ContentPlaceHolder1_UsernameTextBox");
	private static By Porto_Password = By.id("ContentPlaceHolder1_PasswordTextBox");
	private static By Porto_Sign_In = By.id("ContentPlaceHolder1_SubmitButton");

	public AutenticacaoPorto(WebDriver webDriver) {
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
	public WebElement userPorto() throws Exception {
		
		return searchElement.findElementBy(Porto_User, "Usuário Porto Seguro");
	}
	
	/**
	 * Campo de password Porto
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement PasswordPorto() throws Exception {
		
		return searchElement.findElementBy(Porto_Password, "Senha de usuário Porto Seguro");
	}
	
	
	/**
	 * Botão de Sign in
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement signIn() throws Exception {
		
		return searchElement.findElementBy(Porto_Sign_In, "Botão de Sign In");
	}
	
	
}
	
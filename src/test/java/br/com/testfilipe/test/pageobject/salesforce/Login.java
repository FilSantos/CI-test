package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;



/**
 * Page Object da pagina de Login
 * @author Bruno Silva(Cognizant)
 *
 */
public class Login  extends BaseWebPage{

	private static By LOGIN_FORM = By.id("theloginform"); 
	private static By USERNAME_TEXTBOX = By.id("username"); 
	private static By PASSWORD_TEXTBOX = By.id("password");
	private static By LOGIN_BUTTON = By.id("Login"); 
	private static By USERNAME_SAVED_TEXTBOX = By.xpath("//div[@id='idcard']/span");
	private static By USERNAME_SAVED_CLEAR_BUTTON = By.className("clearicon");
	private static By REMEMBERME_CHECKBOX = By.name("rememberUn");
	private static By FORGOT_PASSWORD_HYPERLINK = By.id("forgot_password_link");
	
	public Login(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isDisplayed() {
		return searchElement.existsNoLog(LOGIN_FORM, "Página de acesso", 30) ? true : false;
	}	

	/**
	 * Campo de texto - Usuario
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement user() throws Exception {
		
		return searchElement.findElementBy(USERNAME_TEXTBOX, "Nome de usuário");
	}
	
	/**
	 * Campo de senha - Senha
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement password() throws Exception{
		
		return searchElement.findElementBy(PASSWORD_TEXTBOX, "Senha");
	}
	
	/**
	 * Efetuar login - Login de Usuario
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement logarSF() throws Exception{
		
		return searchElement.findElementBy(LOGIN_BUTTON, "Efetuar login");
		
	}	
	
	/**
	 * Campo de Usuário salvo - Usuario salvo
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	public WebElement savedUsername() throws Exception{
		
		return searchElement.findElementBy(USERNAME_SAVED_TEXTBOX, "Usuário salvo");
		
	}
	
	/**
	 * Campo de apagar usuário salvo - Apagar Usuario salvo
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public boolean existsClearButton () throws Exception{
		
		return searchElement.existsNoLog(USERNAME_SAVED_CLEAR_BUTTON, "Clear button", 5000);
	}
	
	/**
	 * Validar botão de limpar usuário salvo 
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement btnClear () throws Exception{
		
		return searchElement.findElementBy(USERNAME_SAVED_CLEAR_BUTTON, "Botão Limpar Usuário logado");
		
		
	}
	
	/**
	 * Validar botão de lembrar o usuário logado 
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement rememberMe() throws Exception{
		
		return searchElement.findElementBy(REMEMBERME_CHECKBOX, "Lembrar Usuário");
		
	}
	
	/**
	 * Validar botão de lembrar a senha 
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */
	
	public WebElement esqueciSenha() throws Exception{
		
		return searchElement.findElementBy(FORGOT_PASSWORD_HYPERLINK, "ESqueci a senha");
		
	}
	
	
	
		
}

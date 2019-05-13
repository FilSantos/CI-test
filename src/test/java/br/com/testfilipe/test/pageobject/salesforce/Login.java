package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

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
	public void tapLembrarUsuario() throws Exception {
		WebElement lembrarme = searchElement.findElementBy(REMEMBERME_CHECKBOX, "Lembrar do usuario");
		command.click(lembrarme);
	}
	
	public void tapEsqueciSenha() throws Exception {
		WebElement esqueciSenha = searchElement.findElementBy(FORGOT_PASSWORD_HYPERLINK, "Esqueci minha senha");
		command.click(esqueciSenha);
	}
	
	public void tapAcessar() throws Exception {
		WebElement acessar = searchElement.findElementBy(LOGIN_BUTTON, "Acessar");
		command.click(acessar);
	}
	
	public void setUsuario(String email) throws Exception {
		
		boolean noLogin = true;
		if (searchElement.existsNoLog(USERNAME_SAVED_TEXTBOX, "Login autal", 5)) {
			WebElement usernameLogged = searchElement.findElementBy(USERNAME_SAVED_TEXTBOX, "Usuario logado");
			noLogin = usernameLogged.getText().trim().equals(email.trim()) ? false : true;
			if (noLogin) {
				try {
					WebElement clearUsername = searchElement.findElementBy(USERNAME_SAVED_CLEAR_BUTTON, "Limpar Usuário");
					command.clear(clearUsername);
				} finally {
					
				}

			}
		}
		
		if (noLogin) {
			WebElement username = searchElement.findElementBy(USERNAME_TEXTBOX, "Nome do Usuario");
			command.send(username, email);
		}

	}
	
	public void setSenha(String pwd) throws Exception {
		
		boolean noLogin = true;
		noLogin = searchElement.existsNoLog(USERNAME_SAVED_TEXTBOX, "Login autal", 5) ? false : true;
		
		if (noLogin) {
			WebElement password = searchElement.findElementBy(PASSWORD_TEXTBOX, "Senha");
			command.send(password, pwd);
		}
		

	}
	
	public void setcredentials(String username, String pwd) throws Exception {
		setUsuario(username);
		setSenha(pwd);
		tapAcessar();
	}
	
}

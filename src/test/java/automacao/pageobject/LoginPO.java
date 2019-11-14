package automacao.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.framework.browser.pageobject.WebPage;

public class LoginPO extends WebPage{

	private By TXTUSUARIO = By.id("UserName");
	private By TXTSENHA = By.id("Password");
	private By BTNLOGIN = By.xpath("//button[text()='Entrar']");
	
	public LoginPO(WebDriver webDriver) {
		super(webDriver);

	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public WebElement usuario() throws Exception{
		return searchElement.findElementBy(TXTUSUARIO, "Usuário");
	}
	public WebElement senha() throws Exception{
		return searchElement.findElementBy(TXTSENHA, "Senha");
	}
	public WebElement btnLogin() throws Exception{
		return searchElement.findElementBy(BTNLOGIN, "Botão Login");
	}

}

package automacao.model;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.browser.pageobject.WebPage;
import automacao.pageobject.testeWeb.LoginPO;

public class Login extends WebPage {

	private LoginPO login;
	
	
	public Login(WebDriver webDriver) {
		super(webDriver);
		login = new LoginPO(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void acessar(String usuario, String senha) throws Exception {
		command.send(login.usuario(), usuario);
		command.send(login.senha(), senha);
		command.click(login.btnLogin());
		command.screenshot();
	}
	
	

}

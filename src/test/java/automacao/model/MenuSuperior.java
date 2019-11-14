package automacao.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import automacao.framework.browser.pageobject.WebPage;
import automacao.pageobject.MenuSuperiorPO;

public class MenuSuperior extends WebPage {

	private MenuSuperiorPO menuSuperior;
	
	
	public MenuSuperior(WebDriver webDriver) {
		super(webDriver);
		menuSuperior = new MenuSuperiorPO(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		return menuSuperior.isDisplayed();
	}
	
	public void paginaPrincipal() throws Exception {
		command.click(menuSuperior.paginainicial());
	}
	public void usuario() throws Exception {
		 Actions builder = new Actions(webDriver);
		 builder.moveToElement(menuSuperior.usuario()).build().perform();
	}
	
	public void sairSistema() throws Exception {
		 command.click(menuSuperior.sair());
		 
	}
	public void efetuarLogOff() throws Exception {
		usuario();
		command.screenshot();
		sairSistema();
	}
	

}

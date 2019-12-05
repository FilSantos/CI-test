package automacao.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import automacao.framework.iteraction.browser.pageobject.WebPage;
import automacao.pageobject.testeWeb.MenuSuperiorPO;

public class MenuSuperior extends WebPage {

	private MenuSuperiorPO menuSuperior;
	private Actions builder;
	
	public MenuSuperior(WebDriver webDriver) {
		super(webDriver);
		menuSuperior = new MenuSuperiorPO(webDriver);
		builder = new Actions(webDriver);
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
	
	private void menuProposta() throws Exception {
		
		 builder.moveToElement(menuSuperior.proposta()).build().perform();
	}
	public void sairSistema() throws Exception {
		 command.click(menuSuperior.sair());
		 
	}
	public void efetuarLogOff() throws Exception {
		usuario();
		command.screenshot();
		sairSistema();
	}

	public void criaProposta() throws Exception {
		menuProposta();
		command.click(menuSuperior.criarProposta());
		//builder.moveToElement(menuSuperior.usuario()).click().build().perform();
		
	}
	

}

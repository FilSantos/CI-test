package automacao.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.framework.browser.pageobject.WebPage;

public class MenuSuperiorPO extends WebPage{

	private By MNUPROPOSTA =  By.xpath("//a[text()='Proposta']");
	private By MNUCRIARPROPOSTA =  By.xpath("//a[text()='Criar Proposta']");
	private By ICONPAGINAINCIAL = By.xpath(".//*[@class='icon-home icon-2x']");
	private By ICONUSUARIO = By.xpath(".//*[@class='icon-user icon-2x']");
	private By MNUSAIR = By.id("#logoff");
	private By LOGO = By.xpath("//img[@alt='Agoracred']");
	
	public MenuSuperiorPO(WebDriver webDriver) {
		super(webDriver);

	}

	@Override
	public boolean isDisplayed() {
		return searchElement.existsNoLog(LOGO, "Menu Superior", 30);
	}
	
	public WebElement paginainicial() throws Exception{
		return searchElement.findElementBy(ICONPAGINAINCIAL, "Menu inicial");
	}
	public WebElement usuario() throws Exception{
		return searchElement.findElementBy(ICONUSUARIO, "Menu Usuario");
	}
	public WebElement sair() throws Exception{
		return searchElement.findElementBy(MNUSAIR, "Item Sair");
	}

	public WebElement proposta() throws Exception {
		return searchElement.findElementBy(MNUPROPOSTA, "Menu Proposta");
	}
	
	public WebElement criarProposta() throws Exception {
		return searchElement.findElementBy(MNUCRIARPROPOSTA, "Menu Criar Proposta");
	}

}

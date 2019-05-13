package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

/**
 * 
 * @author everton cognizant
 * @since 05032019
 *
 */
@SuppressWarnings("unused")
public class Contas extends BaseWebPage{

	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By BRANDNAME_TEXTBOX = By.xpath(".//*[text()='Nome fantasia']/../../input");
	private static By ACCOUNTNAME_TEXTBOX = By.xpath(".//*[text()='Nome da conta']/../../input");
	private static By REGISTRATIONNUMBER_TEXTBOX = By.xpath(".//*[text()='Número de inscrição']/../../input");
	private static By WEBSITE_TEXTBOX = By.xpath(".//*[text()='Site da Web']/../../input");
	private static By EMAIL_TEXTBOX = By.xpath(".//*[text()='Email']/../../input");
	private static By ADMTYPE_TEXTBOX = By.xpath(".//*[text()='Tipo de administração']/../..//a");
	private static By COUNTRYCODE_TEXTBOX = By.xpath(".//*[text()='Código do país']/../..//a");
	private static By CNAE_TEXTAREA = By.xpath(".//*[text()='CNAE']/../../textarea");
	private static By COMMERCIALPHONE_TEXTBOX = By.xpath(".//*[text()='Telefone comercial']/../../input");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By IMPORT_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Importar']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Account-search-input']");
	
	private static String ADMTYPE_SELECT = "//a[@title='%s']";
	
	public Contas(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


}

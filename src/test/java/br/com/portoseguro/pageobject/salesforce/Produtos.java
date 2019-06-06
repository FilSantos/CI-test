package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
/**
 * 
 * @author everton cognizant
 *
 */
@SuppressWarnings("unused")
public class Produtos extends BaseWebPage{
	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By NEXT_BUTTON = By.xpath("//span[contains(text(),'Avançar')]");
	private static By PRODUCTNAME_TEXTBOX = By.xpath(".//*[text()='Nome do produto']/../..//input");
	private static By RENOVATIONVERSION_SELECT = By.xpath(".//*[text()='Versão de renovação']/../..//a[@class='select']");
	private static By STARTVALIDITY_SELECT = By.xpath(".//*[text()='Vigência inicial']/../..//input");
	private static By FINALVALIDITY_SELECT = By.xpath(".//*[text()='Vigência final']/../..//input");
	private static By TOTALLOADINGLIMIT_TEXTBOX = By.xpath(".//*[text()='Limite total de carregamento']/../..//input");
	private static By PRODUCTDESCRIPTION_TEXTAREA = By.xpath(".//*[text()='Descrição do produto']/../../textarea");
	private static By TYPEPERSONRESPPAYMENT_SLIDER = By.xpath("//span[contains(text(),'Vida')]");
	private static By RECIIENTTYPE_SLIDER = By.xpath("//span[contains(text(),'Parceria')]");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	
	private static String NEWPRODUCT_OPTION = "//span[text()='%s']";
	private static String RENOVATIONVERSION_OPTION = "//span[text()='%s']";
	private static String STARTVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String FINALVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String RIGHTSLIDER_OPTION = "> //div[text()='%s']/..//.././/*[@data-key='right']";
	private static String LEFTSLIDER_OPTION = "< //div[text()='%s']/..//.././/*[@data-key='left']";
	
	public Produtos(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

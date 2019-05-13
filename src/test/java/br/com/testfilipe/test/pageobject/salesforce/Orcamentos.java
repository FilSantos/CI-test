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
public class Orcamentos extends BaseWebPage {

	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By NEXT_BUTTON = By.xpath("//span[contains(text(),'Avançar')]");
	private static By BUDGETNUMBER_TEXTBOX = By.xpath(".//*[text()='Número do orçamento']/../../input");
	private static By FASE_TEXTBOX = By.xpath(".//*[text()='Fase']/../..//a[@class='select']");
	private static By FINANCIALCLOSEDATE_SELECT = By.xpath(".//*[text()='Previsão de Fechamento']/../..//input");
	private static By STARTVALIDITY_SELECT = By.xpath(".//*[text()='Vigência inicial']/../..//input");
	private static By FINALVALIDITY_SELECT = By.xpath(".//*[text()='Vigência final']/../..//input");
	private static By DATEOFCALCULATION_SELECT = By.xpath(".//*[text()='Data do cálculo']/../..//input");
	private static By TYPE_SELECT = By.xpath(".//*[text()='Tipo']/../..//a[@class='select']");
	private static By PURERATE_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa pura')]");
	private static By COMMERCIALAWARD_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa pura')]");
	private static By TOTALRATE_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa total')]");
	private static By TOTALAWARD_TEXTBOX = By.xpath("//span[contains(text(), 'Prêmio total')]");
	private static By DESCAGRAPERCTEC_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual técnico')]");
	private static By DESCAGRAMONTEC_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário técnico')]");
	private static By DESCAGRAPERCCOMM_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual comercial')]");
	private static By DESCAGRAMONCOMM_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário comercial')]");
	private static By DESCAGRAPERCINFO_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual informado')]");
	private static By DESCAGRAMONINFO_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário informado')]");
	private static By BUSINESSENTRANCE_TEXTBOX = By.xpath("//span[contains(text(), 'Entrada Negócio')]");
	private static By DESCRIPTION_TEXTBOX = By.xpath("//span[contains(text(), 'Descrição')]");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Opportunity-search-input']");
	
	
	
	private static String NEWBUDGETSELECT_OPTION = "//span[text()='%s']";
	private static String FINANCIALCLOSEDATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String STARTVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String FINALVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String DATEOFCALCULATION_OPTION = "//span[contains(@class, 'weekday')]";
	private static String TYPE_OPTION = "//a[text()='%s']";
	
	
	
	public Orcamentos(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

/**
 * 
 * @author everton cognizant
 *
 */

@SuppressWarnings("unused")
public class Contratos extends BaseWebPage{

	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");	
	private static By NEXT_BUTTON = By.xpath("//span[contains(text(),'Avançar')]");
	private static By STATUS_SELECT = By.xpath(".//*[text()='Status']/../..//a[@class='select']");
	private static By STARTVALIDITY_SELECT = By.xpath(".//*[text()='Vigência inicial']/../..//input");
	private static By FINALVALIDITY_SELECT = By.xpath(".//*[text()='Vigência final']/../..//input");
	private static By PARTNERAGREEMENTID_TEXTBOX = By.xpath(".//*[text()='id Contrato Parceiro']/../..//input");
	private static By POLICYNUMBER_TEXTBOX = By.xpath(".//*[text()='Número da apólice']/../..//input");
	private static By BRANCHCODE_TEXTBOX = By.xpath(".//*[text()='Código do Ramo']/../..//input");
	private static By TYPE_SELECT = By.xpath(".//*[text()='Tipo']/../..//a[@class='select']");
	private static By MEANSOFMARKETING_SELECT = By.xpath(".//*[text()='Meio de comercialização']/../..//a[@class='select']");
	private static By ENDORSEMENTTYPE_SELECT = By.xpath(".//*[text()='Tipo de endosso']/../..//a[@class='select']");
	private static By PURERATE_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa pura')]");
	private static By COMMERCIALAWARD_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa pura')]");
	private static By COMMERCIALRATE_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa comercial')]");
	private static By PUREAWARD_TEXTBOX = By.xpath("//span[contains(text(), 'Prêmio puro')]");
	private static By TOTALRATE_TEXTBOX = By.xpath("//span[contains(text(), 'Taxa total')]");
	private static By TOTALAWARD_TEXTBOX = By.xpath("//span[contains(text(), 'Prêmio total')]");
	private static By DESCAGRAPERCTEC_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual técnico')]");
	private static By DESCAGRAMONTEC_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário técnico')]");
	private static By DESCAGRAPERCCOMM_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual comercial')]");
	private static By DESCAGRAMONCOMM_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário comercial')]");
	private static By DESCAGRAPERCINFO_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo percentual informado')]");
	private static By DESCAGRAMONINFO_TEXTBOX = By.xpath("//span[contains(text(), 'Desconto agravo monetário informado')]");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Opportunity-search-input']");
	private static By LISTVIEW_SELECT = By.xpath("//div[@class='slds-grid']//a[@title='Selecionar exibição de lista']");
	private static By CONTRACTNUMBER_LINK = By.xpath("//a[@data-refid=\"recordId\"][@title=\"00011981\"]");
	private static By ACCOUNTNAME_LINK = By.xpath("//*[text()='Nome da conta']/../..//a[@class='textUnderline outputLookupLink slds-truncate forceOutputLookup']");
	
	private static String NEWCONTRACTSELECT_OPTION = "//span[contains(text(),'%s')]";
	private static String STATUS_OPTION = "//a[text()='%S']";
	private static String STARTVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String FINALVALIDITY_OPTION = "//span[contains(@class, 'weekday')]";
	private static String TYPE_OPTION = "//a[text()='%s']";
	private static String MEANSOFMARKETING_OPTION = "//a[text()='%s]";
	private static String ENDORSEMENTTYPE_OPTION = "//a[text()='%s']";
	private static String LISTVIEW_OPTION = "//span[contains(@class,'virtualAutocompleteOptionText')][contains(text(),'%s')]";
	
	public Contratos(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

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
public class Faturas extends BaseWebPage{
	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By INVOICETYPE_SELECT = By.xpath(".//*[text()='Tipo de fatura']/../..//a[@class='select']");
	private static By STATUS_SELECT = By.xpath(".//*[text()='Status']/../..//a[@class='select']");
	private static By STARTCOMPETENCE_SELECT = By.xpath(".//*[text()='InÃ­cio de competÃªncia']/../..//input");
	private static By ENDCOMPETENCE_SELECT = By.xpath(".//*[text()='InÃ­cio de competÃªncia']/../..//input");
	private static By COMMERCPRODUCTCODE_TEXTBOX = By.xpath(".//*[text()='Código do produto comercial']/../..//input");
	private static By NETVALUE = By.xpath(".//*[text()='Valor líquido']/../..//input");
	private static By IOFVALUE = By.xpath(".//*[text()='Valor de IOF']/../..//input");
	private static By TOTALVALUE = By.xpath(".//*[text()='Valor total']/../..//input");
	private static By AMOUNTPAID = By.xpath(".//*[text()='Valor pago']/../..//input");
	private static By ISSUEDATE = By.xpath(".//*[text()='Data de emissão']/../..//input");
	private static By DUEDATE = By.xpath(".//*[text()='Data de vencimento']/../..//input");
	private static By ENDCOMPETITION_SELECT = By.xpath(".//*[text()='Final de competência']/../..//input");
	private static By COMPANY_TEXTBOX = By.xpath(".//*[text()='Empresa']/../..//input");
	private static By ORIGIN_TEXTBOX = By.xpath(".//*[text()='Origem']/../..//input");
	private static By BRANCHOFFICE_TEXTBOX = By.xpath(".//*[text()='Sucursal']/../..//input");
	private static By PROPOSAL_TEXTBOX = By.xpath(".//*[text()='Proposta']/../..//input");
	private static By ACTIVITYAREA_TEXTBOX = By.xpath(".//*[text()='Ramo']/../..//input");
	private static By ENDORSEMENT_TEXTBOX = By.xpath(".//*[text()='Endosso']/../..//input");
	private static By POLICY_TEXTBOX = By.xpath(".//*[text()='Apólice']/../..//input");
	private static By FINANCIALCONTRACTNUMBER_TEXTBOX = By.xpath(".//*[text()='Número contrato financeiro']/../..//input");
	private static By INVOICEID_TEXTBOX = By.xpath(".//*[text()='.//*[text()='Id da fatura']/../..//input");
	private static By TICKETCODE_TEXTBOX = By.xpath(".//*[text()='.//*[text()='Código do boleto']/../..//input");
	private static By CODEATTINVOICE_TEXTBOX = By.xpath(".//*[text()='.//*[text()='Código do anexo da fatura']/../..//input");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By CHANGEOWNER_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Alterar proptietário']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Case-search-input']");
	
	
	
	private static String INVOICETYPE_OPTION = "//a[text()='%s']";
	private static String STATUS_OPTION = "//a[text()='%s']";
	private static String STARTCOMPETENCE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String ENDCOMPETENCE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String ISSUEDATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String DUEDATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String ENDCOMPETITION_OPTION = "//span[contains(@class, 'weekday')]";	
	
	public Faturas(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

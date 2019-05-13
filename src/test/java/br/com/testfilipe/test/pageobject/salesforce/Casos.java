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
public class Casos extends BaseWebPage{
	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By NEXT_BUTTON = By.xpath("//span[contains(text(),'Avançar')]");
	private static By CASESTATUS_SELECT = By.xpath(".//*[text()='Status do caso']/../..//a[@class='select']");
	private static By CASESOURCE_SELECT = By.xpath(".//*[text()='Origem do caso']/../..//a[@class='select']");
	private static By SUBJECT_TEXTBOX = By.xpath(".//*[text()='Assunto']/../..//input");
	private static By VICTIM_SELECT = By.xpath(".//*[text()='Vítima']/../..//a[@class='select']");
	private static By DESCRIPTION_TEXTAREA = By.xpath(".//*[text()='Descrição']/../../textarea");
	private static By TYPE_SELECT = By.xpath(".//*[text()='Tipo']/../..//a[@class='select']");
	private static By DATEOFOCCURENCE_SELECT = By.xpath(".//*[text()='Data de ocorrência']/../..//input");
	private static By NOTICEDATE_SELECT = By.xpath(".//*[text()='Data de Aviso Cliente']/../..//input");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By FASE_SELECT = By.xpath(".//*[text()='Fase']/../..//a[@class='select']");
	private static By PRIORITY_SELECT = By.xpath(".//*[text()='Prioridade']/../..//a[@class='select']");
	private static By SINISTBENEFAVISED_CHECKBOX = By.xpath("//span[contains(text(),'Sinistro/Benefício Avisado?')]/../.././/*[contains(@data-aura-class,'Checkbox')]");
	private static By STOPED_CHECKBOX = By.xpath("//span[contains(text(),'Parado')]/../.././/*[contains(@data-aura-class,'Checkbox')]");
	private static By EVENTTYPE_SELECT = By.xpath(".//*[text()='Tipo de Evento']/../..//a[@class='select']");
	private static By DOCENTRYDATE_SELECT = By.xpath(".//*[text()='Data da entrada de documentos']/../..//input");
	private static By ORIGINDOCDATE_SELECT = By.xpath(".//*[text()='Origem de documentos']/../..//a[@class='select']");
	private static By REISURANCE_TEXTBOX = By.xpath(".//*[text()='Resseguro']/../..//input");
	private static By IRBVALUE_TEXTBOX = By.xpath(".//*[text()='Valor IRB']/../..//input");
	private static By INSURANCECOST_SELECT = By.xpath(".//*[text()='Custeador do seguro']/../..//a[@class='select']");
	private static By MEDICALOPINION_CHECKBOX = By.xpath("//span[contains(text(),'Parecer médico?')]/../.././/*[contains(@type,'checkbox')]");
	private static By FRAUD_CHECKBOX = By.xpath("//span[contains(text(),'Fraude?')]/../.././/*[contains(@type,'checkbox')]");
	private static By PERIODREMOTENESS_TEXTBOX = By.xpath(".//*[text()='Período de afastamento']/../..//input");
	private static By MEDICALANALYZE_CHECKBOX = By.xpath("//span[contains(text(),'Análise médica?')]/../.././/*[contains(@type,'checkbox')]");
	private static By INTEGRATIONSTATUS_CHECKBOX = By.xpath(".//*[text()='Status da Integração']/../..//a[@class='select']");
	private static By INTEGRATIONMESSAGE_TEXTBOX = By.xpath(".//*[text()='Mensagem da Integração']/../..//input");
	private static By CHANGEOWNER_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Alterar proptietário']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Opportunity-search-input']");
	
	private static String NEWCASESELECT_OPTION = "//span[text()='%s']";
	private static String CASESTATUS_OPTION = "//a[text()='%s']";
	private static String CASESOURCE_OPTION = "//a[text()='%s']";
	private static String TYPE_OPTION = "//a[text()='%s']";
	private static String DATEOFOCCURENCE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String NOTICEDATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String FASE_OPTION = "//a[text()='%s']";
	private static String PRIORITY_OPTION = "//a[text()='%s']";
	private static String EVENTTYPE_OPTION = "//a[text()='%s']";
	private static String DOCENTRYDATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String ORIGINDOCDATE_OPTION = "//a[text()='%s']";
	private static String INSURANCECOST_OPTION = "//a[text()='%s']";
	private static String INTEGRATIONSTATUS_OPTION = "//a[text()='%s']";
	
	public Casos(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

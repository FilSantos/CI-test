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
public class Valores extends BaseWebPage{
	private static By NEW_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Novo(a)']");
	private static By COMMERCIALCONTRIBUTION_TEXTBOX = By.xpath(".//*[text()='Contribuição comercial']/../..//input");
	private static By SUSEPNUMBER_TEXTBOX = By.xpath(".//*[text()='Número SUSEP']/../..//input");
	private static By PURECONTRIBUTION_TEXTBOX = By.xpath(".//*[text()='Contribuição pura']/../..//input");
	private static By SUSEPPLANCODE_TEXTBOX = By.xpath(".//*[text()='Código do plano SUSEP']/../..//input");
	private static By INITIALCOSTS_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Custos iniciais']");
	private static By DATE_SELECT = By.xpath(".//*[text()='Data']/../..//input");	
	private static By LOADING_TEXTBOX = By.xpath(".//*[text()='Carregamento']/../..//input");
	private static By PARTICIPANTSINCLUDED_TEXTBOX = By.xpath(".//*[text()='Participantes incluídos']/../..//input");
	private static By PARTICIPANTSEXCLUDED_TEXTBOX = By.xpath(".//*[text()='Participantes excluídos']/../..//input");
	private static By TYPEOFINCOME_TEXTBOX = By.xpath(".//*[text()='Tipo de renda']/../..//input");
	private static By PARTICIPANTSCANCELED_TEXTBOX = By.xpath(".//*[text()='Participantes cancelados']/../..//input");
	private static By MOVIMENTTYPE_SELECT = By.xpath(".//*[text()='Tipo de Movimento']/../..//a[@class='select']");
	private static By PORTIONTYPE_SELECT = By.xpath(".//*[text()='Tipo da Parcela']/../..//a[@class='select']");
	private static By REGISTERTYPE_SELECT = By.xpath(".//*[text()='Tipo de Registro']/../..//a[@class='select']");
	private static By LIQUIDAWARD_TEXTBOX = By.xpath(".//*[text()='Prêmio liquido']/../..//input");
	private static By TOTALAWARD_TEXTBOX = By.xpath(".//*[text()='Prêmio total']/../..//input");
	private static By QUANTITYLIVES_TEXTBOX = By.xpath(".//*[text()='Quantidade de vidas']/../..//input");
	private static By SAVEANDCREATE_BUTTON = By.xpath("//span[text()='Salvar e criar']");
	private static By SAVE_BUTTON = By.xpath("//span[text()='Salvar']");
	private static By CHANGEOWNER_BUTTON = By.xpath("//li[contains(@class, 'button')]//div[@title='Alterar proptietário']");
	private static By SEARCHGRID_TEXTBOX = By.xpath("//div[@class='slds-from-element']//input[@name='Case-search-input']");

	
	private static String DATE_OPTION = "//span[contains(@class, 'weekday')]";
	private static String MOVIMENTTYPE_OPTION = "//a[text()='%s']";
	private static String PORTIONTYPE_OPTION = "//a[text()='%s']";
	private static String REGISTERTYPE_OPTION = "//a[text()='%s']";
	
	public Valores(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

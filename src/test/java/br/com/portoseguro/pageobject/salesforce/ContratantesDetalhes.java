package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

/**
 * @autor everton cognizant
 */

public class ContratantesDetalhes extends BaseWebPage {
    public ContratantesDetalhes(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By QUANTITYPARCEL_TEXTBOX = By.xpath("//tr//td[contains(text(),'Quantidade de parcelas')]/..//following-sibling::td[@class='dataCol col02 inlineEditWrite']/div");
	private static By MATCHFIRSTPARCEL_TEXTBOX =  By.xpath("//tr//td[contains(text(),'Vencimento da primeira parcela')]/..//following-sibling::td[@class='dataCol col02 inlineEditWrite']/div");
	private static By PARCELAWARD_TEXTBOX = By.xpath("//tr//td[contains(text(),'Prêmio da parcela')]/..//following-sibling::td[@class='dataCol inlineEditWrite']/div[1]");
	private static By ACCOUNT_LINK = By.xpath("//tr//td[contains(text(),'Conta')]/..//following-sibling::td[@class='dataCol col02 inlineEditWrite']//a");
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Retorna o número de parcelas
	 * @author everton cognizant
	 * @return String
	 * @throws Exception
	 */
	public String getQuantityParcel() throws Exception{

		WebElement text = searchElement.findElementBy(QUANTITYPARCEL_TEXTBOX, "Quantidade de Parcelas");

		return text.getText();
	}
	
	/** Retorna o vencimento da primeira parcela
	 * @author everton cognizant
	 * @return String
	 * @throws Exception
	 */
	public String getMatchFirstParcel() throws Exception{
		
		WebElement text = searchElement.findElementBy(MATCHFIRSTPARCEL_TEXTBOX, "Vencimento da primeira parcela");
		
		return text.getText();
	}
	
	/** Retorna o prêmio da parcela
	 * @author everton cognizant
	 * @return String
	 * @throws Exception
	 */
	public String getParcelAward() throws Exception{
		
		WebElement text = searchElement.findElementBy(PARCELAWARD_TEXTBOX, "Prêmio da parcela");
		
		return text.getText();
	}
	
	/** Acessa o link conta, clicando no nome
	 * @author everton cognizant
	 * @throws Exception
	 */
	public void tapAccount() throws Exception{
		
		WebElement link = searchElement.findElementBy(ACCOUNT_LINK, "Conta (nome)");
		
		command.click(link);
	}
}

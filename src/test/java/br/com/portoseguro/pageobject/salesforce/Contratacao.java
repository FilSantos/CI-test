package br.com.portoseguro.pageobject.salesforce;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;



/**
 * Page Object da pagina de Login de Contratacao
 * @author Bruno Silva(Cognizant)
 *
 */
public class Contratacao  extends BaseWebPage{

	public Contratacao(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By Channel_Choose = By.xpath("//input[@class='select-dropdown dropdown-trigger']");
	private static By Channel_Choosed = By.xpath("//li/span[contains(text(),'Corretor')]");
	private static By Identification = By.id("nome");
	private static By portoButton = By.xpath("//input[@value = 'Entrar']");
	private static By contractType = By.xpath("//section[@id='etapa1']//input");
	private static By typedContractedForYou = By.xpath("//li/span[contains(text(),'Plano para você')]");
	private static By typedContractedForFamily = By.xpath("//li/span[contains(text(),'Plano para sua família')]");
	private static By typedContractedForAll = By.xpath("//li/span[contains(text(),'Plano para todos')]");
	private static By nextStage = By.xpath("//input[@value = 'Próxima etapa']");
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Campo de Selecionar o canal
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement selecionarCanal() throws Exception{
		
		return searchElement.findElementBy(Channel_Choose, "Selecionar o canal");
	}
	
	/**
	 * Campo de canal selecionado
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement canalSelecionado() throws Exception{
		
		return searchElement.findElementBy(Channel_Choosed, "Canal selecionado");
	}
	
	/**
	 * Campo de identificação
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement portoButton() throws Exception{
		
		return searchElement.findElementBy(portoButton, "Botão de entrar");
	}
	
	
	
	/**
	 * Campo de selecionar tipo de plano para você
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForYou() throws Exception{
		
		return searchElement.findElementBy(typedContractedForYou, "Tipo de contrato para você");
	}
	
	/**
	 * Campo de selecionar tipo de plano para família
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForFamily() throws Exception{
		
		return searchElement.findElementBy(typedContractedForFamily, "Tipo de contrato para família");
	}
	
	/**
	 * Campo de selecionar tipo de plano para todos
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForAll() throws Exception{
		
		return searchElement.findElementBy(typedContractedForAll, "Tipo de contrato para todos");
	}
	
	/**
	 * Botão para próxima etapa
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement nextStage() throws Exception{
		
		return searchElement.findElementBy(nextStage, "Botão próxima etapa");
	}
	
	
	
	
}
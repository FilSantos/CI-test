package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

public class MainMenu extends BaseWebPage{

	private static By SEARCH_TEXTBOX = By.id("phSearchInput");
	private static By SEARCH_BUTTON = By.id("phSearchButton");
	
	public MainMenu(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public void navigateMenu(MainMenuItem menuItem) throws Exception {
		WebElement menuNav = searchElement.findElementBy(menuItem.getLocator(), menuItem.getReportName());
		webDriver.navigate().to(menuNav.getAttribute("href"));		
	}

	/** Pesquisa e clica no item pesquisado
	 * @author filipe cognizant
	 * @param search
	 * @throws Exception
	 */
	public void searchValue(String search) throws Exception {
		WebElement searchTextbox = searchElement.findElementBy(SEARCH_TEXTBOX, "Caixa de Pesquisa");
		searchTextbox.sendKeys(search);
		
		WebElement searchButton = searchElement.findElementBy(SEARCH_BUTTON, "Caixa de Pesquisa");
		command.click(searchButton);
	}
	
	
	
}

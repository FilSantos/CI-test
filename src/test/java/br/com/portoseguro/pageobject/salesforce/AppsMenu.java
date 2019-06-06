package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

public class AppsMenu extends BaseWebPage{

	public AppsMenu(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	private static By Menu = By.className("bare slds-icon-waffle_container slds-context-bar__button slds-button uiButton forceHeaderButton salesforceIdentityAppLauncherHeader");

	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public void openMenu() throws Exception {
		WebElement openMenu = searchElement.findElementBy(Menu, "Menu Apps");
	}
	
	public void navigateMenuApp(AppsMenuItem menuItem) throws Exception {
		WebElement menuApp = searchElement.findElementBy(menuItem.getLocator(), menuItem.getReportName());
		webDriver.navigate().to(menuApp.getAttribute("href"));		
	}
	
	
}

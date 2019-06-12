package br.com.portoseguro.test.iteracao;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.AppsMenuItem;

public class AppsMenu extends BaseWebPage{

	public AppsMenu(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	private static By Menu = By.xpath("//div[@class='slds-icon-waffle']");

	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public void openMenu() throws Exception {
		WebElement openMenu = searchElement.findElementBy(Menu, "Menu Apps");
		command.click(openMenu);
	}
	
	public void navigateMenuApp(AppsMenuItem menuItem) throws Exception {
		WebElement menuApp = searchElement.findElementBy(menuItem.getLocator(), menuItem.getReportName());
		command.click(menuApp);	
	}
	
	
}

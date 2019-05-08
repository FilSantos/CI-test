package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;

public class MainMenu extends BaseWebPage{

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
	
}

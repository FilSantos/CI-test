package br.com.portoseguro.core.selenium.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import br.com.portoseguro.core.selenium.command.CommandType;
import br.com.portoseguro.core.selenium.searchelements.SearchElementType;

public abstract class BaseWebPage extends Page {
	
	static final Logger logger = Logger.getLogger(BaseWebPage.class);
	
/*	protected WebDriver webDriver;	
	protected CommandAction command;	
	protected SearchElement searchElement;
	protected WebDriverWait webDriverWait;*/
	
/**Default options to Page Objects
 * 
 * @param webDriver
 * @param command
 * @param searchElement
 */
	public BaseWebPage(WebDriver webDriver){
		super(webDriver,
				CommandType.WEB.getCommand(webDriver),
				SearchElementType.WEB.getSearchElement(webDriver));
	}
	
	/**
	 * @return true if the current UI state is that which is represented by this page object. false otherwise
	 */
	public abstract boolean isDisplayed();
	    

}


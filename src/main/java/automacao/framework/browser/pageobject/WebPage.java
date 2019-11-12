package automacao.framework.browser.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import automacao.framework.browser.command.CommandType;
import automacao.framework.browser.search.SearchElementType;

public abstract class WebPage extends DefaultPage {

	static final Logger logger = Logger.getLogger(WebPage.class);

	/*
	 * protected WebDriver webDriver; protected CommandAction command; protected
	 * SearchElement searchElement; protected WebDriverWait webDriverWait;
	 */

	/**
	 * Default options to Page Objects
	 * 
	 * @param webDriver
	 * @param command
	 * @param searchElement
	 */
	public WebPage(WebDriver webDriver) {
		super(webDriver, CommandType.WEB.getCommand(webDriver), SearchElementType.WEB.getSearchElement(webDriver));
	}

	/**
	 * @return true if the current UI state is that which is represented by this
	 *         page object. false otherwise
	 */
	public abstract boolean isDisplayed();
}
package automacao.framework.browser.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import automacao.framework.browser.command.CommandAction;
import automacao.framework.browser.search.SearchElement;


public abstract class DefaultPage {

	static final Logger logger = Logger.getLogger(DefaultPage.class);

	protected WebDriver webDriver;
	protected CommandAction command;
	protected SearchElement searchElement;
	protected WebDriverWait webDriverWait;

	/**
	 * Default options to Page Objects
	 * 
	 * @param webDriver
	 * @param command
	 * @param searchElement
	 */
	public DefaultPage(WebDriver webDriver, CommandAction command, SearchElement searchElement) {
		this.webDriver = webDriver;
		this.webDriverWait = new WebDriverWait(webDriver, 30);
		this.command = command;
		this.searchElement = searchElement;
	}

	public byte[] takeScreenshot() throws Exception {
		return command.screenshot();
	}
}
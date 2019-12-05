package automacao.framework.iteraction.device.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.framework.iteraction.common.command.CommandAction;

public class CommandActionAppium extends CommandAction {
	
	/**
	 * Construtor
	 * @param webDriver
	 */
	public CommandActionAppium(WebDriver webDriver) {
		super(webDriver);
	}


	@Override
	public void navigateMenu(String newNavegation) {
		logger.warn("Not implemented");
		
	}


	@Override
	public void focus(WebElement webElement) {
		logger.warn("Focus element not available for Appium");
		
	}


	@Override
	public void highlight(WebElement webElement) {
		logger.warn("Highlight element not available for Appium");
		
	}


	@Override
	protected void screenshotReady() {
		logger.warn("Screenshot always allowed");
		
	}
}

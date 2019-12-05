package automacao.framework.iteraction.common.command;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.browser.command.CommandActionWeb;
import automacao.framework.iteraction.device.command.CommandActionAppium;

public enum CommandType {

	WEB {
		@Override
		public CommandAction getCommand(WebDriver webDriver) {
			return new CommandActionWeb(webDriver);
		}
	},
	APPIUM {
			@Override
			public CommandAction getCommand(WebDriver webDriver) {
				return new CommandActionAppium(webDriver);
			}
	};

	/**
	 * get command
	 * 
	 * @param webDriver
	 * @return
	 */
	public abstract CommandAction getCommand(WebDriver webDriver);

	public static CommandAction getCommand(CommandType commandType, WebDriver webDriver) {
		return commandType.getCommand(webDriver);
	}
}
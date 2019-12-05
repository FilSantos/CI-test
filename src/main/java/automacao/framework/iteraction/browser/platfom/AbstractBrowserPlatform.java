package automacao.framework.iteraction.browser.platfom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.common.platform.AbstractPlatform;

public abstract class AbstractBrowserPlatform extends AbstractPlatform {

	final static Logger logger = Logger.getLogger(AbstractBrowserPlatform.class);

	@Override
	public WebDriver getLocalWebDriver() {
		return null;
	}
}
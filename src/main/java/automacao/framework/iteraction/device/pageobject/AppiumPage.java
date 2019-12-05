package automacao.framework.iteraction.device.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import automacao.framework.iteraction.common.command.CommandType;
import automacao.framework.iteraction.common.pageobject.DefaultPage;
import automacao.framework.iteraction.common.search.SearchElementType;

public abstract class AppiumPage extends DefaultPage {
	
	private static final Logger logger = Logger.getLogger(AppiumPage.class);
	
	
	public AppiumPage(WebDriver webDriver){
		super(webDriver,
				CommandType.APPIUM.getCommand(webDriver),
				SearchElementType.APPIUM.getSearchElement(webDriver));
	}
	
	/**
	 * @return true if the current UI state is that which is represented by this page object. false otherwise
	 */
	public abstract boolean isDisplayed();
	
	/**
	 * @param timeout how long to wait in seconds
	 * TODO: Consider making this Exception based and returning void
	 * @return true if the current UI state is that which is represented by this page object. false otherwise
	 */
	public abstract boolean waitUntilDisplayed(long timeout);
	
	/**
	 * Accepts all alerts on the screen
	 * TODO: Consider moving this elsewhere...
	 * @throws Exception
	 */
	public void allowPopup() throws Exception {
		logger.info("Allow Alert, if present");
		while (command.isAlertPresent()) {
			command.sendAlert(true);
			Thread.sleep(1500);
		}
	}
	
	protected void waitForAlertAndTapAction(long timeout, int actionNumber) throws NoAlertPresentException{ 
		webDriverWait.until(ExpectedConditions.alertIsPresent());
		
		Alert alert = webDriver.switchTo().alert();
		
		//Note: Cancel is assumed to be the leftmost button for appium, based on iOS guidelines.
		try {
			if (actionNumber > 0) {
				alert.accept();
			} else {
				alert.dismiss();
			}
		} catch (WebDriverException e) {
			// HORRIBLE HACK to intercept errors caused by slow connections where the driver's CommandExecutor has an
			//instance of ApacheHTTPClient configured to retry POSTs automatically and sometimes causes the accept and
			//dismiss commands to be sent twice which in turn results in a messy unknown server-side error due to the
			//previous request eventually being executed and removing the alert the duplicated request attempts to
			//accept or dismiss.
			// Ideally we would check for a more specific exception but since none is available we check the message for
			//the error we are expecting
			if (e.getMessage().contains("An attempt was made to operate on a modal dialog when one was not open")) {
				return;
			}
			throw e;
		}
	    
	}
	
	public byte[] takeScreenshot() throws Exception {
		return command.screenshot();
	}
}

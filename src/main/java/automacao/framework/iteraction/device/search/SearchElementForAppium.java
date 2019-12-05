package automacao.framework.iteraction.device.search;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.common.search.SearchElement;

public class SearchElementForAppium extends SearchElement{
	
	/**
	 * Construtor
	 * @param webDriver
	 */
	public SearchElementForAppium (WebDriver webDriver) {
		super (webDriver);
		
	}

	@Override
	public void waitToBeReady() throws Exception {
//		
//		By improve = By.xpath("//XCUIElementTypeStaticText[@name='Improve your connection']");
//		WebElement alertImprove = null;
//		try {
//			alertImprove = webDriver.findElement(improve);
//			alertImprove.click();
//		}catch (Exception e) {
//				// do nothing
//		} 
//		
		
	}

	
}

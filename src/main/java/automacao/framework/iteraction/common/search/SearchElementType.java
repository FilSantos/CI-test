package automacao.framework.iteraction.common.search;

import org.openqa.selenium.WebDriver;

import automacao.framework.iteraction.browser.search.SearchElementForCustom;
import automacao.framework.iteraction.browser.search.SearchElementForWEB;
import automacao.framework.iteraction.device.search.SearchElementForAppium;

public enum SearchElementType {

	WEB {
		@Override
		public SearchElement getSearchElement(WebDriver webDriver) {
			return new SearchElementForWEB(webDriver);
		}
	},	
	CUSTOM {
			@Override
			public SearchElement getSearchElement(WebDriver webDriver) {
				return new SearchElementForCustom(webDriver);
			}
	},
	APPIUM {
		@Override
		public SearchElement getSearchElement(WebDriver webDriver) {
			return new SearchElementForAppium(webDriver);
		}
	
	};

	/**
	 * Get SearchElement
	 * 
	 * @param webDriver
	 * @return
	 */
	public abstract SearchElement getSearchElement(WebDriver webDriver);

	public static SearchElement getSearchElement(SearchElementType type, WebDriver webDriver) {
		return type.getSearchElement(webDriver);
	}
}
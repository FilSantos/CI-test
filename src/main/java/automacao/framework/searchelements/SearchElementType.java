package automacao.framework.searchelements;

import org.openqa.selenium.WebDriver;

public enum SearchElementType {

	WEB {
		@Override
		public SearchElement getSearchElement(WebDriver webDriver) {
			return new SearchElementForWEB(webDriver);
		}},
		
	SALESFORCE {
			@Override
			public SearchElement getSearchElement(WebDriver webDriver) {
				return new SearchElementForSalesForce(webDriver);
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
package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;

public enum AppsMenuItem {
	
	VIDA_APP("Vida", By.xpath("//a[@class='appTileTitleNoDesc' and text()='Vida']"));
	
	
	/**
	 * Name to use for identifying the tab batem in the report
	 */
	private final String reportName;
	
	/**
	 * Strategy for locating the item on screen.
	 */
	private final By locator;

	AppsMenuItem(String reportName, By locator) {
		this.reportName = reportName;
		this.locator = locator;
	}
    
    public By getLocator() {
        return locator;
    }
    
    public String getReportName() {
        return reportName;
    }
 
}

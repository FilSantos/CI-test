package br.com.testfilipe.core.selenium.searchelements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchElementForWEB extends SearchElement{
	
/**
 * 
 * @param webDriver
 */
	public SearchElementForWEB (WebDriver webDriver) {
		super (webDriver);
	}
	
	
	@Override
	public void waitToBeReady() throws Exception{

		//Wait for document be loaded
		new WebDriverWait(webDriver, 30).until((ExpectedCondition<Boolean>) wd ->
        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		
		   
	}

	
	
}

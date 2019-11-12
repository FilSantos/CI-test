package automacao.framework.searchelements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchElementForSalesForce extends SearchElement {

	/**
	 * 
	 * @param webDriver
	 */
	public SearchElementForSalesForce(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public void waitToBeReady() throws Exception {
		try {
			
			List<By> loaders = new ArrayList<By>();
			//SalesForce
			loaders.add(By.xpath("//div[@class='loader']"));
			loaders.add( By.xpath("//div[contains(@class,'LoadingBox')]"));
			loaders.add( By.xpath("//div[contains(@class,'slds-spinner')]"));
			// Corretor online
			loaders.add( By.xpath(".//*[contains(@class,'ps-ico-loading-modal')]"));
			
			webDriver.switchTo().defaultContent();
			
			new WebDriverWait(webDriver, 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
					.executeScript("return document.readyState").equals("complete"));
			invisibility(loaders);
			
			List<WebElement> iframes = webDriver.findElements(By.xpath("//iframe"));
			
			if (iframes.size() != 0) {
				for (WebElement iframe : iframes) {
					try {
						webDriver.switchTo().frame(iframe);
						invisibility(loaders);
					} catch (Exception e) {
					}
				}
			} 
		} catch (Exception e) {
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public void invisibility(List<By> loaders) {
		for (By by : loaders) {
			new WebDriverWait(webDriver, 30)
			.until(ExpectedConditions.invisibilityOfElementLocated(by));
		}
	}
}
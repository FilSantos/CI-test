package br.com.portoseguro.core.selenium.searchelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
/** Helper to get Objects for browser or devices
 * 
 * @author filipesantos
 *
 */
public abstract class SearchElement {

	final static Logger logger = Logger.getLogger(SearchElement.class);

	protected final static int TIME_OUT = 30;

	protected WebDriver webDriver;

	protected WebDriverWait webDriverWait;

	/**
	 * Constructor
	 * 
	 * @param webDriver
	 */
	public SearchElement(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.webDriverWait = new WebDriverWait(this.webDriver, TIME_OUT);
	}
	
	/**
	 * 
	 * @param name
	 * @param friendly
	 * @return
	 * @throws Exception
	 */
	public WebElement findElementBy(By by, String reportName) throws Exception {
		try {
			waitToBeReady();
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
			WebElement webElement = webDriver.findElement(by);
			WebElement temp = webElement;
			if (reportName != null && !reportName.trim().isEmpty()) {
				if (Reporter.getOutput().size() != 0){
					Reporter.log("<br>");
				}
				Reporter.log("- Localizado " + reportName.trim());
			}
			return temp;
		} catch (Exception e) {
			String errMsg = String.format(" Object %s not found with %s ",  reportName.trim() ,   by.toString()) ;
			logger.fatal(errMsg,e);
			throw new Exception(errMsg);
		}
		
	}
	
	
	/**
	 * 
	 * @param name
	 * @param friendly
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> findElementsBy(By by, String reportName) throws Exception {
		try {
			waitToBeReady();
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
			List<WebElement> webElements = webDriver.findElements(by);
			if (reportName != null && !reportName.trim().isEmpty()) {
				Reporter.log("-Localizado os itens " + reportName.trim());
			}
			
			return webElements;
		} catch (Exception e) {
			String errMsg = String.format("Object list %s not found with %s ",  reportName.trim() ,   by.toString()) ;
			logger.fatal(errMsg,e);
			throw new Exception(errMsg);
		}
	}
	
	
	
	/**
	 * 
	 * @param name
	 * @param friendly
	 * @return
	 * @throws Exception
	 */
	public WebElement findChildElementBy(WebElement parent, By by, String reportName) throws Exception {
		try {
			waitToBeReady();
			webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, by));
			WebElement webElement = parent.findElement(by);
			if (reportName != null && !reportName.trim().isEmpty()) {
				Reporter.log("-Localizados os objetos " + reportName.trim());
			}
			return webElement;
		} catch (Exception e) {
			String errMsg = String.format(" Object %s not found with %s ",  reportName.trim() ,   by.toString()) ;
			logger.fatal(errMsg,e);
			throw new Exception(errMsg);
		}
		
	}
	
	
	/**
	 * 
	 * @param name
	 * @param friendly
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> findChildElementsBy(WebElement parent, By by, String reportName) throws Exception {
		try {
			waitToBeReady();
			webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, by));			
			List<WebElement> webElements = parent.findElements(by);
			if (reportName != null && !reportName.trim().isEmpty()) {
				Reporter.log("-Localizados os objetos " + reportName.trim());
			}
			
			return webElements;
		} catch (Exception e) {
			String errMsg = String.format("Object list %s not found with %s ",  reportName.trim() ,   by.toString()) ;
			logger.fatal(errMsg,e);
			throw new Exception(errMsg);
		}
	}

	/**
	 * 
	 * @param Xpath
	 * @param friendly
	 * @return
	 */
	public Boolean exists(By by, String reportName) {
		return exists(by, reportName, TIME_OUT);
	}
	
	/**
	 * 
	 * @param xpath
	 * @param friendly
	 * @param timeWait
	 * @return
	 */
	public Boolean exists(By by, String reportName, long timeWait)  {

		try {
			WebDriverWait waitExists = new WebDriverWait(this.webDriver, timeWait);
			waitExists.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch(TimeoutException e) {
			String errorMsg = String.format(" Object %s not found with %s ", reportName.trim(), by.toString());
			logger.debug(errorMsg,e);
			return false;
		}
		
		logger.debug("Found object: " + reportName );
		return true;
	}

	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void waitToBeReady() throws Exception;

	public boolean existsNoLog(By by, String reportName, int timeWait) {
		try {
			waitToBeReady();
			WebDriverWait waitExists = new WebDriverWait(this.webDriver, timeWait);
			waitExists.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch(Exception e) {
			String errorMsg = String.format(" Object %s not found with %s ", reportName.trim(), by.toString());
			logger.debug(errorMsg);
			return false;
		}
		
		logger.debug("Found object: " + reportName );
		return true;
	}
	
}
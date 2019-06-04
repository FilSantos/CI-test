package br.com.testfilipe.test.salesforce;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.core.selenium.platfom.ChromePlatform;
import br.com.testfilipe.core.selenium.platfom.Platform;
import br.com.testfilipe.test.Iteracao.LoginSalesForce;
import br.com.testfilipe.test.pageobject.salesforce.Login;
import br.com.testfilipe.test.utils.SalesForceUtil;

public class PesquisaProposta {
	
	final static Logger logger = Logger.getLogger(PesquisaProposta.class);
	private static WebDriver webDriver; 
	private static LoginSalesForce login; 
	
	@BeforeSuite
	public static void iniciate() throws Exception {
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = ChromePlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();		
		login = new LoginSalesForce(webDriver);
		login.iniciaAutenticação();
		login.preencheLoginSF();
		login.tapAcessar();	
		
				
	}
	
	@Test
	
	public void test (){
		
		
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		
		webDriver.close();
		webDriver.quit();
				
	}
	
	

}
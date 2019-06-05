package br.com.testfilipe.test.Iteracao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.test.pageobject.gmail.LoginGmail;
import br.com.testfilipe.test.utils.PropertiesUtil;

public class LoginContaGmail extends BaseWebPage {

	
	private LoginGmail gmail;

		
		public LoginContaGmail(WebDriver webDriver) {
		super(webDriver);
		gmail = new LoginGmail (webDriver);
		
		}			
		
		@Override
		public boolean isDisplayed() {
			return false;
		}
		
		/**
		 * Validar autenticação no website do gmail
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void iniciaAutenticação  (){
			
			webDriver.navigate().to(PropertiesUtil.getProp("gmail.url"));
		}
		
		/**
		 * Validar login no gmail
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void preencheLoginGmail() throws Exception {
					
			WebElement email = gmail.email();
			command.send(email, PropertiesUtil.getProp("gmail.email"));							
		}
		
		
		public void nextButton()throws Exception{
			
			WebElement botaoNext = gmail.nextButton();
		}
		
		
		
		
}


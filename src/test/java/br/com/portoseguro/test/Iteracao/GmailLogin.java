package br.com.portoseguro.test.Iteracao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.gmail.LoginGmail;
import br.com.portoseguro.pageobject.salesforce.ConfirmacaoGmail;
import br.com.portoseguro.test.utils.PropertiesUtil;

public class GmailLogin extends BaseWebPage {

	
	private LoginGmail gmail;
	private ConfirmacaoGmail confirmacao; 

		
		public GmailLogin(WebDriver webDriver) {
		super(webDriver);
		gmail = new LoginGmail (webDriver);
		confirmacao = new ConfirmacaoGmail(webDriver);
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
			command.screenshot();
		}
		
		/**
		 * Validar login no gmail com senha
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void preencheLoginSenha() throws Exception {
					
			WebElement senha = gmail.senha();
			command.send(senha, PropertiesUtil.getProp("gmail.password"));
			command.screenshot();
		}
		
		
		/**
		 * Botao Next
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void nextButton()throws Exception{
			
			WebElement botaoNext = gmail.nextButton();
			command.click(botaoNext);
		}
		
		/**
		 * Botao continuar
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void btnContinuar()throws Exception{
			if (!(webDriver instanceof PhantomJSDriver) ) {
				WebElement botaoContinuar = confirmacao.btnContinuar();
				command.click(botaoContinuar);
				
			}

		}
		
		
		
		
}


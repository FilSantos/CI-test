package br.com.portoseguro.test.Iteracao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.Login;
import br.com.portoseguro.test.utils.PropertiesUtil;

public class SalesForceLogin extends BaseWebPage {

	private Login login;

		
		public SalesForceLogin(WebDriver webDriver) {
		super(webDriver);
		login = new Login (webDriver);
		
		}			
		
		@Override
		public boolean isDisplayed() {
			return false;
		}
		
		/**
		 * Validar autenticação no website SF 
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void iniciaAutenticação (){
			
			webDriver.navigate().to(PropertiesUtil.getProp("web.url"));
		}
		
		/**
		 * Validar Login no SF 
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void preencheLoginSF() throws Exception {
					
			WebElement usuario = login.user();
			command.send(usuario, PropertiesUtil.getProp("web.usuario"));
			WebElement senha = login.password();
			command.send(senha, PropertiesUtil.getProp("web.senha"));
			
				
		}
		/**
		 * Validar campo esqueci senha 
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */		

		public void tapEsqueciSenha() throws Exception {
			WebElement esqueciSenha = login.esqueciSenha();
			command.click(esqueciSenha);
			command.screenshot();
		}
		
		/**
		 * Validar botão acessar 
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void tapAcessar() throws Exception {
			WebElement acessar = login.logarSF();
			command.screenshot();
			command.click(acessar);
			
		}
		
		
		/**
		 * Valida se foi exibido mensagem de erro de autenticacao
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void erroMessage() throws Exception {

			if ( login.existerroLogin()) {
				command.screenshot();
				Assert.fail("Erro ao logar no SalesForce");
			} 
		
		}
			
		
		/**
		 * Validar botão limpar usuário indisponível 
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void clearUsername(boolean avisaErro) throws Exception{
			
			if (login.existsClearButton()) {
				
				WebElement btnLimpa = login.btnClear();
				command.click(btnLimpa);
				
			}else {
				if (avisaErro) {
					command.screenshot();
					Assert.fail("Limpar usuário não disponível");
				}
			}
			
		}
		
		
		}

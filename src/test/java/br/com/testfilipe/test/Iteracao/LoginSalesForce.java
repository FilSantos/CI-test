package br.com.testfilipe.test.Iteracao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.test.pageobject.salesforce.Login;
import br.com.testfilipe.test.utils.PropertiesUtil;

public class LoginSalesForce extends BaseWebPage {

	private Login login;

		
		public LoginSalesForce(WebDriver webDriver) {
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

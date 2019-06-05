package br.com.testfilipe.test.Iteracao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.test.pageobject.gmail.AutenticacaoPorto;
import br.com.testfilipe.test.utils.PropertiesUtil;

public class LoginAutenticacaoPorto extends BaseWebPage {

	
	private AutenticacaoPorto porto;

		
		public LoginAutenticacaoPorto(WebDriver webDriver) {
		super(webDriver);
		porto = new AutenticacaoPorto (webDriver);
		
		}			
		
		@Override
		public boolean isDisplayed() {
			return false;
		}
		
			
		/**
		 * Validar login no autenticador porto com usuário e senha
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		public void preencheUserPasswordPorto() throws Exception {
					
			WebElement userPorto = porto.userPorto();
			command.send(userPorto, PropertiesUtil.getProp("gmail.usuario"));
			WebElement passwordPorto = porto.PasswordPorto();
			command.send(passwordPorto, PropertiesUtil.getProp("gmail.senha"));
		}
		
		
		/**
		 * Validar botão sign in
		 * @author 634111 - Bruno Silva
		 * @throws Exception
		 */
		
		public void signIn()throws Exception{
			
			WebElement singIn = porto.signIn();
		}
				
		
}


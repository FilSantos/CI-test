package br.com.portoseguro.test.iteracao;

import java.util.Date;

import org.openqa.selenium.WebDriver;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.core.selenium.platfom.PhantomJSPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;

public class LoginIntegradoSFGmail extends BaseWebPage {

	private static GmailLogin loginGmail; 
	private static PortoLogin loginAutenticacao;
	private static GmailInbox emails;
	private static SalesForceLogin loginSF;
	private static InserirToken token;		
	public LoginIntegradoSFGmail(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void login() throws Exception{
				
		loginSF = new SalesForceLogin(webDriver);
		token = new InserirToken(webDriver);

		loginSF.iniciaAutenticação();
		loginSF.preencheLoginSF();
		loginSF.tapAcessar();
		loginSF.erroVerificacao();
		loginSF.erroMessage();
		
		if (token.isDisplayed()){
			Platform authPlat = PhantomJSPlatform.StartWebDriver();
			WebDriver webDriverGmail = authPlat.getLocalWebDriver();
			emails = new GmailInbox(webDriverGmail);
			loginGmail = new GmailLogin(webDriverGmail);
			loginAutenticacao = new PortoLogin(webDriverGmail);
			loginGmail.iniciaAutenticação();
			loginGmail.preencheLoginGmail();
			loginGmail.nextButton();
			loginAutenticacao.preencheUserPasswordPorto();
			loginAutenticacao.signIn();
			loginGmail.btnContinuar();	
			emails.selecionaEmail("noreply@salesforce", "Sandbox: Verifique sua identidade no Salesforce" ,new Date ());
			token.tokenPorto(emails.recuperaToken());
			
			webDriverGmail.close();
			webDriverGmail.quit();
		}


		
	}
	
	
}

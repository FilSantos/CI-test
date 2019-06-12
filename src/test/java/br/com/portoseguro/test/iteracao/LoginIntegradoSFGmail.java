package br.com.portoseguro.test.iteracao;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.core.selenium.platfom.PhantomJSPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;

public class LoginIntegradoSFGmail extends BaseWebPage {

	private static GmailLogin loginGmail; 
	private static PortoLogin loginAutenticacao;
	private static GmailInbox emails;
	private static SalesForceLogin loginSF;
	private static InserirToken token;	
	private static WebDriver webDriverGmail;	
	private static String erroThread = "";

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
		
		Platform authPlat = PhantomJSPlatform.StartWebDriver();
		webDriverGmail = authPlat.getLocalWebDriver();	
		
		loginGmail = new GmailLogin(webDriverGmail);
		loginAutenticacao = new PortoLogin(webDriverGmail);
		emails = new GmailInbox(webDriverGmail);		
		loginSF = new SalesForceLogin(webDriver);
		token = new InserirToken(webDriver);

		Thread authSF = new Thread("Aut SF") {
			public void run(){
				
				try {
					loginSF.iniciaAutenticação();
					loginSF.preencheLoginSF();
					loginSF.tapAcessar();
					loginSF.erroVerificacao();
					loginSF.erroMessage();
				} catch (Exception e) {
					erroThread = erroThread + "Falha na autenticacao do Sales Force";
				}
				
			}
		};
		
		Thread authGoogle = new Thread("Aut gmail") {
			public void run(){

				try {
					loginGmail.iniciaAutenticação();
					loginGmail.preencheLoginGmail();
					loginGmail.nextButton();
					loginAutenticacao.preencheUserPasswordPorto();
					
				} catch (Exception e) {
					erroThread = erroThread + "Falha na autenticacao do GMAIL";
				}
				
			}
		};
		
		Thread[] auth = new Thread[2];
		
		auth[0] = authGoogle;
		auth[1] = authSF;
		
		for (int i = 0; i < auth.length; i++) {
			auth[i].start();
		}
		
		for (int i = 0; i < auth.length; i++) {
			auth[i].join();
		}
		
		Assert.assertEquals(erroThread, "");
		loginAutenticacao.signIn();
		loginGmail.btnContinuar();	
		emails.selecionaEmail("noreply@salesforce", "Sandbox: Verifique sua identidade no Salesforce" ,new Date ());
		token.tokenPorto(emails.recuperaToken());
		
		webDriverGmail.close();
		webDriverGmail.quit();
		
	}
	
	
}

package br.com.portoseguro.test.GmailTeste;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import br.com.portoseguro.core.database.H2sql;
import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.selenium.platfom.PhantomJSPlatform;
import br.com.portoseguro.core.selenium.platfom.Platform;
import br.com.portoseguro.test.Iteracao.HorarioEmails;
import br.com.portoseguro.test.Iteracao.LoginAutenticacaoPorto;
import br.com.portoseguro.test.Iteracao.LoginContaGmail;

public class GmailTeste {
	
	final static Logger logger = Logger.getLogger(GmailTeste.class);
	private static WebDriver webDriver; 
	private static LoginContaGmail loginGmail; 
	private static LoginAutenticacaoPorto loginAutenticacao;
	private static HorarioEmails emails;
	
	

	@BeforeSuite
	public static void iniciate() throws Exception {
	   		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);	
		Platform platformWebDriver = PhantomJSPlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();			
		H2sql.openConnection();			
				
	}
	
	@SuppressWarnings("deprecation")
	@Test
	
	public void test () throws Exception{
		String remetente = "Filipe Santos";
		String assunto = "Fwd: Sandbox: Verifique sua identidade no Salesforce";
		String data = "13:32";
		
		SimpleDateFormat formato = new SimpleDateFormat("dd MMM");
		SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm");
		Date horario;
		try {
			horario = formato.parse(data);
		} catch (Exception e) {
			horario = formato2.parse(data);
			horario.setDate(new Date().getDate());
			horario.setMonth(new Date().getMonth());
			horario.setYear(new Date().getYear());
		}
		
		loginGmail = new LoginContaGmail(webDriver);
		loginAutenticacao = new LoginAutenticacaoPorto(webDriver);
		emails = new HorarioEmails(webDriver);
		
		loginGmail.iniciaAutenticação();
		loginGmail.preencheLoginGmail();
		loginGmail.nextButton();
		loginGmail.preencheLoginSenha();
		loginGmail.nextButton();
		//loginAutenticacao.preencheUserPasswordPorto();
		//loginAutenticacao.signIn();
		emails.selecionaEmail(remetente, assunto ,horario);
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(emails.recuperaTextoEmail());
		while(m.find()) {
            System.out.println(m.group());
        }
		
	}
	
	
	@AfterSuite
	public static void teardown() {
		webDriver.close();
		webDriver.quit();		
				
	}
}

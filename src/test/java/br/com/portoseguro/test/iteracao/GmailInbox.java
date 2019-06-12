package br.com.portoseguro.test.iteracao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.gmail.GmailMailbox;


public class GmailInbox extends BaseWebPage {

	private GmailMailbox gmail;

	public GmailInbox(WebDriver webDriver) {
		super(webDriver);
		gmail = new GmailMailbox (webDriver);
	}

	/** Quando encontrar por algum dos parametros, clicar no item
		 * @author 786472 - Filipe Santos
		 * @param remetente
		 * @param assunto
		 * @param horario
		 * @throws Exception 
		 */
		@SuppressWarnings("deprecation")
		public void selecionaEmail(String remetente, String assunto, Date horario) throws Exception{

			SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
			SimpleDateFormat formato2 = new SimpleDateFormat("MMM dd");
			Date dataEmail = null;

			boolean encontrou = false;
			List<WebElement> emailLista;
			
			command.screenshot();
			int iteradorPhantom = 1;
			emailLista = gmail.emailLista();
		
			for (WebElement emailDetalhe : emailLista) {
			
				String [] email = emailDetalhe.getText().split("\n");

				if(email[0].contains(remetente) | assunto.equals(email[1])){
					
					int colunaHorario = email.length -1;
					try {
						dataEmail = formato.parse(email[colunaHorario]);
						dataEmail.setDate(new Date().getDate());
						dataEmail.setMonth(new Date().getMonth());
						dataEmail.setYear(new Date().getYear());
						
					} catch (Exception e) {
						dataEmail = formato2.parse(email[colunaHorario].trim());
						
					}
				
					long diffminutes = (dataEmail.getTime() - horario.getTime())/ (60 * 1000);
					if(diffminutes <= 1){
						if (webDriver instanceof PhantomJSDriver) {
							emailDetalhe = webDriver.findElement(By.xpath("//table[@class='th']//tbody/tr["+iteradorPhantom+"]//td[3]/..//a"));							
							webDriver.navigate().to(emailDetalhe.getAttribute("href"));
						} else {
							command.click(emailDetalhe);
						}
						
						encontrou=true;
						break;						
					}					
				}
				iteradorPhantom++;
			}
			
			Assert.assertEquals(encontrou, true,"Encontrou email de Autenticação?");			
			
		}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public String recuperaTextoEmail() throws Exception {
		return gmail.getEmailContent().getText();
	}
	
	/**Recupera o token no email da SalesForce utilizado para autenticacao
	 * @author Filipe Santos (Cognizant)
	 * @return
	 * @throws Exception 
	 */
	public String recuperaToken() throws Exception{
		Pattern p = Pattern.compile("\\d{5}");
		String texto = recuperaTextoEmail();
		Matcher m = p.matcher(texto);
		while(m.find()) {
            return(m.group());
        }
		return null;
	}
	
	/**
	 * Realizar exclusao de email ja utilizado no gmail
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void excluirEmailGmail() throws Exception {
		List<WebElement> emails = gmail.getExluirEmail();
						
		for (WebElement emailsExcluir : emails) {
			if ( emailsExcluir.isDisplayed()) {
				command.click(emailsExcluir);
			}
			
		}
	}
}

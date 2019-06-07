package br.com.portoseguro.test.Iteracao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.gmail.GmailInbox;

public class HorarioEmails  extends BaseWebPage {

	private GmailInbox gmailInbox;
	
	public HorarioEmails(WebDriver webDriver) {
		super(webDriver);
		gmailInbox = new GmailInbox(webDriver);
	}
			
		/** Quando encontrar por algum dos parametros, clicar no item
		 * @author 786472 - Filipe Santos
		 * @param remetente
		 * @param assunto
		 * @param horario
		 * @throws Exception 
		 */
		public void Selecionaemail(String remetente, String assunto, Date horario) throws Exception{

			SimpleDateFormat formato = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat formato2 = new SimpleDateFormat("dd MMM");
			Date dataEmail;

			boolean encontrou = false;
			
			List<WebElement> emailLista = gmailInbox.emailLista();
			
			for (WebElement emailDetalhe : emailLista) {
				
				WebElement emailHorario = gmailInbox.emailDetalheHorario(emailDetalhe);
				WebElement emailRemetente = gmailInbox.emailName(emailDetalhe);
				WebElement emailAssunto = gmailInbox.emailContent(emailDetalhe);
				
				String horarioEmail = command.getText(emailHorario);
				String remetenteEmail = command.getText(emailRemetente);
				String assuntoEmail = command.getText(emailAssunto);
				
				try {
					dataEmail = formato.parse(horarioEmail);
					
				} catch (Exception e) {
					dataEmail = formato2.parse(horarioEmail);
				}
				
				if(remetente.equals(remetenteEmail) | assunto.equals(assuntoEmail)){
					long diffminutes = (horario.getTime() - dataEmail.getTime())/ (60 * 1000);
					if(diffminutes <= 1){
						command.click(emailDetalhe);
						encontrou=true;
						break;						
					}					
				}								
			}
			
			Assert.assertEquals(encontrou, true,"Encontrou email de Autenticação?");
			
		
			if (!encontrou){
				Assert.fail("Não encontrou o email de autenticacao");
			}			
			
		}		

		@Override
		public boolean isDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
	
	
}

package br.com.portoseguro.test.Iteracao;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
			
			List<WebElement> emailLista = gmailInbox.emailLista();
			
			for (WebElement emailDetalhe : emailLista) {
				
				WebElement emailHorario = gmailInbox.emailDetalheHorario(emailDetalhe);
			
				command.getText(emailHorario);
								
			}
		}

		@Override
		public boolean isDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
	
	
}

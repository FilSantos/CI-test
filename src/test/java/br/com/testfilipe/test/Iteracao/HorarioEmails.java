package br.com.testfilipe.test.Iteracao;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import br.com.testfilipe.test.pageobject.gmail.GmailInbox;

public class HorarioEmails extends BaseWebPage{
	
	private GmailInbox gmailInbox;

	public HorarioEmails(WebDriver webDriver) {
		super(webDriver);
		gmailInbox = new GmailInbox(webDriver);
		
		}			
		@Override
		public boolean isDisplayed() {
			return false;
		}
		
		public void listaEmail() throws Exception{
			
			List<WebElement> emailLista = gmailInbox.emailLista();
			
			for (WebElement emailDetalhe : emailLista) {
				
				WebElement emailHorario = gmailInbox.emailDetalheHorario(emailDetalhe);
								
			}
		}
		
		
	
	
}

package br.com.portoseguro.test.Iteracao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.gmail.GmailInbox;

public class HorarioEmails extends BaseWebPage {

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
		@SuppressWarnings("deprecation")
		public void selecionaEmail(String remetente, String assunto, Date horario) throws Exception{

			SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
			SimpleDateFormat formato2 = new SimpleDateFormat("MMM dd");
			Date dataEmail = null;

			boolean encontrou = false;
			List<WebElement> emailLista;
			
			
			int iteradorPhantom = 1;
			emailLista = gmailInbox.emailLista();
		
			for (WebElement emailDetalhe : emailLista) {
			
				String [] email = emailDetalhe.getText().split("\n");

				if(remetente.contains(email[0]) | assunto.equals(email[1])){
					
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
		return gmailInbox.getEmailContent().getText();
	}

}

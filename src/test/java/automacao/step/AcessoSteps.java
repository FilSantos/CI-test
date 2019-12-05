package automacao.step;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.pageobject.brqmobile.Acesso;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class AcessoSteps{
	
	private Acesso acesso;
	
	@Dado("^instalado aplicação Portal BRQ$")
	public void instalado_aplicação_Portal_BRQ() throws Exception {
		ConstantsMobile.startAppiumBRQRemote();
		WebDriver driver = ConstantsMobile.getAppiumBRQ();
		acesso = new Acesso(driver);
	}

	@Dado("^exibido tela incial$")
	public void exibido_tela_incial() throws Exception {
		Thread.sleep(4000);
	    try {
			acesso.acessar();
		} catch (Exception e) {
			Assert.fail("Tela inicial não localizada");
		}
	}

	@Quando("^informar usuário \"([^\"]*)\" e senha \"([^\"]*)\"\\.$")
	public void informar_usuário_e_senha(String user, String pwd) throws Exception {
	    WebElement usuario = acesso.usuario();
	    WebElement senha = acesso.senha();
	    
	    usuario.clear();
	    usuario.sendKeys(user);
	    
	    senha.clear();
	    senha.sendKeys(pwd);
	    acesso.acessar().click();
	}

	@Entao("^obtenho o seguinte resultado \"([^\"]*)\"$")
	public void obtenho_o_seguinte_resultado(String status) {
		boolean resultado;
	    try {
			acesso.erro();
	    	resultado = true;
		} catch (Exception e) {
			resultado = false;
		}
	    
	    Assert.assertEquals("Mensagem de Erro",status.equals("Erro"), resultado);
	}


	
}

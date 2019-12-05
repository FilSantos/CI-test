package automacao.pageobject.brqmobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Acesso {

	private By USUARIO = By.xpath(".//*[@resource-id='com.brq.pontobrq:id/txtUsuarioTradicional']");
	private By SENHA = By.xpath(".//*[@resource-id='com.brq.pontobrq:id/txtSenhaTradicional']");
	private By ACESSAR = By.xpath(".//*[@resource-id='com.brq.pontobrq:id/imgFoward']");
	private By VERSAO = By.xpath(".//*[@resource-id='com.brq.pontobrq:id/txvVersaoApp']");
	private By ERRO = By.xpath(".//*[@resource-id='android:id/message'] | .//*[@resource-id='com.brq.pontobrq:id/textinput_error']");
	
	private WebDriver driver;
	
	public Acesso(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement usuario() throws Exception {
		return driver.findElement(USUARIO);
	}
	public WebElement senha() throws Exception {
		return driver.findElement(SENHA);
	}
	public WebElement acessar() throws Exception {
		return driver.findElement(ACESSAR);
	}
	public WebElement erro() {
		return driver.findElement(ERRO);
	}
}

package br.com.portoseguro.pageobject.salesforce;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;



/**
 * Page Object da pagina de Login de Contratacao
 * @author Bruno Silva(Cognizant)
 *
 */
public class Contratacao extends BaseWebPage{

	public Contratacao(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static By Channel_Choose = By.id("canalEntrada");
	private static By Channel_Choosed_Corretor = By.xpath("//li/span[contains(text(),'Corretor')]");
	private static By Channel_Choosed_Clientes = By.xpath("//li/span[contains(text(),'Clientes')]");
	private static By Campo_Susep = By.id("susep");
	private static By Campo_Cpf = By.id("cpf");
	private static By Identification = By.id("nome");
	private static By portoButton = By.xpath("//input[@value = 'Entrar']");
	private static By contractType = By.xpath("//section[@id='etapa1']//input");
	private static By typedContractedForYou = By.xpath("//li/span[contains(text(),'Plano para você')]");
	private static By typedContractedForFamily = By.xpath("//li/span[contains(text(),'Plano para sua família')]");
	private static By typedContractedForAll = By.xpath("//li/span[contains(text(),'Plano para todos')]");
	private static By BtnNextStage = By.xpath("//input[@value = 'Próxima etapa']");
	private static By Complet_Name = By.id("NOMECOMPLETO");
	private static By Nascimento = By.id("NASCIMENTO");
	private static By Profissao = By.id("PROFISSAO");
	private static By Sexo = By.id("");
	private static By Masculino = By.id("");
	private static By Feminino = By.id("");
	private static By FumanteSim = By.id("");
	private static By FumanteNao = By.id("");
	private static By EstadoCivil = By.id("");
	private static By EstadoSolteiro = By.id("");
	private static By EstadoCasado = By.id("");
	private static By EstadoDivorciado = By.id("");
	private static By EstadoViuvo = By.id("");
	private static By btnVoltar = By.id("");
	private static By RendaMensal = By.id("");
	private static By Renda4000 = By.id("");
	private static By Renda4001 = By.id("");
	private static By Renda8001 = By.id("");
	private static By Renda12001 = By.id("");
	private static By RendaAcima16000 = By.id("");
	private static By BtnEnviar = By.id("");
	private static By PlanoInferior = By.id("");
	private static By PlanoMedio = By.id("");
	private static By PlanoSuperior = By.id("");
	private static By BtnConfirmar = By.id("");
	private static By PersonalizePlanoInferior = By.id("");
	private static By PersonalizePlanoMedio = By.id("");
	private static By PersonalizePlanoSuperior = By.id("");
	
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Campo de Selecionar o canal
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement selecionarCanal() throws Exception{
		
		return searchElement.findElementBy(Channel_Choose, "Selecionar o canal");
	}
	
	/**
	 * Campo de canal selecionado - corretor
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement canalSelecionadoCorretor() throws Exception{
		
		return searchElement.findElementBy(Channel_Choosed_Corretor, "Canal selecionado - Corretor");
	}
	
	/**
	 * Campo de canal selecionado - clientes
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement canalSelecionadoClientes() throws Exception{
		
		return searchElement.findElementBy(Channel_Choosed_Clientes, "Canal selecionado - Clientes");
	}
	
	
	/**
	 * Campo de identificação SUSEP - Corretor
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement campoSusep() throws Exception{
		
		return searchElement.findElementBy(Campo_Susep, "Identificação Susep informada");
	}
	
	/**
	 * Campo de identificação CPF - Cliente
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement campoCpf() throws Exception{
		
		return searchElement.findElementBy(Campo_Cpf, "Identificação CPF informado");
	}
	
	/**
	 * Botão entrar
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement portoButton() throws Exception{
		
		return searchElement.findElementBy(portoButton, "Botão de entrar");
	}
	
	
	
	/**
	 * Campo de selecionar tipo de plano para você
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForYou() throws Exception{
		
		return searchElement.findElementBy(typedContractedForYou, "Tipo de contrato para você");
	}
	
	/**
	 * Campo de selecionar tipo de plano para família
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForFamily() throws Exception{
		
		return searchElement.findElementBy(typedContractedForFamily, "Tipo de contrato para família");
	}
	
	/**
	 * Campo de selecionar tipo de plano para todos
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement typedContractForAll() throws Exception{
		
		return searchElement.findElementBy(typedContractedForAll, "Tipo de contrato para todos");
	}
	
	/**
	 * Botão para próxima etapa
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement nextStage() throws Exception{
		
		return searchElement.findElementBy(BtnNextStage, "Botão próxima etapa");
	}
	
	/**
	 * Campo Nome Completo
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	public WebElement completo_Nome() throws Exception{
		
		return searchElement.findElementBy(Complet_Name, "Campo Nome Completo");
	}
	
	/**
	 * Campo data de nascimento
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement nascimento() throws Exception {
		
		return searchElement.findElementBy(Nascimento, "Data de nascimento");
	}
	
	
	/**
	 * Campo Profissão
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement profissao () throws Exception {
		
		return searchElement.findElementBy(Profissao, "Campo de profissão");
		
	}
	
	/**
	 * Campo Sexo
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement sexo () throws Exception {
		
		return searchElement.findElementBy(Sexo, "Campo de sexo");
		
	}
	
	/**
	 * Campo sexo - Masculino
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement sexoMasculino () throws Exception {
		
		return searchElement.findElementBy(Masculino, "Campo de sexo - Masculino selecionado");
		
	}
	
	/**
	 * Campo sexo - Feminino
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement sexoFeminino () throws Exception {
		
		return searchElement.findElementBy(Feminino, "Campo de sexo - Feminino selecionado");
		
	}
	
	/**
	 * Campo de seleção - fumante Sim
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement fumanteSim () throws Exception {
		
		return searchElement.findElementBy(FumanteSim, "Campo de seleção - fumante Sim");
		
	}
	
	/**
	 * Campo de seleção - fumante Não
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement fumanteNao () throws Exception {
		
		return searchElement.findElementBy(FumanteNao, "Campo de seleção - fumante Não");
		
	}
	
	/**
	 * Campo de Estado Civil
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement estadoCivil () throws Exception {
		
		return searchElement.findElementBy(EstadoCivil, "Campo de estado civil");
		
	}
	
	/**
	 * Campo de Estado Civil - Solteiro
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement estadoSolteiro () throws Exception {
		
		return searchElement.findElementBy(EstadoSolteiro, "Campo de estado civil - Solteiro");
		
	}
	
	/**
	 * Campo de Estado Civil - Casado
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement estadoCasado () throws Exception {
		
		return searchElement.findElementBy(EstadoCasado, "Campo de estado civil - Casado");
		
	}
	
	/**
	 * Campo de Estado Civil - Divorciado
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement estadoDivorciado () throws Exception {
		
		return searchElement.findElementBy(EstadoDivorciado, "Campo de estado civil - Divorciado");
		
	}
	
	/**
	 * Campo de estado civil - Viuvo
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement estadoViuvo () throws Exception {
		
		return searchElement.findElementBy(EstadoViuvo, "Campo de estado civil - Viuvo");
		
	}
	
	/**
	 * Botão Voltar
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement btnVoltar () throws Exception {
		
		return searchElement.findElementBy(btnVoltar, "Botão Voltar");
		
	}
	
	/**
	 * Campo de renda mensal
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement rendaMensal () throws Exception {
		
		return searchElement.findElementBy(RendaMensal, "Campo de renda mensal");
		
	}
	
	/**
	 * Campo de renda mensal - R$4.000,00
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement renda4000 () throws Exception {
		
		return searchElement.findElementBy(Renda4000, "Campo de renda mensal - Até R$4.000,00");
		
	}
	
	/**
	 * Campo de renda mensal - R$4.000,01 a R$8.000,00
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement renda4001 () throws Exception {
		
		return searchElement.findElementBy(Renda4001, "Campo de renda mensal - R$4.000,01 a R$8.000,00");
		
	}
	
	/**
	 * Campo de renda mensal - R$8.000,01 a R$12.000,00
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement renda8001 () throws Exception {
		
		return searchElement.findElementBy(Renda8001, "Campo de renda mensal - R$8.000,01 a R$12.000,00");
		
	}
	
	/**
	 * Campo de renda mensal - R$12.000,01 a R$16.000,00
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement renda12001() throws Exception {
		
		return searchElement.findElementBy(Renda12001, "Campo de renda mensal - R$12.000,01 a R$16.000,00");
		
	}
	
	/**
	 * Campo de renda mensal - Acima de R$16.000,00
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement rendaAcima16000 () throws Exception {
		
		return searchElement.findElementBy(RendaAcima16000, "Campo de renda mensal - Acima de R$16.000,00");
		
	}
	
	/**
	 * Botão Enviar
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement btnEnviar () throws Exception {
		
		return searchElement.findElementBy(BtnEnviar, "Botão Enviar");
		
	}
	
	/**
	 * Plano Inferior
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement planoInferior () throws Exception {
		
		return searchElement.findElementBy(PlanoInferior, "Plano Inferior");
		
	}
	
	/**
	 * Plano Medio
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement planoMedio () throws Exception {
		
		return searchElement.findElementBy(PlanoMedio, "Plano Medio");
		
	}
	
	/**
	 * Plano Superior
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement planoSuperior () throws Exception {
		
		return searchElement.findElementBy(PlanoSuperior, "Plano Superior");
		
	}
	
	/**
	 * Botão Confirmar
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement btnConfirmar () throws Exception {
		
		return searchElement.findElementBy(BtnConfirmar, "Botão confirmar");
		
	}
	
	/**
	 * Opção de Personalize Plano Inferior
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement personalizarPlanoInferior () throws Exception {
		
		return searchElement.findElementBy(PersonalizePlanoInferior, "Personalize Plano Inferior");
		
	}
	
	/**
	 * Personalize Plano Medio
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement personalizarPlanoMedio () throws Exception {
		
		return searchElement.findElementBy(PersonalizePlanoMedio, "Personalize Plano Medio");
		
	}
	
	/**
	 * Personalize Plano Superior
	 * @author Bruno Silva(Cognizant)
	 * @return
	 * @throws Exception
	 */	
	
	
	public WebElement personalizarPlanoSuperior () throws Exception {
		
		return searchElement.findElementBy(PersonalizePlanoSuperior, "Personalize Plano Superior");
		
	}
	
	
	
	
	
	
	
	
	
}
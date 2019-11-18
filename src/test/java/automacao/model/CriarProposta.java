package automacao.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automacao.framework.browser.pageobject.WebPage;
import automacao.pageobject.CriarPropostaPO;

public class CriarProposta extends WebPage {

	private CriarPropostaPO proposta;
	
	
	public CriarProposta(WebDriver webDriver) {
		super(webDriver);
		proposta = new CriarPropostaPO(webDriver);
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void simulacaoValorCompra(String valorVista, String valorEntrada, String dataParcela, String produto,
			String tabelaComercial, Integer plano, String vendedor, String cpf, String observacao) throws Exception {
		command.click(proposta.calculoValorCompra());
		WebElement vlVista = proposta.valorVista();
		command.clear(vlVista);
		command.send(vlVista, valorVista);
		if (valorEntrada != null){
			WebElement vlEntrada = proposta.valorEntrada();
			command.clear(vlEntrada);
			command.send(vlEntrada, valorEntrada);	
		}
		if (dataParcela != null){
			WebElement data = proposta.dataPrimeiraParcela();
			command.clear(data);
			command.send(data, dataParcela);
		}
		command.send(proposta.nomeVendedor(),vendedor);
		command.send(proposta.observacao(), observacao);
		command.selectOptionText(proposta.produto(), produto);
		Thread.sleep(4000);
		command.selectOptionText(proposta.tabelaComercial(), tabelaComercial);
		command.selectOptionIndex(proposta.qtdeParcelas(), plano);
		command.screenshot();
		WebElement cpfCliente = proposta.cpfCliente();
		command.clear(cpfCliente);
		command.send(cpfCliente, cpf);
		command.screenshot();
		command.click(proposta.pesquisaCliente());
		String nomeCliente = proposta.nomeCliente().getText();
		quebraCritica();		
		command.screenshot();
		command.click(proposta.enviarProposta());
		quebraCritica();
		command.screenshot();
		
	}

	private void quebraCritica() throws Exception {
		if (proposta.erroTextoExiste()){
			String mensagemErro = proposta.erroTexto().getText();
			command.screenshot();
			
			if(mensagemErro.equals("É necessário atualizar o cadastro do Cliente.")){
			
				command.click(proposta.btnOK());
				command.click(proposta.atualizaCliente());
				command.screenshot();
				command.click(proposta.salvarCliente());
				return;
				
			}else if(mensagemErro.equals("Você esqueceu de tirar a foto do cliente. Deseja enviar a proposta mesmo assim?")){
			
				command.click(proposta.btnOK());
				return;
				
			}else{
				
				Assert.fail(mensagemErro);
			}
		}
	}
	
	

}

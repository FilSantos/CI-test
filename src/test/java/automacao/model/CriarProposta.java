package automacao.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
			String tabelaComercial, String plano, String vendedor, String cpf, String observacao) throws Exception {
		//command.click(proposta.calculoValorCompra());
		command.send(proposta.valorVista(), valorVista);
		if (valorEntrada != null){
			command.send(proposta.valorEntrada(), valorEntrada);	
		}
		if (dataParcela != null){
			WebElement data = proposta.dataPrimeiraParcela();
			command.clear(data);
			command.send(data, dataParcela);
		}
		command.send(proposta.nomeVendedor(),vendedor);
		command.send(proposta.observacao(), observacao);
		command.selectOption(proposta.produto(), produto);
		Thread.sleep(4000);
		command.selectOption(proposta.tabelaComercial(), tabelaComercial);
		Thread.sleep(4000);
		command.selectOption(proposta.qtdeParcelas(), plano);
		command.screenshot();
		command.send(proposta.cpfCliente(), cpf);
		command.screenshot();
		command.click(proposta.enviarProposta());
		command.screenshot();
	}
	
	

}

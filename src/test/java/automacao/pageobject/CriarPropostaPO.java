package automacao.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automacao.framework.browser.pageobject.WebPage;

public class CriarPropostaPO extends WebPage{

	private By VALORCOMPRA =  By.id("linkCalculoNormal");
	private By CALCULOREVERSO =  By.id("linkCalculoReverso");
	private By VALORAVISTA =  By.id("ValorAVista");
	private By VALORENTRADA =  By.id("ValorEntrada");
	private By VALORSOLICITADO =  By.id("ValorSolicitado");
	private By DATAPARCELA =  By.id("DataParcela");
	private By CODIGOTIPOOPERACAO =  By.id("CodigoTipoOperacao");
	private By TABELACOMERCIAL =  By.id("TabelaComerc_CodigoTabelaComercial");
	private By QTDEPARCELAS =  By.id("QtdparcelaS");
	private By FORMAPAGAMENTO =  By.id("FormaPagamento");
	private By NOMEVENDEDOR =  By.id("NomeVendedor");
	private By VALORPARCELA =  By.id("ValorParcela");
	private By CPFCLIENTE =  By.id("Cliente_Identificacao");
	private By NOMECLIENTE =  By.id("NomeCliente");
	private By PESQUISARCLIENTE =  By.id("btnPesquisarCliente");
	private By EDITACLIENTE =  By.id("btnEditCliente");
	private By LIMPACLIENTE =  By.id("btnLimparCliente");
	private By OBSERVACAO =  By.id("Observacao");
	private By SALVARPROPOSTA =  By.id("salvarProposta");
	private By SIMULACAO = By.xpath("//span[text()='Simulação']");
	public CriarPropostaPO(WebDriver webDriver) {
		super(webDriver);

	}

	@Override
	public boolean isDisplayed() {
		return searchElement.existsNoLog(SIMULACAO, "Pagina de Criar Proposta", 30);
	}
	
	public WebElement enviarProposta() throws Exception{
		return searchElement.findElementBy(SALVARPROPOSTA, "Botão Enviar Proposta");
	}
	
	public WebElement pesquisaCliente() throws Exception{
		return searchElement.findElementBy(PESQUISARCLIENTE, "Botão Pesquisa cliente");
	}
	public WebElement limparCliente() throws Exception{
		return searchElement.findElementBy(LIMPACLIENTE, "Botão Limpar cliente");
	}
	public WebElement atualizaCliente() throws Exception{
		return searchElement.findElementBy(EDITACLIENTE, "Botão Atualizar cliente");
	}
	public WebElement cpfCliente() throws Exception{
		return searchElement.findElementBy(CPFCLIENTE, "CPF Cliente");
	}
	
	public WebElement nomeCliente() throws Exception{
		return searchElement.findElementBy(NOMECLIENTE, "Nome cliente");
	}
	
	public WebElement observacao() throws Exception{
		return searchElement.findElementBy(OBSERVACAO, "Observação");
	}
	
	public WebElement valorParcela() throws Exception{
		return searchElement.findElementBy(VALORPARCELA, "Valor parcela");
	}
	
	public WebElement nomeVendedor() throws Exception{
		return searchElement.findElementBy(NOMEVENDEDOR, "Nome vendedor");
	}
	
	public WebElement formaPagamento() throws Exception{
		return searchElement.findElementBy(FORMAPAGAMENTO, "Forma de pagamento");
	}
	
	public WebElement qtdeParcelas() throws Exception{
		return searchElement.findElementBy(QTDEPARCELAS, "Plano");
	}
	
	public WebElement tabelaComercial() throws Exception{
		return searchElement.findElementBy(TABELACOMERCIAL, "Tabela comercial");
	}

	public WebElement produto() throws Exception{
		return searchElement.findElementBy(CODIGOTIPOOPERACAO, "Produto");
	}
	
	public WebElement dataPrimeiraParcela() throws Exception{
		return searchElement.findElementBy(DATAPARCELA, "Data primeira Parcela");
	}
	
	public WebElement valorSolicitado() throws Exception{
		return searchElement.findElementBy(VALORSOLICITADO, "Valor solicitado");
	}
	public WebElement valorEntrada() throws Exception{
		return searchElement.findElementBy(VALORENTRADA, "Valor Entrada");
	}
	public WebElement valorVista() throws Exception{
		return searchElement.findElementBy(VALORAVISTA, "Valor a vista");
	}
	public WebElement calculoValorCompra() throws Exception{
		return searchElement.findElementBy(VALORCOMPRA, "Valor a vista");
	}
}

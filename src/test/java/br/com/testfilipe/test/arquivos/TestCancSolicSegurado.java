package br.com.testfilipe.test.arquivos;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import br.com.testfilipe.core.utils.StringFormat;


public class TestCancSolicSegurado {

	//private static WebDriver webDriver;
	
	@BeforeClass
	public static void initiate() {
		//Platform platformWebDriver = ChromePlatform.StartWebDriver();
		//webDriver = platformWebDriver.getLocalWebDriver();
		
	}
	
	@AfterMethod
	public static void tearDown() {
		//webDriver.close();
		
	}
	
	@Test
	public void GeracaoArquivo() throws Exception {

		LocalDate localDate = LocalDate.now();
		
		DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("ddMMyyyyhhmm");
		FileWriter fw = new FileWriter("000010_" + fileFormat.format(localDate) + "_VG07_V0003064.PRO");
		
		int tamanhoLinha = 93;
		
		int contratosQuantidade  = 30000;
		int parcelasQuantidade = 4;
		int versao = (int )(Math.random() * 999999 + 1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		//Header do arquivo
		String header = StringUtils.repeat(" ", tamanhoLinha);
		header = StringFormat.substituiTextoPosicao(header, 0, 1, 2); //Tipo Registro
		header = StringFormat.substituiTextoPosicao(header, 7, 3, 2); //Tipo Arquivo
		header = StringFormat.substituiTextoPosicao(header, versao, 5, 6); //Numero Sequencial do Arquivo
		header = StringFormat.substituiTextoPosicao(header, dtf.format(localDate), 11, 10); //Data de Geracao do Arquivo	
		header = StringFormat.substituiTextoPosicao(header, "REALIZE CREDITO, FINANCEIRA E INVESTIMENTO S.A.", 21, 15); //Nome da Empresa
		header = StringFormat.substituiTextoPosicao(header, "27351731000138", 36, 15); //CNPJ  da Empresa
		header = StringFormat.substituiTextoPosicao(header, "EP", 51, 8); //Codigo do Produto
		fw.write(header + "\r\n");
		
		//Detail do arquivo
		int numeroLinha = 1;

		for (int i = 0; i < contratosQuantidade; i++) {
			//Detalhes
			int origemProposta = 59;
			int numeroProposta  = 0;
			int NumeroContrato = 0;
			//String detail = solicitacaoCancelamento(tamanhoLinha, numeroLinha, origemProposta, numeroProposta, NumeroContrato, 
			//										dtf, dataCancelamento, motivoCancelamento);
			//fw.write(detail);
			numeroLinha ++;
		}
		
		//Footer do arquivo
		String footer = StringUtils.repeat(" ", tamanhoLinha);
		footer = StringFormat.substituiTextoPosicao(footer, 99, 1, 2); //Tipo Registro
		footer = StringFormat.substituiTextoPosicao(footer, numeroLinha + 1, 3, 7); //Tipo Arquivo
		fw.write(footer);
		
		fw.close();
	}

	private String solicitacaoCancelamento(int tamanhoLinha, int numeroLinha, int origemProposta, int numeroProposta, 
									int NumeroContrato, DateTimeFormatter dtf,  LocalDate dataCancelamento, 
									String motivoCancelamento) {
		
		String detail = StringUtils.repeat(" ", tamanhoLinha);
		detail = StringFormat.substituiTextoPosicao(detail, 17, 1, 2); //Tipo de Registro
		detail = StringFormat.substituiTextoPosicao(detail,numeroLinha, 3, 7); //Número sequencial de Registro
		detail = StringFormat.substituiTextoPosicao(detail, origemProposta, 10, 2); //Origem da Proposta
		detail = StringFormat.substituiTextoPosicao(detail, numeroProposta, 12, 8); //número da proposta Porto
		detail = StringFormat.substituiTextoPosicao(detail, NumeroContrato, 20, 14); //número do contrato  
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(dataCancelamento), 34, 10); //Data Cancelamento
		detail = StringFormat.substituiTextoPosicao(detail, motivoCancelamento, 44, 50); // Motivo Cancelamento
		
		return detail;
	}
}

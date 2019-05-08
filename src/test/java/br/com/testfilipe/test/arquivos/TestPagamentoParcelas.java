package br.com.testfilipe.test.arquivos;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import br.com.testfilipe.core.utils.StringFormat;


public class TestPagamentoParcelas {

	//private static WebDriver webDriver;
	
	@BeforeClass
	public static void initiate() {
		//Platform platformWebDriver = ChromePlatform.StartWebDriver();
		//webDriver = platformWebDriver.getLocalWebDriver();
		
	}
	
	@AfterClass
	public static void tearDown() {
		//webDriver.close();
		
	}
	
	@Test
	public void GeracaoArquivo() throws Exception {

		LocalDate localDate = LocalDate.now();
		
		DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("ddMMyyyyhhmm");
		FileWriter fw = new FileWriter("000010_" + fileFormat.format(localDate) + "_VG02_V0003064.PRO");
		
		int tamanhoLinha = 130;
		
		int contratosQuantidade  = 30000;
		int parcelasQuantidade = 4;
		int versao = (int )(Math.random() * 999999 + 1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		//Header do arquivo
		String header = StringUtils.repeat(" ", tamanhoLinha);
		header = StringFormat.substituiTextoPosicao(header, 0, 1, 2); //Tipo Registro
		header = StringFormat.substituiTextoPosicao(header, 2, 3, 2); //Tipo Arquivo
		header = StringFormat.substituiTextoPosicao(header, versao, 5, 6); //Numero Sequencial do Arquivo
		header = StringFormat.substituiTextoPosicao(header, dtf.format(localDate), 11, 10); //Data de Geracao do Arquivo	
		header = StringFormat.substituiTextoPosicao(header, "Empresa de Teste", 21, 15); //Nome da Empresa
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
			//String detail = pagamentoParcela(tamanhoLinha, numeroLinha, origemProposta, numeroProposta, NumeroContrato, numeroParcela, 
			//		dtf, pagtoData, vlParcela, percJuros, vlJuros, vlPremio, vlJurosPremio, inicioVigencia, fimVigencia);
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

	private String pagamentoParcela(int tamanhoLinha, int numeroLinha, int origemProposta, int numeroProposta, 
									int NumeroContrato, int numeroParcela, DateTimeFormatter dtf,  LocalDate pagtoData, 
									long vlParcela, long percJuros, long vlJuros, long vlPremio, long vlJurosPremio,
									LocalDate inicioVigencia, LocalDate fimVigencia) {
		
		String detail = StringUtils.repeat(" ", tamanhoLinha);
		detail = StringFormat.substituiTextoPosicao(detail, 11, 1, 2); //Tipo de Registro
		detail = StringFormat.substituiTextoPosicao(detail,numeroLinha, 3, 7); //Número sequencial de Registro
		detail = StringFormat.substituiTextoPosicao(detail, origemProposta, 10, 2); //Origem da Proposta
		detail = StringFormat.substituiTextoPosicao(detail, numeroProposta, 12, 8); //número da proposta Porto
		detail = StringFormat.substituiTextoPosicao(detail, NumeroContrato, 20, 14); //número do contrato  
		detail = StringFormat.substituiTextoPosicao(detail, numeroParcela, 34, 2); //Número da parcela que está sendo paga.
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(pagtoData), 36, 10); // Data Pagto Parcela
		detail = StringFormat.substituiTextoPosicao(detail, vlParcela, 46, 15); //Valor da parcela que o cliente deveria pagar na data do vencimento
		detail = StringFormat.substituiTextoPosicao(detail, percJuros, 61, 5); //Informar apenas quando a data de pagamento da parcela for diferente da data de vencimento.
		detail = StringFormat.substituiTextoPosicao(detail, vlJuros, 66, 15); //Informar apenas quando a data de pagamento da parcela for diferente da data de vencimento.
		detail = StringFormat.substituiTextoPosicao(detail, vlPremio, 81, 15); //Valor da parcela referente a prêmio do seguro
		detail = StringFormat.substituiTextoPosicao(detail, vlJurosPremio, 96, 15); //Informar apenas quando a data de pagamento da parcela for diferente da data de vencimento.
		detail = StringFormat.substituiTextoPosicao(detail, inicioVigencia == null ? 0 :  dtf.format(inicioVigencia), 111, 10); // Inicio da Vigencia
		detail = StringFormat.substituiTextoPosicao(detail, fimVigencia== null ? 0 : dtf.format(fimVigencia), 121, 10); // Fim da Vigencia
		
		return detail;
	}
}

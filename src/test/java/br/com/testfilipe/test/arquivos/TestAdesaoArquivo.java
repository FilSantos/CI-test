package br.com.testfilipe.test.arquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.core.selenium.platfom.ChromePlatform;
import br.com.testfilipe.core.selenium.platfom.Platform;
import br.com.testfilipe.core.utils.BrazilianDocuments;
import br.com.testfilipe.test.pageobject.salesforce.Contas;
import br.com.testfilipe.test.pageobject.salesforce.Contratos;
import br.com.testfilipe.test.pageobject.salesforce.Login;
import br.com.testfilipe.test.pageobject.salesforce.MainMenu;
import br.com.testfilipe.test.pageobject.salesforce.MainMenuItem;
import br.com.testfilipe.test.utils.FileGenerator;


public class TestAdesaoArquivo {
	final static Logger logger = Logger.getLogger(TestAdesaoArquivo.class);
			
	private static WebDriver webDriver;
	private static MainMenu mainMenu;
	private static FileGenerator file;
	private static int numeroLinha;
	private static int numeroProposta;
	private static int NumeroContrato;
	
	@BeforeClass
	public static void initiate() throws Exception {
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);

		H2sql.openConnection();
		
		Platform platformWebDriver =  ChromePlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();

		webDriver.navigate().to("https://portoseguro--uat.cs53.my.salesforce.com");
		Login login = new Login(webDriver);
		mainMenu = new MainMenu(webDriver);
		file = new FileGenerator();
		
		login.setcredentials("filipe.santos@portoseguro.com.br.uat", "235802@Gl");
	}
	
	@Before
	public void startTest() throws Exception {
		mainMenu.navigateMenu(MainMenuItem.INICIO_TAB);
		
		numeroLinha = 1;
		numeroProposta  = 0;
		NumeroContrato = 0;
		file = new FileGenerator();
		
	}
	
	@AfterClass
	public static void tearDown() {
		webDriver.close();
	}
	
	@Test
	public void validaArquivoAdesaoSalesForce() throws Exception {
		
		String arquivoGerado = gerarArquivo();
		
		importacaoArquivo(arquivoGerado);
		
		String     sqlQuery = "SELECT NumeroPropostaPorto";
		sqlQuery = sqlQuery + " FROM ADESAO WHERE TipoRegistro = 10";
		ResultSet returnResultSet = H2sql.returnResultSet(sqlQuery);
		
		mainMenu.navigateMenu(MainMenuItem.PROPOSTAS_TAB);
		Contratos contratos = new Contratos(webDriver);
		Contas contas = new Contas(webDriver);
		
	}

	private void importacaoArquivo(String arquivoGerado) {
		List<String> createTables = new ArrayList<String>();
		createTables.add("DROP TABLE IF EXISTS ADESAO");
		createTables.add("DROP TABLE IF EXISTS ADESAOPARCELAS");
		createTables.add("CREATE TABLE ADESAO (DATA VARCHAR(450)) AS " + 
						 "SELECT * FROM CSVREAD ('"+ arquivoGerado +"'," +
						 "'DATA', 'charset=UTF-8 fieldSeparator=|')");
		
		for (String table : createTables) {
			H2sql.executeStatement(table);
		}
		createTables.clear();
		
		logger.info("Dados importados");
		createTables.add("ALTER TABLE ADESAO add OrigemProposta int;");
		createTables.add("ALTER TABLE ADESAO add NumeroPropostaPorto int;");
		createTables.add("ALTER TABLE ADESAO add NumeroContrato int;");
		createTables.add("ALTER TABLE ADESAO add TipoRegistro int;");
		createTables.add("delete from adesao  where SUBSTRING(DATA, 1, 2) = '00' OR SUBSTRING(DATA, 1, 2) = '99'");
		createTables.add("update adesao set ORIGEMPROPOSTA =cast( SUBSTRING(DATA, 10, 2) as int), " +
						 "NUMEROPROPOSTAPORTO  =cast(SUBSTRING(DATA, 12, 8) as int), " + 
						 "NUMEROCONTRATO = cast(SUBSTRING(DATA, 20, 14) as int), " +
						 "TipoRegistro = cast(SUBSTRING(DATA, 1, 2) as int)");
		createTables.add("CREATE TABLE ADESAOPARCELAS AS SELECT * FROM ADESAO where TipoRegistro = 30");
		createTables.add("DELETE FROM ADESAO where TipoRegistro = 30");
		
		for (String table : createTables) {
			H2sql.executeStatement(table);
		}
		logger.info("Dados Classificados");
	}

	private String gerarArquivo() throws IOException {
		
		System.out.println("Gerando dados");
		
		LocalDateTime localDate = LocalDateTime.now();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
		
		String fileName = Paths.get(".").toAbsolutePath().normalize().toString() + 
						  "/" + "data/ 000010_" + fileFormat.format(localDate) + "_VG01_V0003064.PRO";
		
		FileWriter fw = new FileWriter(fileName);
		
		int tamanhoLinha = 450;
		int versao = (int )(Math.random() * 999 + 1);
		
		//Header do arquivo
		String header = file.preencheHeader(dtf.format(localDate), tamanhoLinha, versao, 1, 
						"REALIZE CFI - CREDITO, FINANCIAMENTO E INVESTIMENTO S.A.", "27929879000126", "EP");
		fw.write(header + "\r\n");
		
		//Detail do arquivo
		gerarDetalhe(localDate, fw, tamanhoLinha, 3000, 4);
		gerarDetalhe(localDate, fw, tamanhoLinha, 3000, 5);
		gerarDetalhe(localDate, fw, tamanhoLinha, 10000, 7);
		gerarDetalhe(localDate, fw, tamanhoLinha, 10000, 11);
		
		//Footer do arquivo
		fw.write(file.footer(tamanhoLinha, numeroLinha));
		fw.close();
		System.out.println("Dados gerados");
		return fileName;
	}

	private void gerarDetalhe(LocalDateTime localDate, FileWriter fw, int tamanhoLinha, int contratosQuantidade,
			int parcelasQuantidade) throws IOException {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		for (int i = 0; i < contratosQuantidade; i++) {
			//Detalhes
			int origemProposta = 59;

			String cpfSegurado = BrazilianDocuments.person(false);
			String detail = file.geraProposta(tamanhoLinha, parcelasQuantidade, dtf, localDate, numeroLinha, origemProposta, numeroProposta, NumeroContrato);
			
			fw.write(detail + "\r\n");
			numeroLinha ++;
			
			//Segurado
			String segurado = StringUtils.repeat(" ", tamanhoLinha);
			segurado = file.geraSegurado(tamanhoLinha, numeroLinha, origemProposta, numeroProposta, NumeroContrato,
					cpfSegurado);
			fw.write(segurado + "\r\n");
			numeroLinha ++;
			
			// Parcelas
			for (int j = 0; j < parcelasQuantidade; j++) {
				LocalDateTime venctoData = localDate.plusMonths(j);
				String parcela = StringUtils.repeat(" ", tamanhoLinha);
				parcela = file.geraParcelas(tamanhoLinha, dtf, numeroLinha, origemProposta, numeroProposta, NumeroContrato, j, venctoData);
				
				fw.write(parcela + "\r\n");
				numeroLinha ++;
			}
			numeroProposta ++;
			NumeroContrato++;
		}
	}


	
}

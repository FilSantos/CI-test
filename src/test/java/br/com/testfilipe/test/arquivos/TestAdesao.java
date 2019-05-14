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
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.core.selenium.platfom.ChromePlatform;
import br.com.testfilipe.core.selenium.platfom.Platform;
import br.com.testfilipe.core.utils.BrazilianDocuments;
import br.com.testfilipe.test.pageobject.salesforce.ContaDetalhes;
import br.com.testfilipe.test.pageobject.salesforce.ContaSeguradoEndereco;
import br.com.testfilipe.test.pageobject.salesforce.ContratantesDetalhes;
import br.com.testfilipe.test.pageobject.salesforce.ContratosDetalhes;
import br.com.testfilipe.test.pageobject.salesforce.Login;
import br.com.testfilipe.test.pageobject.salesforce.MainMenu;
import br.com.testfilipe.test.pageobject.salesforce.SearchResults;
import br.com.testfilipe.test.utils.FileGenerator;

public class TestAdesao {

	final static Logger logger = Logger.getLogger(TestAdesao.class);
	
	private static WebDriver webDriver;
	private static MainMenu mainMenu;
	private static SearchResults searchResults;
	private static FileGenerator file;
	private static int numeroLinha;
	private static int numeroProposta;
	private static int NumeroContrato;
	private static ContratosDetalhes contrato;
	private static ContaDetalhes conta;
	private static ContaSeguradoEndereco contaseguradoendereco;
	private static ContratantesDetalhes contratantes;

	
	
	@Test(dataProvider="getData")
	public void validaDados(String NumeroPropostaPorto) throws Exception {
		
		ResultSet resultSetProposta = H2sql.returnResultSet
				("Select * from V_Proposta WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		ResultSet resultSetSegurado = H2sql.returnResultSet
				("Select * from V_Segurado WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		ResultSet resultSetParcela = H2sql.returnResultSet
				("Select * from AdesaoParcelas WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		
		resultSetProposta.first();
		resultSetSegurado.first();
		resultSetParcela.first();
		
		mainMenu.searchValue(resultSetProposta.getString("numeroContrato"));
		try {
			searchResults.tapContractTable(resultSetProposta.getString ("numeroContrato"));
		} catch (Exception e) {
		Assert.fail("Contrato não localizado");
		}
	
		Assert.assertEquals(contrato.getProposal(), resultSetProposta.getString("numeroProposta"), "Proposta");
		Assert.assertEquals(contrato.getInitialValidity(), resultSetProposta.getString("datainíciovigênciaseguro"),"Inicio Vigencia");
		Assert.assertEquals(contrato.getFinalValidity(),resultSetProposta.getString("datafinalvigênciaseguro"),"Final Vigencia");
		Assert.assertEquals(contrato.getIDPartner(), resultSetParcela.getString("IDContratoParceiro"), "ID contrato Parceiro");
		contrato.tapContactorsNumber();
		Assert.assertEquals(contratantes.getQuantityParcel(),resultSetProposta.getString("Quantidade Parcelas"));
		Assert.assertEquals(contratantes.getMatchFirstParcel(), resultSetParcela.getString("Vencimento primeira parcela"));
		Assert.assertEquals(contratantes.getParcelAward(),resultSetParcela.getString("Prêmio da parcela"));
		contratantes.tapAccount();
		Assert.assertEquals(conta.getAccountName(), resultSetSegurado.getString("Nome da Conta"));
		Assert.assertEquals(conta.getSex(),resultSetSegurado.getString("Sexo"));
		Assert.assertEquals(conta.getDateOfBirth(), resultSetSegurado.getString("Data de Nascimento"));
		Assert.assertEquals(conta.getCPF(), resultSetSegurado.getString("CPF"));
		Assert.assertEquals(conta.getMaritalStatus(), resultSetSegurado.getString("Estado civil"));
		Assert.assertEquals(conta.getCellphoneNumber(), resultSetSegurado.getString("Celular"));
		Assert.assertEquals(conta.getCommercialNumber(), resultSetSegurado.getString("Número comercial"));
		Assert.assertEquals(conta.getPhoneNumber(), resultSetSegurado.getString("Telefone residencial"));
		conta.tapEditAdress();
		Assert.assertEquals(contaseguradoendereco.getCEP(), resultSetSegurado.getString("CEP"));
		Assert.assertEquals(contaseguradoendereco.getStreet(), resultSetSegurado.getString("Logradouro"));
		Assert.assertEquals(contaseguradoendereco.getComplement(), resultSetSegurado.getString("Complemento"));
		Assert.assertEquals(contaseguradoendereco.getDistrict(), resultSetSegurado.getString("Bairro"));
		Assert.assertEquals(contaseguradoendereco.getCity(), resultSetSegurado.getString("Cidade"));
		Assert.assertEquals(contaseguradoendereco.getState(), resultSetSegurado.getString("Estado"));
	}
	
	@BeforeSuite
	public static void initiate() throws Exception {
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		
		H2sql.openConnection();
		
		Platform platformWebDriver =  ChromePlatform.StartWebDriver();
		webDriver = platformWebDriver.getLocalWebDriver();

		webDriver.navigate().to("https://portoseguro--uat.cs53.my.salesforce.com");
		Login login = new Login(webDriver);
		mainMenu = new MainMenu(webDriver);
		searchResults = new SearchResults(webDriver);
		
		login.setcredentials("filipe.santos@portoseguro.com.br.uat", "235802@Gl");

		contrato = new ContratosDetalhes(webDriver);
		conta = new ContaDetalhes(webDriver);
		contaseguradoendereco = new ContaSeguradoEndereco (webDriver);
		contratantes = new ContratantesDetalhes (webDriver);
		
	}
	
	@AfterSuite
	public static void tearDown() {
		webDriver.close();
	}
	
	@DataProvider
	public Object[][] getData() throws Exception {
		
		numeroLinha = 1;
		numeroProposta  = 0;
		NumeroContrato = 0;
		
		file = new FileGenerator();
		importacaoArquivo("//home/filipe/Downloads/vg1.pro");
		file = null;
		
		String     sqlQuery = "SELECT CAST(ORIGEMPROPOSTA,CHAR(2)) + '-' + NumeroPropostaPorto AS PROPOSTA";
		sqlQuery = sqlQuery + " FROM ADESAO WHERE TipoRegistro = 10";
		ResultSet returnResultSet = H2sql.returnResultSet(sqlQuery);
	    int colCount = returnResultSet.getMetaData().getColumnCount();
	    returnResultSet.last();
	    int rowCount = returnResultSet.getRow();
	    returnResultSet.beforeFirst();
	    Object[][] data = new Object[rowCount][colCount];
	    int row = 0;
	    while (returnResultSet.next()) {
	        for (int i = 0; i < colCount; i++) {
	            data[row][i] = returnResultSet.getObject(i+1);
	        }
	        row++;
	    }
	    return data;

	}
	
	private void importacaoArquivo(String arquivoGerado) {
		logger.info("Dados sendo importados");

		List<String> createTables = new ArrayList<String>();
		createTables.add("DROP VIEW IF EXISTS V_Proposta");
		createTables.add("DROP VIEW IF EXISTS V_Segurado");
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
		createTables.add("ALTER TABLE ADESAO add NumeroPropostaPorto char(8);");
		createTables.add("ALTER TABLE ADESAO add NumeroContrato int;");
		createTables.add("ALTER TABLE ADESAO add TipoRegistro int;");
		createTables.add("CREATE VIEW V_Proposta AS \r\n" + 
				"SELECT ORIGEMPROPOSTA ,NUMEROPROPOSTAPORTO ,NUMEROCONTRATO, \r\n" + 
				"SUBSTRING(DATA, 34, 20 ) as IdentSeguradoParceiro, \r\n" + 
				"SUBSTRING(DATA, 54, 8 ) as Estipulante, \r\n" + 
				"SUBSTRING(DATA, 62, 5 ) as Filial, \r\n" + 
				"SUBSTRING(DATA, 67, 3 ) as NumSerieSulacap, \r\n" + 
				"SUBSTRING(DATA, 70, 5 ) as NumSorteSulacap, \r\n" + 
				"SUBSTRING(DATA, 75, 10 ) as DataCompra, \r\n" + 
				"SUBSTRING(DATA, 85, 10 ) as DataAdesãoSeguro, \r\n" + 
				"SUBSTRING(DATA, 95, 2 ) as PlanoContratado, \r\n" + 
				"SUBSTRING(DATA, 97, 2 ) as QtdeTotalParcelas, \r\n" + 
				"SUBSTRING(DATA, 99, 10 ) as DataInícioVigênciaSeguro, \r\n" + 
				"SUBSTRING(DATA, 109, 10 ) as DataFinalVigênciaSeguro \r\n" + 
				"FROM ADESAO \r\n where Tiporegistro = 10;");
		
		createTables.add("CREATE VIEW V_Segurado AS \r\n" + 
				"SELECT ORIGEMPROPOSTA ,NUMEROPROPOSTAPORTO ,NUMEROCONTRATO, \r\n" + 
				"SUBSTRING(DATA, 34, 14 ) as CPFSegurado,\r\n" + 
				"SUBSTRING(DATA, 48, 50 ) as NomeSegurado,\r\n" + 
				"SUBSTRING(DATA, 98, 10 ) as DataNascimentoSegurado,\r\n" + 
				"SUBSTRING(DATA, 108, 1 ) as SexoSegurado,\r\n" + 
				"SUBSTRING(DATA, 109, 1 ) as EstadoCivilSegurado,\r\n" + 
				"SUBSTRING(DATA, 110, 40 ) as EnderecoSegurado,\r\n" + 
				"SUBSTRING(DATA, 150, 5 ) as NumeroEndereco,\r\n" + 
				"SUBSTRING(DATA, 155, 15 ) as Complemento,\r\n" + 
				"SUBSTRING(DATA, 170, 5 ) as CEP,\r\n" + 
				"SUBSTRING(DATA, 175, 3 ) as CEPComplemento,\r\n" + 
				"SUBSTRING(DATA, 178, 20 ) as Bairro,\r\n" + 
				"SUBSTRING(DATA, 198, 20 ) as Cidade,\r\n" + 
				"SUBSTRING(DATA, 218, 2 ) as Estado,\r\n" + 
				"SUBSTRING(DATA, 220, 2 ) as TipoFone1,\r\n" + 
				"SUBSTRING(DATA, 222, 4 ) as DDDFone1,\r\n" + 
				"SUBSTRING(DATA, 226, 8 ) as NumeroFone1,\r\n" + 
				"SUBSTRING(DATA, 234, 4 ) as RamalFone1,\r\n" + 
				"SUBSTRING(DATA, 238, 2 ) as TipoFone2,\r\n" + 
				"SUBSTRING(DATA, 240, 4 ) as DDDFone2,\r\n" + 
				"SUBSTRING(DATA, 244, 8 ) as NumeroFone2,\r\n" + 
				"SUBSTRING(DATA, 252, 4 ) as RamalFone2,\r\n" + 
				"SUBSTRING(DATA, 256, 2 ) as TipoFone3,\r\n" + 
				"SUBSTRING(DATA, 258, 4 ) as DDDFone3,\r\n" + 
				"SUBSTRING(DATA, 262, 8 ) as NumeroFone3,\r\n" + 
				"SUBSTRING(DATA, 270, 4 ) as RamalFone3,\r\n" + 
				"SUBSTRING(DATA, 274, 2 ) as TipoFone4,\r\n" + 
				"SUBSTRING(DATA, 276, 4 ) as DDDFone4,\r\n" + 
				"SUBSTRING(DATA, 280, 8 ) as NumeroFone4,\r\n" + 
				"SUBSTRING(DATA, 288, 4 ) as RamalFone4,\r\n" + 
				"SUBSTRING(DATA, 292, 50 ) as Email,\r\n" + 
				"SUBSTRING(DATA, 342, 5 ) as CodigoProfissao,\r\n" + 
				"SUBSTRING(DATA, 347, 18 ) as ValorRendaMensal\r\n" + 
				"FROM ADESAO \r\n where Tiporegistro = 20;");
		createTables.add("delete from adesao  where SUBSTRING(DATA, 1, 2) = '00' OR SUBSTRING(DATA, 1, 2) = '99'");
		createTables.add("update adesao set ORIGEMPROPOSTA =cast( SUBSTRING(DATA, 10, 2) as int), " +
						 "NUMEROPROPOSTAPORTO  = SUBSTRING(DATA, 12, 8), " + 
						 "NUMEROCONTRATO = cast(SUBSTRING(DATA, 20, 14) as int), " +
						 "TipoRegistro = cast(SUBSTRING(DATA, 1, 2) as int)");
		createTables.add("CREATE TABLE ADESAOPARCELAS AS SELECT * FROM ADESAO where TipoRegistro = 30");
		createTables.add("ALTER TABLE ADESAOPARCELAS add NumeroParcela int;");
		createTables.add("ALTER TABLE ADESAOPARCELAS add DataVencimentoParcela DATE;");
		createTables.add("ALTER TABLE ADESAOPARCELAS add ValorParcela DECIMAL;");
		createTables.add("ALTER TABLE ADESAOPARCELAS add ValorPrêmioParcela DECIMAL;");
		//createTables.add("update ADESAOPARCELAS set NumeroParcela = CAST(SUBSTRING(DATA, 34, 2 ) AS INT), " +
		//		 "DataVencimentoParcela  = CAST(SUBSTRING(DATA, 36, 10 ) AS DECIMAL), " + 
		//		 "ValorParcela = CAST(SUBSTRING(DATA, 46, 15,02 )AS DECIMAL), " +
		//		 "ValorPrêmioParcela = CAST(SUBSTRING(DATA, 61,02, 15 ) AS DECIMAL)");
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
		gerarDetalhe(localDate, fw, tamanhoLinha, 1, 4);
		//gerarDetalhe(localDate, fw, tamanhoLinha, 3000, 5);
		//gerarDetalhe(localDate, fw, tamanhoLinha, 10000, 7);
		//gerarDetalhe(localDate, fw, tamanhoLinha, 10000, 11);
		
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
		int origemProposta = 18;

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

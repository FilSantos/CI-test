package br.com.testfilipe.test.arquivos;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.core.utils.BrazilianDocuments;
import br.com.testfilipe.test.utils.FileGenerator;
import br.com.testfilipe.test.utils.HerokuUtil;
import br.com.testfilipe.test.utils.SalesForceUtil;

public class TestPagamentoParcelasWS {

	final static Logger logger = Logger.getLogger(TestPagamentoParcelasWS.class);
	private static String mensagemCritica = "";
	private int numeroLinha;
	
	@Test(dataProvider="getData")
	public void pagamentoParcelas(String origemProposta, String numeroPropostaPorto, Integer numeroContrato, long numeroParcela) throws Exception {

		Reporter.log("Verificando dados no SalesForce - Objeto: Contrato");
		String queryUri = "SELECT Id FROM Quote WHERE Name = '%s-%s'";
		String retorno = SalesForceUtil.getQuery(String.format(queryUri, origemProposta,numeroPropostaPorto));
		
		JSONParser parser = new JSONParser();
		JSONObject search = (JSONObject) parser.parse(retorno);
		
		if ((long) search.get("totalSize") ==  (long) 0 ) {
			Assert.fail("Proposta não encontrada no salesforce (Quote)");
		}else if ((long) search.get("totalSize") !=  (long) 1 ) {
			Assert.fail("Existe mais de um registro com essa chave no Quote");
		}

		JSONArray searchArray = (JSONArray) search.get("records"); 
		search = (JSONObject) searchArray.get(0);
		
		String quoteId = (String) search.get("Id");

		queryUri = "SELECT Id FROM Contract WHERE Proposta__c = '%s'";
		retorno = SalesForceUtil.getQuery(String.format(queryUri, quoteId));
		search = (JSONObject) parser.parse(retorno);
		
		if ((long) search.get("totalSize") ==  (long) 0 ) {
			Assert.fail("Proposta não encontrada no salesforce (Contract)");
		}else if ((long) search.get("totalSize") !=  (long) 1 ) {
			Assert.fail("Existe mais de um registro com essa chave no Contract");
		}
		searchArray = (JSONArray) search.get("records"); 
		search = (JSONObject) searchArray.get(0);
		String contractId = (String) search.get("Id");
		logger.info("Validando");	
		//	/services/data/v45.0/sobjects/ContratanteContrato__c/{ID}
		queryUri = "SELECT Id FROM ContratanteContrato__c WHERE Contrato__c = '%s'";
		retorno = SalesForceUtil.getQuery(String.format(queryUri, contractId));
		JSONObject searchContract = (JSONObject) parser.parse(retorno);
		
		searchArray = (JSONArray) searchContract.get("records"); 
		search = (JSONObject) searchArray.get(0);
		String ContratocId = (String) search.get("Id");
		JSONObject returnData = null;

		Reporter.log("Validando Parcelas");
		String parcelas = HerokuUtil.getParcelas(contractId, ContratocId);
		returnData = (JSONObject) parser.parse(parcelas);
		searchArray = (JSONArray) returnData.get("data"); 
		for (Object object : searchArray) {
			JSONObject jsonParcela = (JSONObject) object;
			if( Long.valueOf(numeroParcela) == (Long) jsonParcela.get("numeroparcela") ){
				Assert.assertEquals((String) jsonParcela.get("status"), "EM FATURAMENTO");
			}
			
		} 
		Assert.fail("Parcela nao encontrado!");
	}
	
	@BeforeSuite
	public static void initiate() throws Exception {
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		
		H2sql.openConnection();
		SalesForceUtil.startAuth();
	}
	
	@BeforeTest
	public static void startTest() {
		 mensagemCritica = "";
	}
	
	
	@DataProvider
	public Object[][] getData() throws Exception {
		
		String currentPath = new java.io.File( "." ).getCanonicalPath();
		List<String> arquivos =  new ArrayList<>();
		//arquivos.add(gerarArquivo());
		File folder = new File(currentPath+"\\prodata");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (Files.getFileExtension(file.getAbsolutePath()).toLowerCase().equals("pro") & 
					Files.getNameWithoutExtension(file.getAbsolutePath()).toUpperCase().startsWith("VG02", 20) ){
				arquivos.add(file.getAbsolutePath());
			}

		}
		importacaoArquivo(arquivos);
		
		String     sqlQuery = "SELECT ORIGEMPROPOSTA, NUMEROPROPOSTAPORTO, NUMEROCONTRATO, NUMEROPARCELA";
		sqlQuery = sqlQuery + " FROM PAGAMENTOPARCELAS ";
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
	
	private void importacaoArquivo(List<String> arquivoGerado) {
		logger.info("Dados sendo importados");

		List<String> createTables = new ArrayList<String>();
		createTables.add("DROP TABLE IF EXISTS PAGAMENTOPARCELAS");
		boolean primeiroCriado = false;
		for (String arquivo : arquivoGerado) {
			if (primeiroCriado){
				createTables.add("INSERT INTO PAGAMENTOPARCELAS (DATA ) " + 
						 "SELECT * FROM CSVREAD ('" + arquivo  +
						 "', 'DATA', 'charset=UTF-8 fieldSeparator=|')");
			}else {
				createTables.add("CREATE TABLE PAGAMENTOPARCELAS (DATA VARCHAR(130)) AS " + 
						 "SELECT * FROM CSVREAD ('" + arquivo  +
						 "', 'DATA', 'charset=UTF-8 fieldSeparator=|')");	
				primeiroCriado = true;
			}
			
		}

		
		for (String table : createTables) {
			H2sql.executeStatement(table);
		}
		createTables.clear();
		
		logger.info("Dados importados");
		createTables.add("ALTER TABLE PAGAMENTOPARCELAS add OrigemProposta char(2);");
		createTables.add("ALTER TABLE PAGAMENTOPARCELAS add NumeroPropostaPorto char(8);");
		createTables.add("ALTER TABLE PAGAMENTOPARCELAS add NumeroContrato int;");
		createTables.add("ALTER TABLE PAGAMENTOPARCELAS add NumeroParcela long;");
		createTables.add("delete from PAGAMENTOPARCELAS  where SUBSTRING(DATA, 1, 2) = '00' OR SUBSTRING(DATA, 1, 2) = '99'");
		createTables.add("update PAGAMENTOPARCELAS set ORIGEMPROPOSTA =  SUBSTRING(DATA, 10, 2), " +
						 "NUMEROPROPOSTAPORTO  = SUBSTRING(DATA, 12, 8), " + 
						 "NUMEROCONTRATO = cast(SUBSTRING(DATA, 20, 14) as int), " +
						 "NUMEROPARCELA = cast(SUBSTRING(DATA, 34, 2) as long)");

		
		for (String table : createTables) {
			H2sql.executeStatement(table);
		}
		logger.info("Dados Classificados");
	}
	
	private String gerarArquivo() throws Exception {
			
			System.out.println("Gerando dados");
			numeroLinha = 1;
			
			FileGenerator file= new FileGenerator();
			
			
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
			gerarDetalhe(localDate, fw, tamanhoLinha, 1, 4, file);
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
			int parcelasQuantidade, FileGenerator file) throws Exception {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		//String arq = HerokuUtil.getLote();
		
		//String sqlImport = "INSERT INTO estoque SELECT ORIGEMPROPOSTA , NUMEROSERIE, NUMEROSORTE, STATUS, NUMEROPROPOSTA FROM CSVREAD ('" + arq  +
		//				   "',null, 'charset=UTF-8 fieldSeparator=|')";
		//H2sql.executeStatement(sqlImport);
		
		
		String sqlResult = "SELECT OrigemProposta,NumeroProposta FROM ESTOQUE WHERE Status NOT IN ('Processado', 'AUTOMACAO')";
		List<HashMap<String, Object>> list = H2sql.returnResultSetList(sqlResult);
		int iterador = 0;
		
		if ( contratosQuantidade > list.size() ){
			throw new Exception(" Quantidade de Estoque não é suficiente para a automação gerar contratos. Teste interrompido!");
		}
		
		for (HashMap<String, Object> hashMap : list) {
			SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmssS");
			
			int numeroProposta = Integer.parseInt( (String) hashMap.get("NUMEROPROPOSTA"));
			long NumeroContrato = Long.parseLong(format.format( new Date()));
			int origemProposta = Integer.parseInt( (String) hashMap.get("ORIGEMPROPOSTA"));
			
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
			sqlResult = "UPDATE Estoque SET Status = 'EM AUTOMACAO' WHERE  OrigemProposta = '%s' AND NumeroProposta = '%s'";
			H2sql.executeStatement(String.format(sqlResult,(String) hashMap.get("ORIGEMPROPOSTA"), (String) hashMap.get("NUMEROPROPOSTA") ));
			iterador ++;
			if (iterador == contratosQuantidade ){ break; }
		}
		sqlResult = "UPDATE Estoque SET Status = 'AUTOMACAO' WHERE Status = 'EM AUTOMACAO'";
		H2sql.executeStatement(sqlResult);
	}
}

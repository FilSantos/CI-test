package br.com.portoseguro.test.arquivos;

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

import br.com.portoseguro.core.database.H2sql;
import br.com.portoseguro.core.log.LogConstants;
import br.com.portoseguro.core.utils.BrazilianDocuments;
import br.com.portoseguro.test.utils.FileGenerator;
import br.com.portoseguro.test.utils.HerokuUtil;
import br.com.portoseguro.test.utils.SalesForceUtil;

public class TestCancelamentoWS {

	final static Logger logger = Logger.getLogger(TestCancelamentoWS.class);
	private static String mensagemCritica = "";
	private int numeroLinha;
	
	@Test(dataProvider="getData")
	public void cancelamentoProposta(String origemProposta, String numeroPropostaPorto, Integer numeroContrato) throws Exception {
		

		Reporter.log("Verificando dados no SalesForce - Objeto: Contrato");
		String queryUri = "SELECT ContractId FROM Quote WHERE Name = '%s-%s'";
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
		String contractId = (String) search.get("ContractId");

		String contract = SalesForceUtil.getObject("Contract/" + contractId);
		JSONObject returnData = (JSONObject) parser.parse(contract);
	//A partir daqui verificar porque o contractIdCancelado está retornando NULL; 		
		String contractIdCancelado = (String) returnData.get("Name");
	
		contractIdCancelado = contractIdCancelado.substring(0, contractIdCancelado.length() -1) + "1";
		queryUri = "SELECT id FROM Contract WHERE Name = '%s'";
		retorno = SalesForceUtil.getQuery(String.format(queryUri, contractIdCancelado));
		search = (JSONObject) parser.parse(retorno);
		
		if ((long) search.get("totalSize") ==  (long) 0 ) {
			Assert.fail("Proposta não encontrada no salesforce (Quote)");
		}else if ((long) search.get("totalSize") !=  (long) 1 ) {
			Assert.fail("Existe mais de um registro com essa chave no Quote");
		}

		searchArray = (JSONArray) search.get("records"); 
		search = (JSONObject) searchArray.get(0);
		contractIdCancelado = (String) search.get("id");
		
		Thread originalContract = new Thread("Contract Orig - " + contractId) {
			public void run(){
				try{
		
					logger.info("Validando");
				//	/services/data/v45.0/sobjects/Contract/{ID}
					Reporter.log("Validando Contrato Original");
					String contract = SalesForceUtil.getObject("Contract/" + contractId);
					JSONParser parser = new JSONParser();
					JSONObject returnData = (JSONObject) parser.parse(contract);
						
					Assert.assertEquals((String) returnData.get("Status"), "Cancelado");
					Assert.assertEquals((String) returnData.get("Tipo__c"), "Novo");
					Assert.assertEquals((String) returnData.get("Endosso__c"), "0");

////				/services/data/v45.0/sobjects/ContratanteContrato__c/{ID}
					String queryUri = "SELECT Id FROM ContratanteContrato__c WHERE Contrato__c = '%s'";
					String retorno = SalesForceUtil.getQuery(String.format(queryUri, contractId));
					JSONObject searchContract = (JSONObject) parser.parse(retorno);
				
					JSONArray searchArray = (JSONArray) searchContract.get("records"); 
					JSONObject search = (JSONObject) searchArray.get(0);
					String ContratocId = (String) search.get("Id");
					returnData = null;

					Reporter.log("Validando Parcelas");
					String parcelas = HerokuUtil.getParcelas(contractId, ContratocId);
					returnData = (JSONObject) parser.parse(parcelas);
					searchArray = (JSONArray) returnData.get("data"); 
					for (Object object : searchArray) {
						JSONObject jsonParcela = (JSONObject) object;
						Date dataAtual = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						Date dataParcela = dateFormat.parse((String) jsonParcela.get("datavencimento"));
						if( dataAtual.after(dataParcela)){
							Assert.assertEquals((String) jsonParcela.get("status"), "CANCELADA");
						} 
					}
				}catch (Exception e) {
					logger.error(e);
					mensagemCritica = mensagemCritica +  e.getStackTrace() + ";";
				}
			}
		};
			
		Thread cancelledContract = new Thread(contractIdCancelado) {
			public void run(){
				try {
					
					logger.info("Validando");
					Reporter.log("Validando Contrato Cancelado");
					String contractCancelado = SalesForceUtil.getObject("Contract/" + Thread.currentThread().getName());
					JSONParser parser = new JSONParser();
					JSONObject returnData = (JSONObject) parser.parse(contractCancelado);
						
					Assert.assertEquals((String) returnData.get("Status"), "Emitido");
					Assert.assertEquals((String) returnData.get("Tipo__c"), "Cancelamento");
					Assert.assertEquals((String) returnData.get("Endosso__c"), "1");
					
					String queryUri = "SELECT Id FROM ContratanteContrato__c WHERE Contrato__c = '%s'";
					String retorno = SalesForceUtil.getQuery(String.format(queryUri, contractId));
					JSONObject searchContract = (JSONObject) parser.parse(retorno);
				
					JSONArray searchArray = (JSONArray) searchContract.get("records"); 
					JSONObject search = (JSONObject) searchArray.get(0);
					String ContratocId = (String) search.get("Id");
					returnData = null;

					Reporter.log("Validando Parcelas");
					String parcelas = HerokuUtil.getParcelas(contractId, ContratocId);
					returnData = (JSONObject) parser.parse(parcelas);
					searchArray = (JSONArray) returnData.get("data");
					compararvalor(0, searchArray.size(), "Parcelas");
					
					
				}catch (Exception e) {
					logger.error(e);
					mensagemCritica = mensagemCritica +  e.getStackTrace() + ";";
				}
			}
		};
		
		Thread validacaoThread[] = new Thread[2];
		validacaoThread[0] = originalContract;
		validacaoThread[1] = cancelledContract;
		
		for (int j = 0; j < validacaoThread.length; j++) {
			validacaoThread[j].start();
		}
		for (int j = 0; j < validacaoThread.length; j++) {
			validacaoThread[j].join(); 
		}
		
		if (mensagemCritica!= "") {
			Assert.fail(mensagemCritica);
		}
		
	}
	
	private boolean compararvalor(Object esperado , Object retornado, String descricao) {
		
		if ( retornado == null) { retornado =""; }
		
		if(!esperado.equals(retornado) ) {
			mensagemCritica = mensagemCritica + descricao + ";";
			return false;
		}
		return true;

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
					Files.getNameWithoutExtension(file.getAbsolutePath()).toUpperCase().startsWith("VG07", 20) ){
				arquivos.add(file.getAbsolutePath());
			}

		}
		importacaoArquivo(arquivos);
		
		String     sqlQuery = "SELECT ORIGEMPROPOSTA, NUMEROPROPOSTAPORTO, NUMEROCONTRATO";
		sqlQuery = sqlQuery + " FROM CANCELAMENTO";
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
		createTables.add("DROP TABLE IF EXISTS CANCELAMENTO");
		boolean primeiroCriado = false;
		for (String arquivo : arquivoGerado) {
			if (primeiroCriado){
				createTables.add("INSERT INTO CANCELAMENTO (DATA ) " + 
						 "SELECT * FROM CSVREAD ('" + arquivo  +
						 "', 'DATA', 'charset=UTF-8 fieldSeparator=|')");
			}else {
				createTables.add("CREATE TABLE CANCELAMENTO (DATA VARCHAR(93)) AS " + 
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
		createTables.add("ALTER TABLE CANCELAMENTO add OrigemProposta char(2);");
		createTables.add("ALTER TABLE CANCELAMENTO add NumeroPropostaPorto char(8);");
		createTables.add("ALTER TABLE CANCELAMENTO add NumeroContrato int;");
		createTables.add("delete from CANCELAMENTO  where SUBSTRING(DATA, 1, 2) = '00' OR SUBSTRING(DATA, 1, 2) = '99'");
		createTables.add("update CANCELAMENTO set ORIGEMPROPOSTA =  SUBSTRING(DATA, 10, 2), " +
						 "NUMEROPROPOSTAPORTO  = SUBSTRING(DATA, 12, 8), " + 
						 "NUMEROCONTRATO = cast(SUBSTRING(DATA, 20, 14) as int)");
		
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

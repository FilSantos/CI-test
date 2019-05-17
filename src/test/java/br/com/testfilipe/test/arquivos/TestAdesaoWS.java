package br.com.testfilipe.test.arquivos;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

import br.com.testfilipe.core.database.H2sql;
import br.com.testfilipe.core.log.LogConstants;
import br.com.testfilipe.test.utils.SalesForceUtil;

public class TestAdesaoWS {

	final static Logger logger = Logger.getLogger(TestAdesaoWS.class);
	private static String mensagemCritica = "";
	
	@Test(dataProvider="getData")
	public void validaDados(String OrigemProposta, String NumeroPropostaPorto) throws Exception {
		
		ResultSet resultSetProposta = H2sql.returnResultSet
				("Select * from V_Proposta WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		ResultSet resultSetSegurado = H2sql.returnResultSet
				("Select * from V_Segurado WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		ResultSet resultSetParcela = H2sql.returnResultSet
				("Select * from AdesaoParcelas WHERE numeropropostaporto = "+ NumeroPropostaPorto);
		
		resultSetProposta.first();
		resultSetSegurado.first();
		resultSetParcela.first();

		Reporter.log("Verificando dados no SalesForce - Objeto: Contrato");
		String queryUri = "SELECT AccountId,Id FROM Quote WHERE Name = '%s-%s'";
		String retorno = SalesForceUtil.getQuery(String.format(queryUri, 
												resultSetProposta.getString("OrigemProposta"),
												resultSetProposta.getString("NumeroPropostaPorto")));
		
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
		String accountId = (String) search.get("AccountId");

		queryUri = "SELECT Id FROM Contract WHERE Proposta__c = '%s'";
		retorno = SalesForceUtil.getQuery(String.format(queryUri, quoteId));
		parser = new JSONParser();
		search = (JSONObject) parser.parse(retorno);
		
		if ((long) search.get("totalSize") ==  (long) 0 ) {
			Assert.fail("Proposta não encontrada no salesforce (Contract)");
		}else if ((long) search.get("totalSize") !=  (long) 1 ) {
			Assert.fail("Existe mais de um registro com essa chave no Contract");
		}
		searchArray = (JSONArray) search.get("records"); 
		search = (JSONObject) searchArray.get(0);
		String contractId = (String) search.get("Id");


		Thread account = new Thread("AccountId - " + accountId) {
			public void run(){
				// /services/data/v45.0/sobjects/Account/{ID}
				Reporter.log("Validando Conta do Cliente");
				SalesForceUtil.getObject("Account/" + accountId);
				
			}
		};
		

		Thread quote = new Thread("Quote - " + quoteId) {
			public void run() {
			//	 /services/data/v45.0/sobjects/Quote/{ID}
				Reporter.log("Validando Proposta");
					String quote = SalesForceUtil.getObject("Quote/" + quoteId);
					JSONParser parser = new JSONParser();
					JSONObject returnData =null;
					String numeroContrato = null;
					try{
						returnData = (JSONObject) parser.parse(quote);
						numeroContrato = resultSetProposta.getString("OrigemProposta") + "-" + resultSetProposta.getString("NumeroPropostaPorto");
						compararvalor(numeroContrato, (String) returnData.get("Name"), "Origem da Proposta + Numero do Contrato");
						compararvalor(resultSetProposta.getString("ORIGEMPROPOSTA"), (String) returnData.get("Origem__c"), "Origem da Proposta");
						compararvalor(resultSetProposta.getString("NUMEROPROPOSTAPORTO"), (String) returnData.get("NumeroProposta__c"), "Numero da Proposta");
						compararvalor(resultSetProposta.getString("QTDETOTALPARCELAS"), (String) returnData.get("QuantidadeParcelas__c"), "Qtde Parcelas");
						compararvalor(resultSetProposta.getString("IDENTSEGURADOPARCEIRO"), (String) returnData.get("IdContratoParceiro__c"), "Ident. Seg. Parceiro");
						 
					} catch (Exception e) {
						logger.error(e);
						Assert.fail("Erro com o body de resposta e/ou no arquivo 'pro'");
					}


					
				

				
				
			}
		};
		
		Thread contract = new Thread("Contract - " + contractId) {
			public void run(){
			//	/services/data/v45.0/sobjects/Contract/{ID}
				Reporter.log("Validando Contrato");
				
			}
		};
		
		Thread validacaoThread[] = new Thread[3];
		validacaoThread[0] = contract;
		validacaoThread[1] = account;
		validacaoThread[2] = quote;
		
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
	
	private void compararvalor(Object esperado , Object retornado, String descricao) {
		
		if(!esperado.equals(retornado) ) {
			mensagemCritica = mensagemCritica + descricao + ";";
		}

		
		
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
		importacaoArquivo( currentPath+"\\prodata\\Adesao.pro");
		
		String     sqlQuery = "SELECT CAST(ORIGEMPROPOSTA as CHAR(2)) as OrigemProposta , NumeroPropostaPorto";
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
						 "SELECT * FROM CSVREAD ('" + arquivoGerado  +
						 "', 'DATA', 'charset=UTF-8 fieldSeparator=|')");
		
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
}

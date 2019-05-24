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

public class TestAdesaoWS {

	final static Logger logger = Logger.getLogger(TestAdesaoWS.class);
	private static String mensagemCritica = "";
	private int numeroLinha;
	
	@Test(dataProvider="getData")
	public void adesao_Proposta(String OrigemProposta, String NumeroPropostaPorto) throws Exception {
		
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
				logger.info("Validando");
				Reporter.log("Validando Conta do Cliente");
				String account = SalesForceUtil.getObject("Account/" + accountId);
				JSONParser parser = new JSONParser();
				JSONObject returnData = null;
				try{
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat dateFile = new SimpleDateFormat("dd/MM/yyyy");
					Date nascto = dateFile.parse(resultSetSegurado.getString("DataNascimentoSegurado"));
					
					returnData = (JSONObject) parser.parse(account);
					compararvalor(resultSetSegurado.getString("NomeSegurado").trim(), (String) returnData.get("Name"), "Nome do Segurado");
					compararvalor(resultSetSegurado.getString("CPFSegurado").substring(3), 
							((String) returnData.get("Cpf__c")).replaceAll("([-])|([.])", ""), "CPF do Segurado");
					compararvalor(dateFormat.format(nascto), (String) returnData.get("PersonBirthdate"), "Data Nascimento Segurado");
					String sexoAccount = resultSetSegurado.getString("SexoSegurado").equals("F") ? "FEM" : "MASC";
					compararvalor(sexoAccount, (String) returnData.get("Sexo__c"), "Sexo Segurado");
					String estadoCivil= "";
					
					switch (resultSetSegurado.getString("EstadoCivilSegurado")) {
					case "1":
						estadoCivil = "Solteiro (a)";
						break;
					case "2":
						estadoCivil = "Casado (a)";
						break;
					case "4":
						estadoCivil = "Desquitado (a)";
						break;
					case "5":
						estadoCivil = "Divorciado (a)";
						break;
					case "6":
						estadoCivil = "Separado (a)";
						break;
					case "7":
						estadoCivil = "Viuvo (a)";
						break;
					case "8":
						estadoCivil = "Ignorado (a)";
						break;
					default:
						estadoCivil = "";
						break;
					}
					
					String phone = "55 (%s) %s-%s";
					
					String homePhone = "";
					String mobilePhone = "";
					String workPhone = "";
					String faxPhone = "";
					
						switch (resultSetSegurado.getString("TipoFone1")){
						case "01":
							homePhone = String.format(phone, resultSetSegurado.getString("DDDFone1").substring(2),
									resultSetSegurado.getString("NumeroFone1").substring(0,4),
									resultSetSegurado.getString("NumeroFone1").substring(4));
							break;
						case "02":
							mobilePhone = String.format(phone, resultSetSegurado.getString("DDDFone1").substring(2),
									resultSetSegurado.getString("NumeroFone1").substring(0,4),
									resultSetSegurado.getString("NumeroFone1").substring(4));
							break;
						case "03":
							workPhone = String.format(phone, resultSetSegurado.getString("DDDFone1").substring(2),
									resultSetSegurado.getString("NumeroFone1").substring(0,4),
									resultSetSegurado.getString("NumeroFone1").substring(4));
							break;
						case "04":
							faxPhone = String.format(phone, resultSetSegurado.getString("DDDFone1").substring(2),
									resultSetSegurado.getString("NumeroFone1").substring(0,4),
									resultSetSegurado.getString("NumeroFone1").substring(4));
							break;
					}
					
					
						switch (resultSetSegurado.getString("TipoFone2")){
						case "01":
							homePhone = String.format(phone, resultSetSegurado.getString("DDDFone2").substring(2),
									resultSetSegurado.getString("NumeroFone2").substring(0,4),
									resultSetSegurado.getString("NumeroFone2").substring(4));
							break;
						case "02":
							mobilePhone = String.format(phone, resultSetSegurado.getString("DDDFone2").substring(2),
									resultSetSegurado.getString("NumeroFone2").substring(0,4),
									resultSetSegurado.getString("NumeroFone2").substring(4));
							break;
						case "03":
							workPhone = String.format(phone, resultSetSegurado.getString("DDDFone2").substring(2),
									resultSetSegurado.getString("NumeroFone2").substring(0,4),
									resultSetSegurado.getString("NumeroFone2").substring(4));
							break;
						case "04":
							faxPhone = String.format(phone, resultSetSegurado.getString("DDDFone2").substring(2),
									resultSetSegurado.getString("NumeroFone2").substring(0,4),
									resultSetSegurado.getString("NumeroFone2").substring(4));
							break;
					}
					
						switch (resultSetSegurado.getString("TipoFone3")){
						case "01":
							homePhone = String.format(phone, resultSetSegurado.getString("DDDFone3").substring(2),
									resultSetSegurado.getString("NumeroFone3").substring(0,4),
									resultSetSegurado.getString("NumeroFone3").substring(4));
							break;
						case "02":
							mobilePhone = String.format(phone, resultSetSegurado.getString("DDDFone3").substring(2),
									resultSetSegurado.getString("NumeroFone3").substring(0,4),
									resultSetSegurado.getString("NumeroFone3").substring(4));
							break;
						case "03":
							workPhone = String.format(phone, resultSetSegurado.getString("DDDFone3").substring(2),
									resultSetSegurado.getString("NumeroFone3").substring(0,4),
									resultSetSegurado.getString("NumeroFone3").substring(4));
							break;
						case "04":
							faxPhone = String.format(phone, resultSetSegurado.getString("DDDFone3").substring(2),
									resultSetSegurado.getString("NumeroFone3").substring(0,4),
									resultSetSegurado.getString("NumeroFone3").substring(4));
							break;
						}
						
						switch (resultSetSegurado.getString("TipoFone4")){
						case "01":
							homePhone = String.format(phone, resultSetSegurado.getString("DDDFone4").substring(2),
									resultSetSegurado.getString("NumeroFone4").substring(0,4),
									resultSetSegurado.getString("NumeroFone4").substring(4));
							break;
						case "02":
							mobilePhone = String.format(phone, resultSetSegurado.getString("DDDFone4").substring(2),
									resultSetSegurado.getString("NumeroFone4").substring(0,4),
									resultSetSegurado.getString("NumeroFone4").substring(4));
							break;
						case "03":
							workPhone = String.format(phone, resultSetSegurado.getString("DDDFone4").substring(2),
									resultSetSegurado.getString("NumeroFone4").substring(0,4),
									resultSetSegurado.getString("NumeroFone4").substring(4));
							break;
						case "04":
							faxPhone = String.format(phone, resultSetSegurado.getString("DDDFone4").substring(2),
									resultSetSegurado.getString("NumeroFone4").substring(0,4),
									resultSetSegurado.getString("NumeroFone4").substring(4));
							break;
					}
					
					compararvalor(estadoCivil, (String) returnData.get("EstadoCivil__c"), "Estado Civil Segurado");
					compararvalor(homePhone, (String) returnData.get("PersonHomePhone"), "Telefone Residencial");
					compararvalor(workPhone, (String) returnData.get("Phone"), "Telefone Comercial");
					compararvalor(mobilePhone, (String) returnData.get("PersonMobilePhone"), "Celular");
					compararvalor(resultSetSegurado.getString("Email").trim(), (String) returnData.get("PersonEmail"), "Email");
					compararvalor(resultSetSegurado.getString("CodigoProfissao"), (String) returnData.get("Profissao__c"), "Codigo Profissao");
					String valorRendaMensal = 	resultSetSegurado.getString("ValorRendaMensal").substring(0,16) + "." + 
												resultSetSegurado.getString("ValorRendaMensal").substring(16);
					compararvalor(Double.parseDouble(valorRendaMensal),	(Double) returnData.get("Renda__c"), "Valor Renda Mensal");
					
					String endereco = resultSetSegurado.getString("EnderecoSegurado").trim() + ", " + 
										 String.valueOf(Integer.parseInt(resultSetSegurado.getString("NumeroEndereco")));
					
					compararvalor(endereco, (String) returnData.get("BillingStreet"), "Endereco Segurado");
					String cep = resultSetSegurado.getString("CEP") + resultSetSegurado.getString("CEPComplemento");
					compararvalor(cep, (String) returnData.get("BillingPostalCode"), "CEP");
					compararvalor(resultSetSegurado.getString("Cidade").trim(), (String) returnData.get("BillingCity"), "Cidade");
					compararvalor(resultSetSegurado.getString("Estado").trim(), (String) returnData.get("BillingState"), "Estado");	
					
				} catch (Exception e) {
					logger.error(e);
					mensagemCritica = mensagemCritica + e.getStackTrace() + ";";
					Assert.fail("Erro com o body de resposta e/ou no arquivo 'pro'");
				}
				
			}
		};
		
		Thread quote = new Thread("Quote - " + quoteId) {
			public void run() {
			//	 /services/data/v45.0/sobjects/Quote/{ID}
				Reporter.log("Quote - " + quoteId);
				logger.info("Validando");
					String quote = SalesForceUtil.getObject("Quote/" + quoteId);
					JSONParser parser = new JSONParser();
					JSONObject returnData = null;
					String numeroContrato = null;
					try{
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat dateFile = new SimpleDateFormat("dd/MM/yyyy");
						Date inicio = dateFile.parse(resultSetProposta.getString("DataInícioVigênciaSeguro"));
						Date fim = dateFile.parse(resultSetProposta.getString("DataFinalVigênciaSeguro"));
						
						returnData = (JSONObject) parser.parse(quote);
						numeroContrato = resultSetProposta.getString("OrigemProposta") + "-" + resultSetProposta.getString("NumeroPropostaPorto");
						compararvalor(numeroContrato, (String) returnData.get("Name"), "Origem da Proposta + Numero do Contrato");
						compararvalor(resultSetProposta.getString("ORIGEMPROPOSTA"), (String) returnData.get("Origem__c"), "Origem da Proposta");
						compararvalor(resultSetProposta.getString("NUMEROPROPOSTAPORTO"), (String) returnData.get("NumeroProposta__c"), "Numero da Proposta");
						compararvalor(resultSetProposta.getString("IdentSeguradoParceiro").trim(), (String) returnData.get("IdContratoParceiro__c"), "Ident. Seg. Parceiro");
						compararvalor(resultSetProposta.getString("Filial"), (String) returnData.get("EntradaNegocio__c"), "Entrada Negócio");
						compararvalor(dateFormat.format(inicio), 
										(String) returnData.get("VigenciaInicial__c"), "Data Ini. Vigência");
						compararvalor(dateFormat.format(fim),
										(String) returnData.get("VigenciaFinal__c"), "Data Final Vigência");	
					
					} catch (Exception e) {
						logger.error(e);
						mensagemCritica = mensagemCritica + e.getStackTrace() + ";";
						Assert.fail("Erro com o body de resposta e/ou no arquivo 'pro'");
					}
			}
		};
		
		Thread contract = new Thread("Contract - " + contractId) {
			public void run(){
				try{
					logger.info("Validando");
			//	/services/data/v45.0/sobjects/ContratanteContrato__c/{ID}
				String queryUri = "SELECT Id FROM ContratanteContrato__c WHERE Contrato__c = '%s'";
				String retorno = SalesForceUtil.getQuery(String.format(queryUri, contractId));
				JSONParser parser = new JSONParser();
				JSONObject searchContract = (JSONObject) parser.parse(retorno);
				
				JSONArray searchArray = (JSONArray) searchContract.get("records"); 
				JSONObject search = (JSONObject) searchArray.get(0);
				String ContratocId = (String) search.get("Id");
				JSONObject returnData = null;

				Reporter.log("Validando Contrato");
				String contract = SalesForceUtil.getObject("ContratanteContrato__c/" + ContratocId);
				parser = new JSONParser();
				
				returnData = (JSONObject) parser.parse(contract);
				
				compararvalor(Double.parseDouble( resultSetProposta.getString("QtdeTotalParcelas")),
						(Double) returnData.get("QuantidadeParcelas__c"), "Quantidade total de parcelas");
				
				Reporter.log("Validando Parcelas");
				String parcelas = HerokuUtil.getParcelas(contractId, ContratocId);
				
				compararvalor(resultSetParcela.getString("NumeroParcela"), (String) returnData.get("NumerodaParcela"), "Numero da Parcela");
				
				
				
				
				
		
				
						
				
				} catch (Exception e) {
					logger.error(e);
					mensagemCritica = mensagemCritica +  e.getStackTrace() + ";";
					Assert.fail("Erro com o body de resposta e/ou no arquivo 'pro'");
				}
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
			if (Files.getFileExtension(file.getAbsolutePath()).toLowerCase().equals("pro") ){
				arquivos.add(file.getAbsolutePath());
			}
		}
		importacaoArquivo(arquivos);
		
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
	
	private void importacaoArquivo(List<String> arquivoGerado) {
		logger.info("Dados sendo importados");

		List<String> createTables = new ArrayList<String>();
		createTables.add("DROP VIEW IF EXISTS V_Proposta");
		createTables.add("DROP VIEW IF EXISTS V_Segurado");
		createTables.add("DROP TABLE IF EXISTS ADESAO");
		createTables.add("DROP TABLE IF EXISTS ADESAOPARCELAS");
		boolean primeiroCriado = false;
		for (String arquivo : arquivoGerado) {
			if (primeiroCriado){
				createTables.add("INSERT INTO ADESAO (DATA ) " + 
						 "SELECT * FROM CSVREAD ('" + arquivo  +
						 "', 'DATA', 'charset=UTF-8 fieldSeparator=|')");
			}else {
				createTables.add("CREATE TABLE ADESAO (DATA VARCHAR(450)) AS " + 
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

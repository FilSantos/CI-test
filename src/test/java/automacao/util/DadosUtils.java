package automacao.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import automacao.framework.database.H2sql;

public class DadosUtils {
	final static Logger LOGGER = Logger.getLogger(DadosUtils.class);

	@DataProvider(name = "enviaProposta")
	public static Object[][] enviaProposta() throws Exception {
		String arquivo = "enviaProposta.csv";
		importacaoArquivo(arquivo,null);
		return loadData(arquivo);
	}

	@DataProvider(name = "testCSV1")
	public static Object[][] load1() throws Exception {
		String arquivo = "TestCSV.csv";
		importacaoArquivo(arquivo,"'campo1,campo2'");
		return loadData(arquivo);
	}

	@DataProvider(name = "testCSV2")
	public static Object[][] load2() throws Exception {
		String arquivo = "testCSV2.csv";
		importacaoArquivo(arquivo,null);
		return loadData(arquivo);
	}
	
	@DataProvider(name = "testCSV3")
	public static Object[][] load3() throws Exception {
		String arquivo = "testCSV2.csv";
		importacaoArquivo(arquivo,null);
		return loadData(arquivo,"'campo3'");
	}
	
	private static Object[][] loadData(String arquivoGerado) throws Exception {
		String sqlQuery = "SELECT * FROM " + arquivoGerado.split(".csv")[0];
		ResultSet returnResultSet = H2sql.returnResultSet(sqlQuery);
		int colCount = returnResultSet.getMetaData().getColumnCount();
		returnResultSet.last();
		int rowCount = returnResultSet.getRow();
		returnResultSet.beforeFirst();
		Object[][] data = new Object[rowCount][colCount];
		int row = 0;
		while (returnResultSet.next()) {
			for (int i = 0; i < colCount; i++) {
				data[row][i] = returnResultSet.getObject(i + 1);
			}
			row++;
		}
		return data;
	}
	
	private static Object[][] loadData(String arquivoGerado, String campos) throws Exception {
		
		String sqlQuery = String .format("SELECT %s FROM %s", campos , arquivoGerado.split(".csv")[0]) ;
		
		ResultSet returnResultSet = H2sql.returnResultSet(sqlQuery);
		int colCount = returnResultSet.getMetaData().getColumnCount();
		returnResultSet.last();
		int rowCount = returnResultSet.getRow();
		returnResultSet.beforeFirst();
		Object[][] data = new Object[rowCount][colCount];
		int row = 0;
		while (returnResultSet.next()) {
			for (int i = 0; i < colCount; i++) {
				data[row][i] = returnResultSet.getObject(i + 1);
			}
			row++;
		}
		return data;
	}

	private static void importacaoArquivo(String arquivoGerado, String fields) throws Exception {
		LOGGER.info("Dados sendo importados");
		String file = new java.io.File(".").getCanonicalPath()+ "/dados/" + arquivoGerado;
		List<String> createTables = new ArrayList<String>();
		createTables.add("DROP TABLE IF EXISTS " + arquivoGerado.split(".csv")[0]);
		String createTable = "CREATE TABLE %s AS " + "SELECT * FROM CSVREAD ('%s', %s, 'charset=Windows-1252 fieldSeparator=;')";
		createTables.add(String.format(createTable, arquivoGerado.split(".csv")[0],file,fields));
		for (String table : createTables) {
			H2sql.executeStatement(table);
		}
		createTables.clear();
	}
	
}
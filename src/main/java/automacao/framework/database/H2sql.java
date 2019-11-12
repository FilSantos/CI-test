package automacao.framework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.h2.tools.Server;

public class H2sql {

	final static Logger logger = Logger.getLogger(H2sql.class);

	static final String JDBC_DRIVER = "org.h2.Driver";
	// static final String DB_URL = "jdbc:h2:file:./data/data";
	static final String DB_URL = "jdbc:h2:mem:";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";
	private static Connection conn = null;

	public static void openConnection() {
		try {
			if (conn == null) {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Server.createWebServer("-webAllowOthers").start();
				logger.info("Banco de dados iniciado");
			}

		} catch (Exception e) {
			logger.error("Erro ao acessar o Banco", e);
			System.exit(-1);
		}
	}

	public static Connection getConnection() {
		return conn;
	}

	public static boolean executeStatement(String sqlStatement) {

		try {
			Statement st = null;
			st = conn.createStatement();
			st.executeUpdate(sqlStatement);
			st.close();
			conn.commit();
			return true;
		} catch (Exception e) {
			try {
				logger.error(sqlStatement, e);
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(sqlStatement, e);
			}
			return false;
		}
	}

	public static ResultSet returnResultSet(String sql) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
		} catch (SQLException ex) {
			logger.error(sql, ex);
		}
		return rs;
	}

	public static List<HashMap<String, Object>> returnResultSetList(String sql) {

		ResultSet resultSet = returnResultSet(sql);
		try {
			return convertResultSetToList(resultSet);
		} catch (SQLException e) {
			logger.error(sql, e);
			return null;
		}
	}

	public static List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}
		return list;
	}

	public static void backup(String filePath) {
		executeStatement(String.format("BACKUP to '%s'", filePath));
	}
}
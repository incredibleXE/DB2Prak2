package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MsSqlHelper {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String database = null, user = null, passwd = null, host = null;
	private boolean winlogon = false;

	public MsSqlHelper(String host, String database, String user, String passwd) {
		this.database = database;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
	}

	public MsSqlHelper(String host, String database, String user,
			String passwd, boolean winlogon) {
		this.database = database;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
	}

	private Connection connect() {
		try {
			String url = "jdbc:sqlserver://" + host + ";databaseName="
					+ database;
			if (winlogon == false) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connect = DriverManager.getConnection(url, user, passwd);
			} else {
				url = url + ";integratedSecurity=true";
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connect = DriverManager.getConnection(url);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return connect;
	}

	public static void printSQLException(SQLException ex) {

		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

					e.printStackTrace(System.err);
					System.err.println("SQLState: "
							+ ((SQLException) e).getSQLState());

					System.err.println("Error Code: "
							+ ((SQLException) e).getErrorCode());

					System.err.println("Message: " + e.getMessage());

					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	public static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;

		return false;
	}
	
	public ResultSet executeSQLQuery(String query) {
		try {
			connect = connect();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery("select * from feedback.comments");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return resultSet;
	}
}

package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SqlHelper {
	private String database = null, user = null, passwd = null, host = null;
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	public SqlHelper(String host, String database, String user, String passwd) {
		this.database = database;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
	}

	/**
	 * treats the exception before it print it to console
	 *  
	 * @param ex SQLException
	 */
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

	/**
	 * executes a normal query
	 * 
	 * @param query a normal sql statement as string 
	 * @return ResultSet
	 */
	public ResultSet executeSQLQuery(String query) {
		try {
			setConnect(connect());
			// Statements allow to issue SQL queries to the database
			setStatement(getConnect().createStatement());
			// Result set get the result of the SQL query
			setResultSet(getStatement()
					.executeQuery(query));
		} catch (SQLException e) {
			printSQLException(e);
		}
		return getResultSet();
	}
	
	/**
	 * preparedStatement way of talking to the database
	 *  
	 * @param statement SQL Query with ?
	 * @param arguments String array with arguments in order to their places in the statement
	 * @return resultSet the answer from database
	 */
	public ResultSet executePreparedStatement(String statement, String[] arguments)  {
		setConnect(connect());
		try {
			setPreparedStatement(getConnect().prepareStatement(statement));
			for(int i=1;arguments.length>=i;i++) {
				getPreparedStatement().setString(i, arguments[i-1]);
			}
			setResultSet(getPreparedStatement().executeQuery());
			return getResultSet();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return null;
	}
	
	protected abstract Connection connect();
	
	
	/**
	 * testing the connection
	 * 
	 * @return boolean - true -> connection is working | false -> not
	 */
	public boolean testConnection() {
		if(getConnect()!=null) {
			close();
		}
		setConnect(connect());
		if(getConnect()!=null) {
			close();
			return true;
		}
		return false;
	}

	/**
	 * closes everything in the right order
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
	
	public void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.print(resultSet.getMetaData().getColumnName(i)+" | ");
		}
		System.out.println();
	}

	public void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				System.out.print(resultSet.getString(i)+" | ");
			}
			System.out.println();
		}
	}
	
	public void writeResultSetWithMeta(ResultSet resultSet) throws SQLException {
		this.writeMetaData(resultSet);
		this.writeResultSet(resultSet);
	}
	
	/*
	 * GETTER / SETTER 
	 * 
	 */
	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getHost() {
		return host;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Connection getConnect() {
		return connect;
	}

	public Statement getStatement() {
		return statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
}

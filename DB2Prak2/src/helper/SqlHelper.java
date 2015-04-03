package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	public List<String[]> executeSQLQuery(String query) {
		List<String[]> rows = null;
		try {
			setConnect(connect());
			// Statements allow to issue SQL queries to the database
			setStatement(getConnect().createStatement());
			// Result set get the result of the SQL query
			setResultSet(getStatement()
					.executeQuery(query));
			
			rows = resultSetToList();
			
			close();
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		return rows;
	}
	
	/**
	 * preparedStatement way of talking to the database
	 *  
	 * @param statement SQL Query with ?
	 * @param arguments String array with arguments in order to their places in the statement
	 * @return resultSet the answer from database
	 */
	public List<String[]> executePreparedStatement(String statement, String[] arguments)  {
		List<String[]> rows = null;
		setConnect(connect());
		try {
			setPreparedStatement(getConnect().prepareStatement(statement));
			for(int i=1;arguments.length>=i;i++) {
				getPreparedStatement().setString(i, arguments[i-1]);
			}
			setResultSet(getPreparedStatement().executeQuery());
			
			rows = resultSetToList();
			
			close();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rows;
	}
	
	protected abstract Connection connect();
	
	private List<String[]> resultSetToList() throws SQLException {
		int columnCount = getResultSet().getMetaData().getColumnCount();
		List<String[]> rows = new ArrayList<String[]>();
		
		String[] rowHeader = new String[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			rowHeader[i-1]=resultSet.getMetaData().getColumnName(i);
		}
		rows.add(rowHeader);
		
        while(getResultSet().next()){
            String[] row = new String[columnCount];
            for(int i = 1;i<=columnCount;i++){
                row[i-1]=getResultSet().getString(i).trim();
            }
            rows.add(row);
        }
        
        return rows;
	}
	
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
	
	private void writeMetaData(List<String[]> list) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		String[] row = list.get(0);
		for (int i = 0; i < row.length; i++) {
			System.out.print(row[i].toString()+" | ");
		}
		System.out.println();
	}

	private void writeResultSet(List<String[]> list) throws SQLException {
		for(int a=1;list.size()>a;a++) {
			String[] row = list.get(a);
			for (int i = 0; i < row.length; i++) {
				System.out.print(row[i].toString()+" | ");
			}
			System.out.println();
		}
	}
	
	public void writeResultSetWithMeta(List<String[]> list) {
		try {
			this.writeMetaData(list);
			this.writeResultSet(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			SqlHelper.printSQLException(e);
		}
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

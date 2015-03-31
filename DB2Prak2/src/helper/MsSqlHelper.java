package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MsSqlHelper extends SqlHelper {
	private boolean winlogon = false;

	/**
	 * Constructor 
	 * winlogon is on standard value (false)
	 * 
	 * @param host address to MsSql server 
	 * @param database name of the database to use
	 * @param user as string 
	 * @param passwd as string
	 */
	public MsSqlHelper(String host, String database, String user, String passwd) {
		super(host,database,user,passwd);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param host address to MsSql server 
	 * @param database name of the database to use
	 * @param user as string 
	 * @param passwd as string
	 * @param winlogon use this value to enable windows authentication to database server
	 */
	public MsSqlHelper(String host, String database, String user,
			String passwd, boolean winlogon) {
		super(host,database,user,passwd);
		this.winlogon=winlogon;
	}
	
	/**
	 * establishs a connection to the database server
	 * 
	 * @return Connection stable connection or null
	 */
	@Override
	protected Connection connect() {
		try {
			String url = "jdbc:jtds:sqlserver://" + getHost() + ":1433;databaseName="+ getDatabase();
			if (winlogon == false) {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				setConnect(DriverManager.getConnection(url, getUser(), getPasswd()));
			} else {
				url = url + ";integratedSecurity=true";
				System.out.println("with winlogon - "+url);
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				Connection c = DriverManager.getConnection(url); 
				setConnect(c);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return getConnect();
	}
}

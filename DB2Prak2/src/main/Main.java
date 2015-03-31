package main;

import java.sql.SQLException;

import helper.MsSqlHelper;
import helper.MySqlHelper;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("====== test mysql connection ======");
		/*MySqlHelper mysql = new MySqlHelper("localhost", "table", "root", "passwd");
		if(mysql.testConnection()) {
			System.out.println("connection established");
			System.out.println("====== trying to put some date in ======");
			try {
				mysql.writeResultSetWithMeta(mysql.executeSQLQuery("SHOW databases"));
				System.out.println("==== changing database ====");
				mysql.setDatabase("gGeist");
				mysql.writeResultSetWithMeta(mysql.executeSQLQuery("SHOW tables"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				MySqlHelper.printSQLException(e);
			} finally {
				mysql.close();
			}
		}*/
		System.out.println("====== finished testing mysql ======");
		System.out.println("====== test M$sql connection ======");
		MsSqlHelper mssql = new MsSqlHelper("192.168.188.22", "PZM2_data", "user", "passwd",true);
		mssql.testConnection();
		try {
			mssql.writeResultSetWithMeta(mssql.executeSQLQuery("SELECT * FROM Kunde"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("====== test M$sql connection ======");
		
	}

}

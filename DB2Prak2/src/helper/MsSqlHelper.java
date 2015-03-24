package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public MsSqlHelper(String host, String database, String user, String passwd,boolean winlogon) {
		this.database = database;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
	}
}

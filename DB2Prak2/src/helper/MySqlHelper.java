/**
 * 
 */
package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author root
 *
 */
public class MySqlHelper extends SqlHelper {

	public MySqlHelper(String host, String database, String user, String passwd) {
		super(host, database, user, passwd);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see helper.SqlHelper#connect()
	 */
	@Override
	protected Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			setConnect(DriverManager.getConnection("jdbc:mysql://" + getHost()
					+ "/" + getDatabase() + "?" + "user=" + getUser() + "&password="
					+ getPasswd()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return getConnect();
	}

}

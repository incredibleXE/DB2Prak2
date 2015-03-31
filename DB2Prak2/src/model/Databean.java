package model;

public class Databean {
	private String host, user, passwd, databasename, backgroundStyle;
	
	public Databean() {
	}
	
	public Databean(String host, String database, String user, String passwd) {
		this.host = host;
		this.user = user;
		this.databasename = database;
		this.passwd = passwd;
		this.backgroundStyle = "-fx-background-color: lightgrey;";
	}

	// GETTER & SETTER
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the databasename
	 */
	public String getDatabasename() {
		return databasename;
	}

	/**
	 * @param databasename the databasename to set
	 */
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	/**
	 * @return the backgroundStyle
	 */
	public String getBackgroundStyle() {
		return backgroundStyle;
	}

	/**
	 * @param backgroundStyle the backgroundStyle to set
	 */
	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}
	

}

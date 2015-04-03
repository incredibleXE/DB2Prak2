package model;

import javafx.stage.Stage;

public class Databean {
	// defines which database should be used
	public enum DATABASE_TYPE {
	    MsSQL, MySQL;
	}
	private DATABASE_TYPE type = null;
	
	// SQL connection informations
	private String host, user, passwd, databasename;
	
	// winlogon variable for MsSql
	private Boolean winlogon = false;
	
	// primary stage
	private Stage stage = null;
	
	// Stage height and width
	private int stage_width = 0, stage_height = 0;
	
	public Databean() {
	}
	
	public Databean(String host, String database, String user, String passwd, DATABASE_TYPE type) {
		this.type = type;
		this.host = host;
		this.user = user;
		this.databasename = database;
		this.passwd = passwd;
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
	 * @return the winlogon
	 */
	public Boolean getWinlogon() {
		return winlogon;
	}

	/**
	 * @param winlogon the winlogon to set
	 */
	public void setWinlogon(Boolean winlogon) {
		this.winlogon = winlogon;
	}

	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return the stage_width
	 */
	public int getStage_width() {
		return stage_width;
	}

	/**
	 * @param stage_width the stage_width to set
	 */
	public void setStage_width(int stage_width) {
		this.stage_width = stage_width;
	}

	/**
	 * @return the stage_height
	 */
	public int getStage_height() {
		return stage_height;
	}

	/**
	 * @param stage_height the stage_height to set
	 */
	public void setStage_height(int stage_height) {
		this.stage_height = stage_height;
	}

	/**
	 * @return the type
	 */
	public DATABASE_TYPE getType() {
		return type;
	}
	

}

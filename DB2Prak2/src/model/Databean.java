package model;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * save class for almost all informations sharing for all controller 
 * @author incredibleXE
 * @version 0.1
 *
 */
public class Databean {
	/**
	 * ENUM for decision which SQL Connector should be used
	 * 
	 * @version 0.1
	 * @author incredibleXE
	 */
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
	
	//Console for sql Exceptions
	private TextArea console = new TextArea();
	
	// some strings
	private String newEntryDisplayTxt = "(wird automatisch gefüllt)";
	
	/**
	 * minimal constructor
	 * 
	 * @param type ENUM Database type {@see DATABASE_TYPE}
	 */
	public Databean(DATABASE_TYPE type) {
		this.type = type;
	}
	
	/**
	 * constructor
	 * 
	 * @param host server where database is running on
	 * @param database database name to which should connecting to
	 * @param user for authentication
	 * @param passwd for authentication
	 * @param winlogon use this value to enable windows authentication to database server
	 */
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

	/**
	 * @return the console
	 */
	public TextArea getConsole() {
		return console;
	}

	/**
	 * @return the newEntryDisplayTxt
	 */
	public String getNewEntryDisplayTxt() {
		return newEntryDisplayTxt;
	}
	

}

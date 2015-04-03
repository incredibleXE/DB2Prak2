package controller;

import helper.MsSqlHelper;
import helper.MySqlHelper;
import helper.SqlHelper;
import model.Databean;

abstract class dbController {
	private Databean bean = null;
	private SqlHelper sqlHelper = null;

	public dbController(Databean bean) {
		this.bean = bean;
		this.createSqlHelper();
	}
	
	/**
	 * creates MsSqlHelper class and tests connection
	 * 
	 * @return MsSqlHelper object if connection test succeeded but null if not  
	 */
	private MsSqlHelper makeMsSqlHelper() {
		MsSqlHelper mssql = new MsSqlHelper(bean.getHost(), bean.getDatabasename(), bean.getUser(), bean.getPasswd(), bean.getWinlogon());
		if(mssql.testConnection())
			return mssql;
		else
			return null;
	}
	
	/**
	 * creates MySqlHelper class and tests connection
	 * 
	 * @return MySqlHelper object if connection test succeeded but null if not  
	 */
	private MySqlHelper makeMySqlHelper() {
		MySqlHelper mysql = new MySqlHelper(bean.getHost(), bean.getDatabasename(), bean.getUser(), bean.getPasswd());
		if(mysql.testConnection())
			return mysql;
		else
			return null;
	}
	
	/**
	 * creates specified db helper
	 */
	private void createSqlHelper() {
		switch(bean.getType()) {
		case MySQL:
			this.sqlHelper = this.makeMySqlHelper();
			break;
		case MsSQL:
			this.sqlHelper = this.makeMsSqlHelper();
			break;
		default:
			this.sqlHelper = null;
			break;
		}
	}

	/**
	 * @return the sqlHelper
	 */
	public SqlHelper getSqlHelper() {
		return sqlHelper;
	}
	
	

}

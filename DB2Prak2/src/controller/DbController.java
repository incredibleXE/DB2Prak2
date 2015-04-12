package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import helper.MsSqlHelper;
import helper.MySqlHelper;
import helper.SqlHelper;
import model.Databean;

/**
 * abstract class
 * something like a bridge class between tableController (customerControler e.g.) and the SqlHelper classes
 *  
 * @version 0.1
 * @author franacher
 *
 */
abstract class DbController {
	protected List<String[]> result = null;
	protected int displayedRow = 1;
	
	private Databean bean = null;
	private SqlHelper sqlHelper = null;
	
	
	// buttons
	protected Button button_last = new Button("<");
	protected Button button_new = new Button("Neu");
	protected Button button_save = new Button("Speichern");
	protected Button button_delete = new Button("Löschen");
	protected Button button_next = new Button(">");

	/**
	 * Constructor
	 * creates sql helper
	 * gets its information about connection type and so on from bean {@link model.Databean}
	 * @param bean Databean from {@link main.Main#main(String[])} method
	 */
	public DbController(Databean bean) {
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
		try {
			if(mssql.testConnection())
				return mssql;
		} catch (SQLException e) {
			bean.getConsole().setText(SqlHelper.printSQLException(e));
		}
		return null;
	}
	
	/**
	 * creates MySqlHelper class and tests connection
	 * 
	 * @return MySqlHelper object if connection test succeeded but null if not  
	 */
	private MySqlHelper makeMySqlHelper() {
		MySqlHelper mysql = new MySqlHelper(bean.getHost(), bean.getDatabasename(), bean.getUser(), bean.getPasswd());
		try {
			if(mysql.testConnection())
				return mysql;
		} catch (SQLException e) {
			bean.getConsole().setText(SqlHelper.printSQLException(e));
		}
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
	
	/**
	 * @see helper.SqlHelper#executeSQLQuery(String)
	 * @param query
	 * @return List<String[]> with all the informations from db
	 */
	public List<String[]> executeSQLQuery(String query) {
		try {
			return sqlHelper.executeSQLQuery(query);
		} catch (SQLException e) {
			bean.getConsole().setText(helper.SqlHelper.printSQLException(e));
		}
		return null;
	}
	
	/**
	 * @see helper.SqlHelper#executeSQLQueryWithoutResult(String)
	 * @param query
	 */
	public void executeSQLQueryWithoutResult(String query) {
		try {
			sqlHelper.executeSQLQueryWithoutResult(query);
		} catch (SQLException e) {
			bean.getConsole().setText(helper.SqlHelper.printSQLException(e));
		}
	}
	
	/**
	 * @see helper.SqlHelper#executePreparedStatement(String, String[])
	 * @param statement
	 * @param arguments
	 * @return List<String[]> with all the information from db
	 */
	public List<String[]> executePreparedStatement(String statement, String[] arguments) {
		try {
			return sqlHelper.executePreparedStatement(statement, arguments);
		} catch (SQLException e) {
			bean.getConsole().setText(helper.SqlHelper.printSQLException(e));
		}
		return null;
	}
	
	/**
	 * deletes entry(s) which are true for where statement
	 * @param tableName name of the table
	 * @param whereStatement everything after "WHERE" in SQL annotation
	 */
	public void deleteEntry(String tableName, String whereStatement) {
		try {
			sqlHelper.executeSQLQueryWithoutResult("DELETE FROM "+tableName+" WHERE "+whereStatement);
		} catch (SQLException e) {
			bean.getConsole().setText(helper.SqlHelper.printSQLException(e));
		}
	}
	
	/**
	 * @see helper.SqlHelper#writeResultSetWithMeta(List)
	 * @param result
	 */
	protected void writeResultSetWithMeta(List<String[]> result) {
		sqlHelper.writeResultSetWithMeta(result);
	}
	
	
	/**
	 * updates the form, resets the row number to start (1 by default) and updates the result list from SQL
	 * @param sql_select SQL Query for Select 
	 */
	public void updateForm(String sql_select) {
		result = executeSQLQuery(sql_select);
		displayedRow = 1;
		writeResultSetWithMeta(result);
		
		writeDataToForm();
		button_delete.setDisable(false);
	}
	
	/**
	 * builds the button row at the end of the form
	 * @return hBox with buttons
	 */
	protected HBox getButtons() {
		HBox hBox = new HBox();
		{
			button_last.getStyleClass().addAll("pill-left-btn");
			button_new.getStyleClass().addAll("pill-center-left-btn");
			button_save.getStyleClass().addAll("pill-center-left-btn");
			button_delete.getStyleClass().addAll("pill-center-btn");
			button_next.getStyleClass().addAll("pill-right-btn");
		}
		hBox.getChildren().addAll(button_last,button_new,button_save,button_delete,button_next);

	    return hBox;
	}
	
	/**
	 * initiates button listener for all five buttons
	 */
	protected void initListener() {
		button_last.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(displayedRow>1) {
            		displayedRow--;
            	} else {
            		displayedRow=result.size()-1;
            	}
                writeDataToForm();
                button_new.setText("Neu");
                button_delete.setDisable(false);
            }
        });
		
		button_next.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(result.size()>displayedRow+1) {
            		displayedRow++;
            	} else {
            		displayedRow=1;
            	}
                writeDataToForm();
                button_new.setText("Neu");
                button_delete.setDisable(false);
            }
        });
		
		button_save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	button_save_handle();
            }
        });
		
		button_new.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	button_new_handle();
            }
        });
		
		button_delete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	button_delete_handle();
            }
        });
	}
	
	/**
	 * handler for new button
	 */
	protected abstract void button_new_handle();
	
	/**
	 * handler for delete button
	 */
	protected abstract void button_delete_handle();
	
	/**
	 * handler for save button
	 */
	protected abstract void button_save_handle();
	
	/**
	 * writes data to form
	 */
	protected abstract void writeDataToForm();
	
	/**
	 * builds the form and adds it to the grid from {@link controller.GuiController}
	 * Additional it adds four buttons at the bottom for last entry, next entry, new entry and delete entry 
	 */
	protected abstract void makeForm();
	
	protected ObservableList<ChoiceBoxObj> makeObservableList(String tableName, String nameField, String idField) {
		List<String[]> sqlList = this.executeSQLQuery("SELECT "+idField+", "+nameField+" FROM "+tableName+";");
		ObservableList<ChoiceBoxObj> comboList = FXCollections.observableArrayList();
		for(int i=1;sqlList.size()>i;i++)
			comboList.add(new ChoiceBoxObj(sqlList.get(i)[1],sqlList.get(i)[0]));
		  
		
		return comboList;
	}
	
	protected class ChoiceBoxObj {
		private  String description;
		private  String db_id;        

		public ChoiceBoxObj(String description,String db_id) {
		    this.description = description;         
		    this.db_id = db_id;             
		}
		public ChoiceBoxObj(String db_id) {         
		    this.db_id = db_id;  
		}
		public String getDb_id() {
		    return db_id;
		}
		public void setDb_id(String db_id) {
			this.db_id= db_id;
		}         
		public String getDescription() {
		    return description;
		}
		public void setDescription(String description) {
		    this.description = description;
		}                 
		@Override
		public String toString() {
		    return description;
		}   
		@Override
		public int hashCode() {
		    int hash = 0;
		    hash += (db_id != null ? db_id.hashCode() : 0);
		    return hash;
		}

		@Override
		public boolean equals(Object object) {
		     String otherDb_id = "";
		    if (object instanceof ChoiceBoxObj) {
		    	otherDb_id = ((ChoiceBoxObj)object).db_id;
		    } else if(object instanceof String){
		    	otherDb_id = (String)object;
		    } else {
		        return false;
		    }   

		    if ((this.db_id == null && otherDb_id != null) || (this.db_id != null && !this.db_id.equals(otherDb_id))) {
		        return false;
		    }
		    return true;
		}    
	}
}

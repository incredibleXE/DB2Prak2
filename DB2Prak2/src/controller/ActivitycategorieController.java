package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Databean;

/**
 * controller for table "Taetigkeitskategorie" 
 * 
 * @extends controller.DbController.class
 * @author incredibleXE
 * @version 0.1
 *
 */
public class ActivitycategorieController extends DbController {
	private GridPane grid = null;
	
	// SQL Statements
	private String sql_tableName = "Taetigkeitskategorie";
	private String sql_idField = "TaetigkeitskategorieID";
	private String sql_select = "SELECT * FROM "+sql_tableName;
	private String sql_newEntry = "INSERT INTO "+sql_tableName+" (Bezeichnung) VALUES ";
	private String sql_saveEntry = "UPDATE "+sql_tableName+" SET ";
	
	// text fields
	private TextField textField_id = new TextField();
	private TextField textField_name = new TextField();
	
	// somem strings
	private String newEntryDisplayTxt; 
	
	/**
	 * constructor
	 * 
	 * @see controller.DbController.class#constructor()
	 * @param grid GridPane from {@link controller.GuiController.class)
	 * @param bean Databean where all informations are saved in {@link model.Databean.class}
	 */
	public ActivitycategorieController(GridPane grid,Databean bean) {
		super(bean);
		
		// language from Databean
		newEntryDisplayTxt = bean.getNewEntryDisplayTxt();
		
		updateForm(sql_select);
		
		this.grid = grid;
		
		// make id field not editable, because id was declared by database
		textField_id.setEditable(false);
		
		makeForm();
		
		//
		// Listener 
		//
		initListener();
	}

	/**
	 * @see DbController#writeDataToForm()
	 */
	@Override
	protected void writeDataToForm() {
		textField_id.setText(result.get(this.displayedRow)[0]);
		textField_name.setText(result.get(this.displayedRow)[1]);
	}
	
	/**
	 * @see DbController#makeForm()
	 */
	@Override
	protected void makeForm() {
		GridPane formGrid = new GridPane();
		int column = 0;
		{
			{ // column 1
				formGrid.add(new Label("ID:"), 0, column);
				formGrid.add(textField_id, 1, column);
			}
			{ // column 2
				column++;
				formGrid.add(new Label("Kategoriename:"), 0, column);
				formGrid.add(textField_name, 1, column);
			}
		}
		this.grid.add(formGrid, 0, 0);
		
		grid.add(getButtons(), 0, 1);
	}

	/**
	 * @see DbController#button_new_handle()
	 */
	@Override
	protected void button_new_handle() {
		textField_id.setText(newEntryDisplayTxt);
		textField_name.setText("");
		button_new.setText("Reset");
		button_delete.setDisable(true);
	}

	/**
	 * @see DbController#button_delete_handle()
	 */
	@Override
	protected void button_delete_handle() {
		deleteEntry(sql_tableName,sql_idField+"="+textField_id.getText().toString());
    	
    	updateForm(sql_select);
    	button_new.setText("Neu");
	}
	
	/**
	 * @see DbController#button_save_handle()
	 */
	@Override
	protected void button_save_handle() {
		if(textField_id.getText().toString().equals(newEntryDisplayTxt)) {
    		String valueTxt = "('"+textField_name.getText()+"')";
    		executeSQLQueryWithoutResult(sql_newEntry+valueTxt);
    		button_new.setText("Neu");
    	} else {
    		String valueTxt = "Abteilungsname='"+textField_name.getText()+"'";
    		executeSQLQueryWithoutResult(sql_saveEntry+valueTxt);
    	}
    	updateForm(sql_select);
	}
}

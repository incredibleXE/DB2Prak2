package controller;

import model.Databean;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * controller for table "Mitarbeiter" 
 * 
 * @extends controller.DbController.class
 * @author incredibleXE
 * @version 0.1
 *
 */
public class WorkerController extends DbController {
	private GridPane grid = null;
	
	// SQL Statements
	private String sql_tableName = "Mitarbeiter";
	private String sql_idField = "MitNr";
	private String sql_select = "SELECT * FROM "+sql_tableName;
	private String sql_newEntry = "INSERT INTO "+sql_tableName+" (FaName,VoName,Studenlohn,Abteilung,Arbeitsende,Arbeitsbegin) VALUES ";
	private String sql_saveEntry = "UPDATE "+sql_tableName+" SET ";
	
	// text fields
	private TextField textField_id = new TextField();
	private TextField textField_name = new TextField();
	private TextField textField_forename = new TextField();
	private TextField textField_amountperh = new TextField();
	private TextField textField_abteilung = new TextField();
	private TextField textField_finishedWork = new TextField();
	private TextField textField_startWork = new TextField();
	
	// somem strings
	private String newEntryDisplayTxt; 
	
	/**
	 * constructor
	 * 
	 * @see controller.DbController.class#constructor()
	 * @param grid GridPane from {@link controller.GuiController.class)
	 * @param bean Databean where all informations are saved in {@link model.Databean.class}
	 */
	public WorkerController(GridPane grid, Databean bean) {
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
		textField_forename.setText(result.get(this.displayedRow)[2]);
		textField_amountperh.setText(result.get(this.displayedRow)[3]);
		textField_abteilung.setText(result.get(this.displayedRow)[4]);
		textField_finishedWork.setText(result.get(this.displayedRow)[5]);
		textField_startWork.setText(result.get(this.displayedRow)[5]);
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
				formGrid.add(new Label("Name:"), 0, column);
				formGrid.add(textField_name, 1, column);
			}
			{ // column 3
				column++;
				formGrid.add(new Label("Vorname:"), 0, column);
				formGrid.add(textField_forename, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Stundenlohn:"), 0, column);
				formGrid.add(textField_amountperh, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Abteilung:"), 0, column);
				formGrid.add(textField_abteilung, 1, column);
			}
			{ // column 5
				column++;
				formGrid.add(new Label("Arbeitsende:"), 0, column);
				formGrid.add(textField_finishedWork, 1, column);
			}
			{ // column 6
				column++;
				formGrid.add(new Label("Arbeitsbeginn:"), 0, column);
				formGrid.add(textField_startWork, 1, column);
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
		textField_forename.setText("");
		textField_amountperh.setText("");
		textField_abteilung.setText("");
		textField_finishedWork.setText("00:00:00");
		textField_startWork.setText("00:00:00");
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
    		String valueTxt = "('"+textField_name.getText()+"','"+textField_forename.getText()+"','"+textField_amountperh.getText()+"','"+textField_abteilung.getText()+"','"+textField_finishedWork.getText()+"','"+textField_startWork.getText()+"')";
    		executeSQLQueryWithoutResult(sql_newEntry+valueTxt);
    		button_new.setText("Neu");
    	} else {
    		String valueTxt = "Name='"+textField_name.getText()+"',Vorname='"+textField_forename.getText()
    				+"',Strasse='"+textField_amountperh.getText()+"',HausNr='"+textField_abteilung.getText()
    				+"',Postleitzahl='"+textField_finishedWork.getText()+"',Postleitzahl='"+textField_startWork.getText()
    				+"' WHERE "+sql_idField+"="+textField_id.getText();
    		executeSQLQueryWithoutResult(sql_saveEntry+valueTxt);
    	}
    	updateForm(sql_select);
	}

}

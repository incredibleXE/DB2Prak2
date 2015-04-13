package controller;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Databean;

/**
 * controller for table "Projekt"
 * 
 * @extends controller.DbController.class
 * @author incredibleXE
 * @version 0.1
 */
public class ProjectController extends DbController {

	private GridPane grid = null;
	
	// SQL Statements
	private String sql_tableName = "Projekt";
	private String sql_idField = "ProjektID";
	private String sql_select = "SELECT * FROM "+sql_tableName;
	private String sql_newEntry = "INSERT INTO "+sql_tableName+" (Bezeichnung,KundeNr,ProjektkategorieID) VALUES ";
	private String sql_saveEntry = "UPDATE "+sql_tableName+" SET ";
	
	// text fields
	private TextField textField_id = new TextField();
	private TextField textField_name = new TextField();
	
	// somem strings
	private String newEntryDisplayTxt; 
	
	// choice boxes
	private ChoiceBox<ChoiceBoxObj> choiceBox_customer= new ChoiceBox<ChoiceBoxObj>();
	private ChoiceBox<ChoiceBoxObj> choiceBox_projectcategorie= new ChoiceBox<ChoiceBoxObj>();
	
	/**
	 * constructor
	 * 
	 * @see controller.DbController.class#constructor()
	 * @param grid GridPane from {@link controller.GuiController.class)
	 * @param bean Databean where all informations are saved in {@link model.Databean.class}
	 */
	public ProjectController(GridPane grid,Databean bean) {
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
	 * @see DbController#updateForm()
	 */
	@Override
	public void updateForm(String sql_string) {
		super.updateForm(sql_string);
		
		choiceBox_customer.setItems(makeObservableList("Kunde", "Concat(RTRIM(Name),', ',RTRIM(Vorname))", "KundenNr"));
		choiceBox_customer.setValue(new ChoiceBoxObj(result.get(this.displayedRow)[2]));
		
		choiceBox_projectcategorie.setItems(makeObservableList("Projektkategorie", "Bezeichnung", "ProjektkategorieID"));
		choiceBox_projectcategorie.setValue(new ChoiceBoxObj(result.get(this.displayedRow)[3]));
	}
	
	/**
	 * writes information from sql list into form
	 *  
	 * @param row from sql list
	 */
	@Override
	protected void writeDataToForm() {
		textField_id.setText(result.get(this.displayedRow)[0]);
		textField_name.setText(result.get(this.displayedRow)[1]);
		
		choiceBox_customer.setValue(new ChoiceBoxObj(result.get(this.displayedRow)[2]));
		choiceBox_projectcategorie.setValue(new ChoiceBoxObj(result.get(this.displayedRow)[3]));
	}
	
	/**
	 * builds the form and adds it to the grid from {@link controller.GuiController}
	 * Additional it adds four buttons at the bottom for last entry, next entry, new entry and delete entry 
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
				formGrid.add(new Label("Kundennummer:"), 0, column);
				formGrid.add(choiceBox_customer, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Projektkategorienummer:"), 0, column);
				formGrid.add(choiceBox_projectcategorie, 1, column);
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
    		String valueTxt = "('"+textField_name.getText()+"','"+choiceBox_customer.getSelectionModel().getSelectedItem().getDb_id()+"','"+choiceBox_projectcategorie.getSelectionModel().getSelectedItem().getDb_id()+"')";
    		executeSQLQueryWithoutResult(sql_newEntry+valueTxt);
    		button_new.setText("Neu");
    	} else {
    		String valueTxt = "Bezeichnung='"+textField_name.getText()+"',KundeNr='"+choiceBox_customer.getSelectionModel().getSelectedItem().getDb_id()
    				+"',ProjektkategorieID='"+choiceBox_projectcategorie.getSelectionModel().getSelectedItem().getDb_id()+"' WHERE "+sql_idField+"="+textField_id.getText();
    		executeSQLQueryWithoutResult(sql_saveEntry+valueTxt);
    	}
    	updateForm(sql_select);
	}


}

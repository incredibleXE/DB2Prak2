package controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Databean;

/**
 * controller for table "Tätigkeit"
 * 
 * @extends controller.DbController.class
 * @author franacher
 * @version 0.1
 */
public class ActivityController extends DbController {
	private GridPane grid = null;
	
	// SQL Statements
	private String sql_tableName = "Taetigkeit";
	private String sql_idField = "TaetigkeitsID";
	private String sql_select = "SELECT * FROM "+sql_tableName;
	private String sql_newEntry = "INSERT INTO "+sql_tableName+" (Bezeichnung,Stundenlohn,TaetigkeitskategorieID) VALUES ";
	private String sql_saveEntry = "UPDATE "+sql_tableName+" SET ";
	
	// text fields
	private TextField textField_id = new TextField();
	private TextField textField_name = new TextField();
	private TextField textField_amountperhour = new TextField();
	private TextField textField_activitycategory = new TextField();
	
	private ChoiceBox<CountryObj> choiceBoxActivitycategory = new ChoiceBox<CountryObj>();
	private ObservableList<CountryObj> CountrycomboList = null;
	
	// somem strings
	private String newEntryDisplayTxt; 
	
	/**
	 * constructor
	 * 
	 * @see controller.DbController.class#constructor()
	 * @param grid GridPane from {@link controller.GuiController.class)
	 * @param bean Databean where all informations are saved in {@link model.Databean.class}
	 */
	public ActivityController(GridPane grid,Databean bean) {
		super(bean);
		
		// language from Databean
		newEntryDisplayTxt = bean.getNewEntryDisplayTxt();
		
		updateForm(sql_select);
		
		this.grid = grid;
		
		// make id field not editable, because id was declared by database
		textField_id.setEditable(false);
		
		makeForm();

		choiceBoxActivitycategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CountryObj>() {

	        public void changed(ObservableValue<? extends CountryObj> arg0, CountryObj arg1, CountryObj arg2) {
	            if (arg2 != null) {
	                System.out.println("Selected Country: " + arg2.getTCountryCode());
	            }
	        }

	    });
		
		//
		// Listener 
		//
		initListener();
	}
	
	@Override
	public void updateForm(String sql_string) {
		super.updateForm(sql_string);
		List<String[]> sqlList = super.executeSQLQuery("SELECT TaetigkeitskategorieID, Bezeichnung FROM Taetigkeitskategorie");
		CountrycomboList = FXCollections.observableArrayList();
		for(int i=1;sqlList.size()>i;i++)
			CountrycomboList.add(new CountryObj(sqlList.get(i)[1],sqlList.get(i)[0])); 

		choiceBoxActivitycategory.setItems(CountrycomboList);
		choiceBoxActivitycategory.setValue(new CountryObj(result.get(this.displayedRow)[3]));
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
		textField_amountperhour.setText(result.get(this.displayedRow)[2]);
		textField_activitycategory.setText(result.get(this.displayedRow)[3]);
		choiceBoxActivitycategory.setValue(new CountryObj(result.get(this.displayedRow)[3]));
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
				formGrid.add(new Label("Stundenlohn:"), 0, column);
				formGrid.add(textField_amountperhour, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Tätigkeitskategorie:"), 0, column);
				formGrid.add(textField_activitycategory, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Tätigkeitskategorie:"), 0, column);
				formGrid.add(choiceBoxActivitycategory, 1, column);
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
		textField_amountperhour.setText("");
		textField_activitycategory.setText("");
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
    		String valueTxt = "('"+textField_name.getText()+"','"+textField_amountperhour.getText()+"','"+textField_activitycategory.getText()+"')";
    		executeSQLQueryWithoutResult(sql_newEntry+valueTxt);
    		button_new.setText("Neu");
    	} else {
    		String valueTxt = "Bezeichnung='"+textField_name.getText()+"',Stundenlohn='"+textField_amountperhour.getText()
    				+"',TaetigkeitskategorieID='"+this.choiceBoxActivitycategory.getSelectionModel().getSelectedItem().getTCountryCode()+"' WHERE "+sql_idField+"="+textField_id.getText();
    		executeSQLQueryWithoutResult(sql_saveEntry+valueTxt);
    	}
    	updateForm(sql_select);
	}
	
	public class CountryObj {
		private  String TCountryDescr;
		private  String TCountryCode;        

		public CountryObj(String CountryDescr,String CountryCode) {
		    this.TCountryDescr = CountryDescr;         
		    this.TCountryCode = CountryCode;             
		}
		public CountryObj(String CountryCode) {         
		    this.TCountryCode = CountryCode;  
		}
		public String getTCountryCode() {
		    return TCountryCode;
		}
		public void setTCountryCode(String fComp) {
		    TCountryCode= fComp;
		}         
		public String getTCountryDescr() {
		    return TCountryDescr;
		}
		public void setCountryDescr(String fdescr) {
		    TCountryDescr = fdescr;
		}                 
		@Override
		public String toString() {
		    return TCountryDescr;
		}   
		@Override
		public int hashCode() {
		    int hash = 0;
		    hash += (TCountryCode != null ? TCountryCode.hashCode() : 0);
		    return hash;
		}

		@Override
		public boolean equals(Object object) {
		     String otherTCountryCode = "";
		    if (object instanceof CountryObj) {
		        otherTCountryCode = ((CountryObj)object).TCountryCode;
		    } else if(object instanceof String){
		        otherTCountryCode = (String)object;
		    } else {
		        return false;
		    }   

		    if ((this.TCountryCode == null && otherTCountryCode != null) || (this.TCountryCode != null && !this.TCountryCode.equals(otherTCountryCode))) {
		        return false;
		    }
		    return true;
		}    
	}
}

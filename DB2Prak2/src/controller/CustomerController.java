package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import helper.SqlHelper;
import model.Databean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CustomerController extends dbController {
	private SqlHelper sqlHelper = null;
	private GridPane grid = null;
	
	private List<String[]> result = null;
	private int displayedRow = 1;
	
	// SQL Statements
	private String sql_select = "SELECT * FROM Kunde";
	
	// text fields
	private TextField textField_id = new TextField();;
	private TextField textField_name = new TextField();
	private TextField textField_forename = new TextField();
	private TextField textField_street = new TextField();
	private TextField textField_number = new TextField();
	private TextField textField_postal = new TextField();
	
	private Button button_last = new Button("<");
	private Button button_new = new Button("Neu");
	private Button button_save = new Button("Speichern");
	private Button button_delete = new Button("Lˆschen");
	private Button button_next = new Button(">");
	
	public CustomerController(GridPane grid, Databean bean) {
		super(bean);
		this.sqlHelper = getSqlHelper();
		result = sqlHelper.executeSQLQuery(sql_select);
		sqlHelper.writeResultSetWithMeta(result);
		
		writeDataToFormular(displayedRow);
		
		this.grid = grid;
		
		makeFormular();
		
		button_last.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(displayedRow>1) {
            		displayedRow--;
            	}
                writeDataToFormular(displayedRow);
            }
        });
		button_next.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(result.size()>displayedRow+1) {
            		displayedRow++;
            	}
                writeDataToFormular(displayedRow);
            }
        });
	}
	
	/**
	 * writes information from sql list into form 
	 * @param row from sql list
	 */
	private void writeDataToFormular(int row) {
		textField_id.setText(result.get(row)[0]);
		textField_name.setText(result.get(row)[1]);
		textField_forename.setText(result.get(row)[2]);
		textField_street.setText(result.get(row)[3]);
		textField_number.setText(result.get(row)[4]);
		textField_postal.setText(result.get(row)[5]);
	}
	
	/**
	 * builds the form and adds it to the grid from {@link controller.GuiController}
	 */
	private void makeFormular() {
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
				formGrid.add(new Label("Straﬂe:"), 0, column);
				formGrid.add(textField_street, 1, column);
			}
			{ // column 4
				column++;
				formGrid.add(new Label("Hausnummer:"), 0, column);
				formGrid.add(textField_number, 1, column);
			}
			{ // column 5
				column++;
				formGrid.add(new Label("postal:"), 0, column);
				formGrid.add(textField_postal, 1, column);
			}
		}
		this.grid.add(formGrid, 0, 0);
		
		HBox hBox = new HBox();
		{
			button_last.getStyleClass().addAll("pill-left-btn");
			button_new.getStyleClass().addAll("pill-center-left-btn");
			button_save.getStyleClass().addAll("pill-center-left-btn");
			button_delete.getStyleClass().addAll("pill-center-btn");
			button_next.getStyleClass().addAll("pill-right-btn");
		}
		hBox.getChildren().addAll(button_last,button_new,button_save,button_delete,button_next);

	    grid.add(hBox, 0, 1);
	}
}

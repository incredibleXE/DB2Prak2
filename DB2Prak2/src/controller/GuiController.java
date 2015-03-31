package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Databean;

public class GuiController {
	private Databean bean = null;
	
	public GuiController(Databean bean) {
		this.bean = bean;
	}
	
	public void start(Stage mainStage) {
	      mainStage.setTitle("DB2Prak2");
	      Group root = new Group();
	      Scene scene = new Scene(root, 240, 100);
	      //scene.getStylesheets().add("stylesheet.css");
	 
	      TabPane tabPane = new TabPane();
	 
	      BorderPane borderPane = new BorderPane();
	      { // Kunden Tab
	         Tab tab = new Tab();
	         tab.setText("Kunde");
	         GridPane grid = new GridPane();
	         {
	        	 new CustomerController(grid);
	         }
	         tab.setContent(grid);
	         tabPane.getTabs().add(tab);
	         
	         Tab tab2 = new Tab();
	         tab2.setText("Mitarbeiter");
	         GridPane grid2 = new GridPane();
	         {
	        	 new WorkerController(grid2);
	         }
	         tab2.setContent(grid2);
	         tabPane.getTabs().add(tab2);
	         
	         Tab tab3 = new Tab();
	         tab3.setText("Mitarbeiter");
	         GridPane grid3 = new GridPane();
	         {
	        	 new WorkerController(grid3);
	         }
	         tab3.setContent(grid3);
	         tabPane.getTabs().add(tab3);
	      }
	 
	      // Verfügbaren Platz ausnützen:
	      borderPane.prefHeightProperty()
	                .bind(scene.heightProperty());
	      borderPane.prefWidthProperty()
	                .bind(scene.widthProperty());
	 
	      borderPane.setCenter(tabPane);
	      root.getChildren().add(borderPane);
	 
	      mainStage.setScene(scene);
	      mainStage.show();
	}

}

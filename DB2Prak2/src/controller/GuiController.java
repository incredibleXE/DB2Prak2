package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Databean;

public class GuiController {
	private Databean bean = null;
	
	public GuiController(Databean bean) {
		this.bean = bean;
	}
	
	public void start() {
	      bean.getStage().setTitle("DB2Prak2");
	      Group root = new Group();
	      Scene scene = new Scene(root, bean.getStage_width(), bean.getStage_height());
	      scene.getStylesheets().add("/resources/stylesheet.css");
	 
	      TabPane tabPane = new TabPane();
	      tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
	 
	      BorderPane borderPane = new BorderPane();
	      {
	         Tab tab = new Tab();
	         tab.setText("Kunde");
	         GridPane grid = new GridPane();
	         {
	        	 new CustomerController(grid, bean);
	         }
	         tab.setContent(grid);
	         
	         Tab tab1 = new Tab("Mitarbeiter");
	         GridPane grid1 = new GridPane();
	         {
	        	 new WorkerController(grid1);
	         }
	         tab1.setContent(grid1);
	         
	         Tab tab2 = new Tab("test");
	         GridPane grid2 = new GridPane();
	         {
	        	 new WorkerController(grid2);
	         }
	         tab2.setContent(grid2);
	         
	         tabPane.getTabs().addAll(tab,tab1,tab2);
	      }
	 
	      borderPane.prefHeightProperty()
	                .bind(scene.heightProperty());
	      borderPane.prefWidthProperty()
	                .bind(scene.widthProperty());
	 
	      borderPane.setCenter(tabPane);
	      root.getChildren().add(borderPane);
	 
	      bean.getStage().setScene(scene);
	      bean.getStage().show();
	}

}

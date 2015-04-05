package controller;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Databean;

/**
 * builds the complete GUI and calls the sub controller
 * 
 * @author incredibleXE
 * @version 0.1
 *
 */
public class GuiController {
	private Group root;
	private Scene scene;
	private BorderPane borderPane,borderPane2;
	private TabPane tabPane;
	private StackPane stackPane;
	private TitledPane t1,t2;
	private Accordion accordion; 
	
	private Databean bean = null;
	
	/**
	 * constructor
	 * 
	 * @param bean {@link model.Databean}
	 */
	public GuiController(Databean bean) {
		this.bean = bean;
	}
	
	/**
	 * start method 
	 * puts everything what has to do with gui together
	 */
	public void start() {
	      bean.getStage().setTitle("DB2Prak2");
	      root = new Group();
	      scene = new Scene(root, bean.getStage_width(), bean.getStage_height());
	      scene.getStylesheets().add("/resources/stylesheet.css");
	      {
		      {
			      stackPane = new StackPane();
			      stackPane.prefHeightProperty().bind(scene.heightProperty());
			      stackPane.prefWidthProperty().bind(scene.widthProperty());
			      {
	
				      accordion = new Accordion();
				      {
					      borderPane = new BorderPane();
					      {
					    	  tabPane = new TabPane();
						      tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
						        	 new WorkerController(grid1, bean);
						         }
						         tab1.setContent(grid1);
						         
						         Tab tab2 = new Tab("Abteilung");
						         GridPane grid2 = new GridPane();
						         {
						        	 new DepartmentController(grid2, bean);
						         }
						         tab2.setContent(grid2);
						         
						         Tab tab3 = new Tab("Projektkategorie");
						         GridPane grid3 = new GridPane();
						         {
						        	 new DepartmentController(grid3, bean);
						         }
						         tab3.setContent(grid3);
						         
						         Tab tab4 = new Tab("Tätigkeitskategorie");
						         GridPane grid4= new GridPane();
						         {
						        	 new ActivitycategorieController(grid4, bean);
						         }
						         tab4.setContent(grid4);
						         
						         Tab tab5 = new Tab("Tätigkeit");
						         GridPane grid5= new GridPane();
						         {
						        	 new ActivityController(grid5, bean);
						         }
						         tab5.setContent(grid5);
						         
						         Tab tab6 = new Tab("Projekt");
						         GridPane grid6= new GridPane();
						         {
						        	 new ProjectController(grid6, bean);
						         }
						         tab6.setContent(grid6);
						         
						         tabPane.getTabs().addAll(tab,tab1,tab2,tab3,tab4,tab5,tab6);
						      }
					      }
					      borderPane.setCenter(tabPane);
				      }
				      t1 = new TitledPane("Stammdatenpflege", borderPane);
				      
				      {
				    	  borderPane2 = new BorderPane();
					      {
					    	  Label label = new Label("test 12345");
					    	  borderPane2.setCenter(label);
					      }
				      }
				      t2 = new TitledPane("Übersicht", borderPane2);
				      accordion.getPanes().addAll(t1,t2);
				      accordion.setExpandedPane(t1);
			      }
			      
			      bean.getConsole().setMaxHeight(80);
			      StackPane.setAlignment(bean.getConsole(), Pos.BOTTOM_LEFT);
			      stackPane.getChildren().addAll(accordion,bean.getConsole());
		      }
	      }
	      root.getChildren().add(stackPane);
	 
	      bean.getStage().setScene(scene);
	      bean.getStage().show();
	}

}

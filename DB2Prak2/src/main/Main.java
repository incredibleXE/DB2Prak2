package main;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.GuiController;
import model.Databean;
import model.Databean.DATABASE_TYPE;

public class Main extends Application {

	/**
	 * launches the javaFX thread
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * start method for javaFX stage
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// init data bean
		Databean bean = new Databean("localhost", "PZM2_data","","",DATABASE_TYPE.MsSQL);
		bean.setWinlogon(true);
		bean.setStage(stage);
		bean.setStage_width(500);
		bean.setStage_height(500);
		// finished init
		GuiController controller = new GuiController(bean);
		controller.start();
	}

}

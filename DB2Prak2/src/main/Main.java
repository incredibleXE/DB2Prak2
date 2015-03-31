package main;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.GuiController;
import model.Databean;
public class Main extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Databean bean = new Databean("localhost", "PZM2_data","franacher","passwd");
		GuiController controller = new GuiController(bean);
		controller.start(stage);
	}

}

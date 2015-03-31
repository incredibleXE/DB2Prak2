package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CustomerController {

	public CustomerController(GridPane grid) {
		grid.add(new Label("test"), 0, 0);
		grid.add(new Label("test2"), 0, 1);
	}

}

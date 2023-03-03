package code.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button butt;
	@FXML VBox scrollPaneContainer;
	
	public void addButt(ActionEvent e) {
		scrollPaneContainer.getChildren().add(new Button("Penal"));
	}
	// Comment
}

package code.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button addClientButton;
	@FXML VBox scrollPaneVBox;
	
	public void addButt(ActionEvent e) {
		
		Button butt = new Button("BEAN \n\n\n\n BEAN");
		butt.setMaxWidth(100);
		butt.setPrefWidth(100);
		butt.setMinWidth(100);
		butt.setMaxHeight(100);
		butt.setMinHeight(100);
		butt.setPrefHeight(100);
		
		scrollPaneVBox.getChildren().add(butt);
	}
	// Comment
}

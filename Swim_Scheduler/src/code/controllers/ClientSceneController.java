package code.controllers;

import code.dataObjects.Client;
import code.services.ClientCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button addClientButton;
	@FXML VBox scrollPaneVBox;
	@FXML BorderPane mainClientScenePane;
	
	public void addButt(ActionEvent e) {
		
		scrollPaneVBox.setFillWidth(true);
		scrollPaneVBox.getChildren().add(new ClientCard(new Client("Taco Bell", "2345 Bean Burrito dr.", "123-456-7890", (short)5, "Yung G", (short)12, 125, false)));
	}
	// Comment
}

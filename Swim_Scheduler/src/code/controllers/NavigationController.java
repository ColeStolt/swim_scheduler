package code.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class NavigationController {

	@FXML BorderPane mainBorderPane;
	@FXML Button clientsButton;
	
	// Load scenes for use in navigation switching
	
	public void switchToClientScene(ActionEvent e) {
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the client page.\nEmail the developer the log file located in ...");
			sceneAlert.show();
		}
	}
	
}

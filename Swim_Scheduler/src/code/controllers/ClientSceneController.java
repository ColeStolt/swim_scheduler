package code.controllers;

import java.io.IOException;

import code.customUI.ClientCard;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button addClientButton;
	@FXML VBox scrollPaneVBox;
	@FXML BorderPane mainClientScenePane;
	@FXML TextField clientSearchBar;
	
	private ClientDataDB clientData = ClientDataDB.getInstance();
	
	// Main screen reference
	MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	
	public void initialize() {
		
		// init settings 
		scrollPaneVBox.setFillWidth(true);
		
		// Iterate through list and add client card to screen
		for(int i = 0; i < clientData.getClientDB().size(); i++) {
			scrollPaneVBox.getChildren().add(new ClientCard(clientData.getClientDB().get(i)));
		}

	}
	
	public void addButt(ActionEvent e) {
		try {
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientDataFieldsScene.fxml")));
			//mainClientScenePane.setBottom(null);
			//mainClientScenePane.setTop(null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// Update the client list as user type (VERY NEAT)
	// Remove all clients when key typed
	// Search list for client names that contain the text
	// Fill list with appropriate client cards
	public void updateClientList() {
		scrollPaneVBox.getChildren().removeAll(scrollPaneVBox.getChildren());
		for(int i = 0; i < clientData.getClientDB().size(); i++) {
			if(clientData.getClientDB().get(i).getClientName().toLowerCase().contains(clientSearchBar.getText().toLowerCase())) {
				scrollPaneVBox.getChildren().add(new ClientCard(clientData.getClientDB().get(i)));
			}
		}
	}
}

package code.controllers;

import code.datapersistance_dao.ClientDataDB;
import code.services.ClientCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button addClientButton;
	@FXML VBox scrollPaneVBox;
	@FXML BorderPane mainClientScenePane;
	@FXML TextField clientSearchBar;
	
	private ClientDataDB clientData;
	
	public void initialize() {
		clientData = new ClientDataDB("src\\data\\client.data");
		
		// init settings 
		scrollPaneVBox.setFillWidth(true);
		
		// Iterate through list and add client card to screen
		for(int i = 0; i < clientData.getClientDB().size(); i++) {
			scrollPaneVBox.getChildren().add(new ClientCard(clientData.getClientDB().get(i)));
		}

	}
	
	public void addButt(ActionEvent e) {
		
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

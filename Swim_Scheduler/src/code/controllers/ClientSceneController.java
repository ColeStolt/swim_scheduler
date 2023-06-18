package code.controllers;

import java.io.IOException;

import code.customUI.ClientCard;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientSceneController {

	@FXML Button addClientButton;
	@FXML VBox scrollPaneVBox;
	@FXML BorderPane mainClientScenePane;
	@FXML TextField clientSearchBar;
	
	private ClientDataDB clientData = ClientDataDB.getInstance();
	
	// Main screen reference
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();
	
	public void initialize() {
		
		// init settings 
		scrollPaneVBox.setFillWidth(true);
		
		// Iterate through list and add client card to screen
		for(int i = 0; i < clientData.getClientDB().size(); i++) {
			
			ClientCard card = new ClientCard(clientData.getClientDB().get(i));
			
			card.setOnMouseClicked((new EventHandler<MouseEvent>() { 
				   public void handle(MouseEvent event) { 
				      try {
				    	  clientTempReference.setClientReference(card.getClientReference());
				    	 
						mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientDataFieldsScene.fxml")));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   } 
				}));
			
			scrollPaneVBox.getChildren().add(card);
		}

	}
	
	public void addButt(ActionEvent e) {
		try {
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientDataFieldsScene.fxml")));
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
				
				// Adding handler back to new cards
				ClientCard card = new ClientCard(clientData.getClientDB().get(i));
				
				card.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					   public void handle(MouseEvent event) { 
					      try {
					    	  clientTempReference.setClientReference(card.getClientReference());
					    	 
							mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientDataFieldsScene.fxml")));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					   } 
					}));
				
				scrollPaneVBox.getChildren().add(card);
			}
		}
	}
}

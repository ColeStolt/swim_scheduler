package code.controllers;

import java.io.IOException;

import code.customUI.ClientCard;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ScheduleStatusController {

	@FXML VBox unscheduledVBox;
	@FXML VBox scheduledVBox;
	
	// Databases
	ClientDataDB clientsDB = ClientDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();
	
	public void initialize() {
		populateSchedules();
	}
	
	public void populateSchedules() {
		for(int i = 0; i < clientsDB.getClientDB().size(); i++) {
			if(clientsDB.getClientDB().get(i).isScheduled()) {
				ClientCard scheduled = new ClientCard(clientsDB.getClientDB().get(i));
				scheduled.setId("mainCardPane");
				scheduledVBox.getChildren().add(scheduled);
			} else {
				ClientCard unscheduled = new ClientCard(clientsDB.getClientDB().get(i));
				unscheduled.setId("mainCardPane");
				
				unscheduled.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					   public void handle(MouseEvent event) { 
						      try {
						    	  clientTempReference.setClientReference(unscheduled.getClientReference());
						    	 
								mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduleScene.fxml")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						   }

						}));
				
				unscheduledVBox.getChildren().add(unscheduled);
			}
		}
	}
	
}

package code.controllers;

import code.customUI.ClientCard;
import code.datapersistance_dao.ClientDataDB;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ScheduleStatusController {

	@FXML VBox unscheduledVBox;
	@FXML VBox scheduledVBox;
	
	// Databases
	ClientDataDB clientsDB = ClientDataDB.getInstance();
	
	public void initialize() {
		
	}
	
	public void populateSchedules() {
		for(int i = 0; i < clientsDB.getClientDB().size(); i++) {
			if(clientsDB.getClientDB().get(i).isScheduled()) {
				scheduledVBox.getChildren().add(new ClientCard(clientsDB.getClientDB().get(i)));
			} else {
				unscheduledVBox.getChildren().add(new ClientCard(clientsDB.getClientDB().get(i)));
			}
		}
	}
	
}

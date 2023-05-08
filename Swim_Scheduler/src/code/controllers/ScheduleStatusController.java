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
		populateSchedules();
	}
	
	public void populateSchedules() {
		for(int i = 0; i < clientsDB.getClientDB().size(); i++) {
			if(clientsDB.getClientDB().get(i).isScheduled()) {
				ClientCard scheduled = new ClientCard(clientsDB.getClientDB().get(i));
				scheduled.setId("mainCardPane");
				scheduledVBox.getChildren().add(scheduled);
			} else {
				ClientCard scheduled = new ClientCard(clientsDB.getClientDB().get(i));
				scheduled.setId("mainCardPane");
				unscheduledVBox.getChildren().add(scheduled);
			}
		}
	}
	
}

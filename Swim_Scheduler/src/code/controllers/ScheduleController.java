package code.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import code.customUI.MaskedTextField;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ScheduleController {


	@FXML Button sundayButton;
	@FXML Button mondayButton;
	@FXML Button tuesdayButton;
	@FXML Button wednesdayButton;
	@FXML Button thursdayButton;
	@FXML Button fridayButton;
	@FXML Button saturdayButton;
	@FXML BorderPane timeBorderpane;
	@FXML DatePicker startDatePicker;
	@FXML ComboBox<String> morningNoonComboBox;
	@FXML Label clientName;
	
	MaskedTextField timeField;
	
	// Singletons
	private ClientDataDB clientDB = ClientDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();
	
	public void initialize() {
		
		timeOfLessonsSetup();
		comboBoxSetup();
		clientName.setText(clientTempReference.getClient().getClientName());
		startDatePicker.setValue(LocalDate.now());
	}
	
	void timeOfLessonsSetup() {
		timeField = new MaskedTextField("##:##", '-');
		timeField.setPrefHeight(67);
		timeField.setPrefWidth(158);
		timeField.setId("timeField");
		timeBorderpane.setCenter(timeField);
	}

	
	public void sendAndSaveSchedule() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.US);
		String formattedValue = (startDatePicker.getValue()).format(formatter);
		System.out.println(formattedValue);
	}
	
	public void comboBoxSetup() {
		morningNoonComboBox.getItems().add("AM");
		morningNoonComboBox.getItems().add("PM");
		morningNoonComboBox.setValue("AM");
	}
	
	public void cancelSchedule() {
		try {
			clientTempReference.setClientReference(null);
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduledAndUnscheduledScene.fxml")));
			//mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

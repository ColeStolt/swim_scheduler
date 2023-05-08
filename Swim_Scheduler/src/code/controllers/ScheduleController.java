package code.controllers;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import code.customUI.MaskedTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
	
	MaskedTextField timeField;
	
	public void initialize() {
		
		timeOfLessonsSetup();
		comboBoxSetup();
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
	
}

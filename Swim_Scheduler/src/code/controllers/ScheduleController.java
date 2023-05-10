package code.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import code.customUI.MaskedTextField;
import code.datapersistance_dao.AuthCalendarSingleton;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import code.services.TextService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ScheduleController {

	@FXML
	Button sundayButton;
	@FXML
	Button mondayButton;
	@FXML
	Button tuesdayButton;
	@FXML
	Button wednesdayButton;
	@FXML
	Button thursdayButton;
	@FXML
	Button fridayButton;
	@FXML
	Button saturdayButton;
	@FXML
	BorderPane timeBorderpane;
	@FXML
	DatePicker startDatePicker;
	@FXML
	ComboBox<String> morningNoonComboBox;
	@FXML
	Label clientName;

	Map<String, Boolean> dayMap = new HashMap<String, Boolean>();

	MaskedTextField timeField;

	// Singletons
	private AuthCalendarSingleton instance = AuthCalendarSingleton.getInstance();
	private ClientDataDB clientDB = ClientDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();

	public void initialize() {

		timeOfLessonsSetup();
		comboBoxSetup();
		populateMap();

		// Sets name label at top of screen
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

	public void sendAndSaveSchedule() throws IOException {

		// Check for empty fields
		for (Entry<String, Boolean> pair : dayMap.entrySet()) {
		    if(pair.getValue() == true) {
		    	break;
		    } else {
		    	Alert daySelection = new Alert(AlertType.ERROR);
		    	daySelection.setContentText("No days are selected. Please select at least one day.");
		    	daySelection.showAndWait();
		    	return;
		    }
		}
		
		// Check for incorrect time field
		// Check for empty fields

		    if(timeField.getText().contains("-")) {
		    	Alert daySelection = new Alert(AlertType.ERROR);
		    	daySelection.setContentText("Start time is either empty or not filled in. Start time cannot contain \'-\'.");
		    	daySelection.showAndWait();
		    	return;
		    }
		
		
		// Show alert to confirm settings
		Alert proceed = new Alert(AlertType.CONFIRMATION);
		proceed.setContentText(
				"You are about to schedule a client.\nThis will send them a text and an email about their schedule.\nAre you sure you want to do this?");
		proceed.showAndWait();
		
		
		if (proceed.getResult() == ButtonType.CANCEL) {
			return;
		} else {

			// Text client about start date
			TextService.sendText(clientTempReference.getClient().getPhoneNumber(), clientTempReference.getClient()
					.getClientName()
					+ ",\nWe're so excited to have you join us at Rockwall Private Swimming Lessons!\n\nYour lessons start on "
					+ startDatePicker.getValue() + " at " + LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n\n\nSee you then!");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
			String formattedValue = (startDatePicker.getValue()).format(formatter);
			System.out.println(formattedValue);

			// Event creation
			Event event = new Event().setSummary(clientTempReference.getClient().getClientName() + " Swim Lessons")
					.setLocation(clientTempReference.getClient().getAddressOfLessons())
					.setDescription(clientTempReference.getClient().getNumberOfKids() + " kids");

			// Event end and start time
			DateTime startDateTime = new DateTime(formattedValue + "T" + timeField.getText() + ":00-05:00");
			EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("US/Central");
			event.setStart(start);

			DateTime endDateTime = new DateTime(formattedValue + "T" + timeField.getText() + ":00-05:00");
			EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("US/Central");
			event.setEnd(end);
			
			String tempString = "";
			
			// Create a string that contains the days of the week
			for (Entry<String, Boolean> pair : dayMap.entrySet()) {
			    if(pair.getValue() == true) {
			    	tempString += pair.getKey() + ",";
			    }
			}
			
			tempString = tempString.substring(0, tempString.length() - 1);
			// End string creation
			
			String[] recurrence = new String[] { "RRULE:FREQ=WEEKLY;BYDAY="+tempString+";COUNT="
					+ clientTempReference.getClient().getNumberOfLessons() };
			event.setRecurrence(Arrays.asList(recurrence));

			System.out.println("RRULE:FREQ=WEEKLY;BYDAY=MO,TU,TH,FR;COUNT="
					+ clientTempReference.getClient().getNumberOfLessons());

			String calendarId = "primary";
			event = instance.getCalendarReference().events().insert(calendarId, event).execute();
			
			findAndUpdateClient();
			
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduledAndUnscheduledScene.fxml")));
		}
	}

	public void findAndUpdateClient() {
		for(int i = 0; i < clientDB.getClientDB().size(); i++) {
			if(clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
				clientDB.getClientDB().get(i).setScheduled(true);
				clientDB.saveData();
			}
		}
	}
	
	public void populateMap() {
		dayMap.put("SU", false);
		dayMap.put("MO", false);
		dayMap.put("TU", false);
		dayMap.put("WE", false);
		dayMap.put("TH", false);
		dayMap.put("FR", false);
		dayMap.put("SA", false);
	}

	public void comboBoxSetup() {
		morningNoonComboBox.getItems().add("AM");
		morningNoonComboBox.getItems().add("PM");
		morningNoonComboBox.setValue("AM");
	}

	public void cancelSchedule() {
		try {
			clientTempReference.setClientReference(null);
			mainScreen.getPane().setCenter(
					FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduledAndUnscheduledScene.fxml")));
			// mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Button presses --------------------------------------------------------
	public void setSunday() {
		if (!dayMap.get("SU")) {
			sundayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("SU", true);
		} else {
			sundayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("SU", false);
		}
	}

	public void setMonday() {
		if (!dayMap.get("MO")) {
			mondayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("MO", true);
		} else {
			mondayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("MO", false);
		}
	}

	public void setTuesday() {
		if (!dayMap.get("TU")) {
			tuesdayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("TU", true);
		} else {
			tuesdayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("TU", false);
		}
	}

	public void setWednesday() {
		if (!dayMap.get("WE")) {
			wednesdayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("WE", true);
		} else {
			wednesdayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("WE", false);
		}
	}

	public void setThursday() {
		if (!dayMap.get("TH")) {
			thursdayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("TH", true);
		} else {
			thursdayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("TH", false);
		}
	}

	public void setFriday() {
		if (!dayMap.get("FR")) {
			fridayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("FR", true);
		} else {
			fridayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("FR", false);
		}
	}

	public void setSaturday() {
		if (!dayMap.get("SA")) {
			saturdayButton.setStyle("-fx-background-color: #42505c");
			dayMap.replace("SA", true);
		} else {
			saturdayButton.setStyle("-fx-background-color: #738a9e");
			dayMap.replace("SA", false);
		}
	}

}

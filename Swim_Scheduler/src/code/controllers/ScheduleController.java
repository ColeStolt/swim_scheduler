package code.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
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
import javafx.scene.control.CheckBox;
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
	@FXML
	CheckBox notifyCheck;

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

		boolean daySelected = false;

		// Check for empty fields
		for (Entry<String, Boolean> pair : dayMap.entrySet()) {
			if (pair.getValue() == true) {
				daySelected = true;
				break;
			}
		}

		if (daySelected == false) {

			Alert daySelection = new Alert(AlertType.ERROR);
			daySelection.setContentText("No days are selected. Please select at least one day.");
			daySelection.showAndWait();

			return;
		}

		// Check for incorrect time field
		// Check for empty fields

		if (timeField.getText().contains("-")) {
			Alert daySelection = new Alert(AlertType.ERROR);
			daySelection
					.setContentText("Start time is either empty or not filled in. Start time cannot contain \'-\'.");
			daySelection.showAndWait();
			return;
		}

		// This parses the time to make it a 24 format
		if (parseTime()) {

		} else {
			return;
		}

		// Create alert
		Alert proceed = new Alert(AlertType.CONFIRMATION);

		// Show alert to confirm settings
		if (notifyCheck.isSelected()) {
			proceed.setContentText(
					"You are about to schedule a client.\nThis will send them a text and an email about their schedule.\nAre you sure you want to do this?");
			proceed.showAndWait();
		} else {
			proceed.setResult(ButtonType.YES);
		}

		if (proceed.getResult() == ButtonType.CANCEL) {
			return;
		} else {

			// Check one more time
			if (notifyCheck.isSelected()) {
				// Text client about start date
				DateTimeFormatter readableFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
				TextService.sendText(clientTempReference.getClient().getPhoneNumber(), clientTempReference.getClient()
						.getClientName()
						+ ",\nWe're so excited to have you join us at Rockwall Private Swimming Lessons!\n\nYour lessons start on "
						+ (startDatePicker.getValue()).format(readableFormat) + " at "
						+ LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm"))
								.format(DateTimeFormatter.ofPattern("hh:mm a"))
						+ "\n\n\nSee you then!");
				
				// Split and check time
				String tokes[] = timeField.getText().split(":");

				if(Integer.parseInt(tokes[0]) > 12) {
					tokes[0] = (Integer.parseInt(tokes[0]) - 12) + ":" + tokes[1]; 
				}				
				
				// Text all instructors about start date
				String kidList = createKidListString();
				for (int i = 0; i < clientTempReference.getClient().getInstructor().size(); i++) {
					
					TextService.sendText(
							clientTempReference.getClient().getInstructor().get(i).getInstructorPhoneNumber(),
							clientTempReference.getClient().getClientName() + "\n\n" + "Start date: "
									+ (startDatePicker.getValue()).format(readableFormat) + "\nStart Time: "
									+ tokes[0] + ":" + tokes[1] + " "
									+ morningNoonComboBox.getSelectionModel().getSelectedItem() + "\n\n" + "They have "
									+ clientTempReference.getClient().getNumberOfLessons() + " lessons at "
									+ clientTempReference.getClient().getAddressOfLessons() + "\n\n" 
									+ kidList);
				}
			}

			// Format date for google ease
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
			String formattedValue = (startDatePicker.getValue()).format(formatter);

			// Event creation
			String kidList = createKidListString();
			Event event = new Event().setSummary(clientTempReference.getClient().getClientName() + " Swim Lessons")
					.setLocation(clientTempReference.getClient().getAddressOfLessons())
					.setDescription(kidList);

			// Check again ------------------------------
			if (notifyCheck.isSelected()) {
				// Add instructor as an attendee
				ArrayList<EventAttendee> instructors = new ArrayList<EventAttendee>();
				for (int i = 0; i < clientTempReference.getClient().getInstructor().size(); i++) {

					if (clientTempReference.getClient().getInstructor().get(i).getInstructorEmail().contains("@")
							&& clientTempReference.getClient().getInstructor().get(i).getInstructorEmail().contains(".com")) {
					
						instructors.add(new EventAttendee()
								.setEmail(clientTempReference.getClient().getInstructor().get(i).getInstructorEmail()));
					
					} else {
						System.out.println("Not working");
					}
				}

				if (instructors.size() != 0) {
					event.setAttendees(instructors);
				}
			}

			// Event end and start time
			DateTime startDateTime = new DateTime(formattedValue + "T" + timeField.getText() + ":00-05:00");
			EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("US/Central");
			event.setStart(start);

			// Configure end time
			if (clientTempReference.getClient().getNumberOfKids() < 2 || clientTempReference.getClient().getInstructor()
					.size() > clientTempReference.getClient().getNumberOfKids()) {

				String tokens[] = timeField.getText().split(":");

				int hour = Integer.parseInt(tokens[0]);
				int minute = Integer.parseInt(tokens[1]);

				if ((minute + 30) > 59) {
					hour = hour + 1;
					minute = 30 - (60 - minute);

					if (minute < 10) {
						tokens[1] = "0" + Integer.toString(minute);
					} else {
						tokens[1] = Integer.toString(minute);
					}

					tokens[0] = Integer.toString(hour);

					timeField.setPlainText(tokens[0] + ":" + tokens[1]);
				} else {
					minute = minute + 30;
					tokens[1] = minute + "";
					timeField.setPlainText(tokens[0] + ":" + tokens[1]);
				}

			} else {

				LocalTime startTime = LocalTime.parse(timeField.getText());

				// Step 3: Calculate the total duration in minutes
				int kidTime = clientTempReference.getClient().getNumberOfKids() * 30;

				// Step 4: Calculate the ending time
				int instructorTime = clientTempReference.getClient().getInstructor().size() * 30;
				int kidsForEachInstructor = kidTime / instructorTime;
				int remainingMinutes = kidTime % instructorTime;
				int totalTime = kidsForEachInstructor * 30;

				if (remainingMinutes > 0) {
					totalTime = totalTime + 30;
				}

				LocalTime endTime;

				if (clientTempReference.getClient().getInstructor().size() == 1) {
					endTime = startTime.plusMinutes(kidTime);
				} else {
					endTime = startTime.plusMinutes(totalTime);
				}

				timeField.setPlainText(endTime.toString());

			}

			DateTime endDateTime = new DateTime(formattedValue + "T" + timeField.getText() + ":00-05:00");
			EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("US/Central");
			event.setEnd(end);

			String tempString = "";

			// Create a string that contains the days of the week
			for (Entry<String, Boolean> pair : dayMap.entrySet()) {
				if (pair.getValue() == true) {
					tempString += pair.getKey() + ",";
				}
			}

			tempString = tempString.substring(0, tempString.length() - 1);
			// End string creation

			String[] recurrence = new String[] { "RRULE:FREQ=WEEKLY;BYDAY=" + tempString + ";COUNT="
					+ clientTempReference.getClient().getNumberOfLessons() };
			event.setRecurrence(Arrays.asList(recurrence));

			String calendarId = "primary";
		
			event = instance.getCalendarReference().events().insert(calendarId, event).execute();
			
			// Add the event to each instructors calendar
			for (int i = 0; i < clientTempReference.getClient().getInstructor().size(); i++) {

				event = instance.getCalendarReference().events()
						.insert(clientTempReference.getClient().getInstructor().get(i).getCalendarID(), event)
						.execute();

			}

			findAndUpdateClient();

			mainScreen.getPane().setCenter(
					FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduledAndUnscheduledScene.fxml")));
		}
	}
	
	private String createKidListString() {
		String kidList = "";
		
		for(int i = 0; i < clientTempReference.getClient().getKids().size(); i++) {
			kidList =  kidList + "\n" + "Kid " + (i + 1) + ": " + clientTempReference.getClient().getKids().get(i).getKidInformation() + "\n";
		}
		
		return kidList;
	}

	private boolean parseTime() {
		String tokens[] = timeField.getText().split(":");
		// Check left side of time
		if (Integer.parseInt(tokens[0]) > 12) {
			Alert proceed = new Alert(AlertType.ERROR);
			proceed.setContentText("Hour must be between 01 and 12");
			proceed.showAndWait();
			return false;
		}
		// Check right side of time
		if (Integer.parseInt(tokens[1]) > 59) {
			Alert proceed = new Alert(AlertType.ERROR);
			proceed.setContentText("Minute must be between 00 and 59");
			proceed.showAndWait();
			return false;
		}

		// Parse time to 24 hour if equal to PM
		if (morningNoonComboBox.getSelectionModel().getSelectedItem().equals("PM")
				&& Integer.parseInt(tokens[0]) < 12) {

			// Convert hour
			int hour = Integer.parseInt(tokens[0]) + 12;
			tokens[0] = Integer.toString(hour);

			timeField.setPlainText(tokens[0] + ":" + tokens[1]);

		}

		return true;
	}

	public void findAndUpdateClient() {
		for (int i = 0; i < clientDB.getClientDB().size(); i++) {
			if (clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
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

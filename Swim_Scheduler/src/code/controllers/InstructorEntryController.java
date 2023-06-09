package code.controllers;

import java.io.IOException;

import code.customUI.MaskedTextField;
import code.dataObjects.Instructor;
import code.datapersistance_dao.AuthCalendarSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.InstructorCardReferenceSingleton;
import code.datapersistance_dao.InstructorDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class InstructorEntryController {

	private InstructorDataDB instructorDB = InstructorDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private InstructorCardReferenceSingleton tempInstructor = InstructorCardReferenceSingleton.getInstance();
	private ClientDataDB clientDB = ClientDataDB.getInstance();
	private AuthCalendarSingleton service = AuthCalendarSingleton.getInstance();

	@FXML
	TextField nameField;
	@FXML
	TextField emailField;
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	@FXML
	HBox buttonHBox;
	@FXML
	BorderPane namePane;
	@FXML
	BorderPane emailPane;
	@FXML
	BorderPane phonePane;

	private MaskedTextField phoneField;

	// Labels
	private Label nameLabel;
	private Label emailLabel;
	private Label phoneLabel;

	public void initialize() {
		setup();

		if (tempInstructor.getInstructor() != null) {
			populateEntries();
			enableDeleteButton();
		} else {

		}
	}

	public void setup() {
		// Field setup
		phoneField = new MaskedTextField("(###) ###-####");
		phoneField.setId("phoneField");
		phoneField.setPrefWidth(689);
		phoneField.setPrefHeight(79);
		phoneField.setMinHeight(0);
		phoneField.setMinWidth(0);

		phonePane.setCenter(phoneField);

		// Creating labels
		nameLabel = new Label("Instructor Name:");
		nameLabel.setId("labels");
		emailLabel = new Label("Instructor Email:");
		emailLabel.setId("labels");
		phoneLabel = new Label("Instructor Phone Number:");
		phoneLabel.setId("labels");

		// Pane settings
		BorderPane.setAlignment(phoneLabel, Pos.CENTER_LEFT);
		BorderPane.setAlignment(nameLabel, Pos.CENTER_LEFT);
		BorderPane.setAlignment(emailLabel, Pos.CENTER_LEFT);

		// Adding to pane
		namePane.setTop(nameLabel);
		emailPane.setTop(emailLabel);
		phonePane.setTop(phoneLabel);

	}

	public void cancelAdd() {
		try {
			tempInstructor.setInstructorReference(null);
			mainScreen.getPane()
					.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/InstructorsScene.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addInstructor() {

		if (tempInstructor.getInstructor() == null) {

			Alert alert = new Alert(AlertType.ERROR);
			
			if(nameField.getText() == "" || nameField.getText() == null) {
				
				alert.setContentText("Instructor name cannot be left blank.");
				alert.showAndWait();
				
				return;
			}
			
			if(emailField.getText() == "" || emailField.getText() == null) {
				
				alert.setContentText("Email cannot be left blank.");
				alert.showAndWait();
				
				return;
			}
			
			
			if(phoneField.getText().contains("_")) {
				
				alert.setContentText("Improper phone number entered.");
				alert.showAndWait();
				
				return;
			}
			
			// Create instructor
			Instructor newInstructor = new Instructor(nameField.getText(), phoneField.getText(), emailField.getText());

			// Create calendar
			com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
			calendar.setSummary(nameField.getText());
			calendar.setTimeZone("America/Los_Angeles");

			// Insert the new calendar
			try {
				com.google.api.services.calendar.model.Calendar createdCalendar = service.getCalendarReference()
						.calendars().insert(calendar).execute();
				newInstructor.setCalendarID(createdCalendar.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			instructorDB.getInstructorDB().add(newInstructor);

			instructorDB.saveData();
		} else {
			findAndReplaceInstructor();
			tempInstructor.setInstructorReference(null);
			instructorDB.saveData();
		}

		try {
			mainScreen.getPane()
					.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/InstructorsScene.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateEntries() {
		nameField.setText(tempInstructor.getInstructor().getInstructorName());
		emailField.setText(tempInstructor.getInstructor().getInstructorEmail());
		phoneField.setPlainText(tempInstructor.getInstructor().getInstructorPhoneNumber());
	}

	private void enableDeleteButton() {
		// Create button
		Button deleteButton = new Button("Delete");

		// Button settings
		deleteButton.setId("deleteButton");
		deleteButton.setPrefHeight(52);
		deleteButton.setPrefWidth(353);
		deleteButton.setMinHeight(0);
		deleteButton.setMinWidth(0);

		// Delete button handler
		deleteButton.setOnAction((new EventHandler<ActionEvent>() {

			// Temp instructor name in case of null client object
			String tempName = tempInstructor.getInstructor().getInstructorName();

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					deleteInstructor();
					instructorDB.saveData();
					Alert sceneAlert = new Alert(AlertType.INFORMATION);
					sceneAlert.setContentText("Instructor " + tempName + " has been successfully deleted.");
					sceneAlert.show();
					mainScreen.getPane().setCenter(
							FXMLLoader.load(getClass().getResource("/resources/scenes/InstructorsScene.fxml")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));

		// Add button to hbox
		buttonHBox.getChildren().add(deleteButton);

	}

	private void deleteInstructor() {
		for (int i = 0; i < instructorDB.getInstructorDB().size(); i++) {
			if (tempInstructor.getInstructor().getInstructorID() == instructorDB.getInstructorDB().get(i)
					.getInstructorID()) {

				for (int j = 0; j < clientDB.getClientDB().size(); j++) {
					for (int k = 0; k < clientDB.getClientDB().get(j).getInstructor().size(); k++) {
						if (clientDB.getClientDB().get(j).getInstructor().get(k).getInstructorID() == instructorDB
								.getInstructorDB().get(i).getInstructorID()) {
							clientDB.getClientDB().get(j).getInstructor().remove(k);
						}
					}
				}

				clientDB.saveData();
				
				try {
					service.getCalendarReference().calendars().delete(instructorDB.getInstructorDB().get(i).getCalendarID()).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				instructorDB.getInstructorDB().remove(i);

				break;
			}
		}

		// Set client to null for next iteration
		tempInstructor.setInstructorReference(null);
	}

	private void findAndReplaceInstructor() {
		for (int i = 0; i < instructorDB.getInstructorDB().size(); i++) {
			if (tempInstructor.getInstructor().getInstructorID() == instructorDB.getInstructorDB().get(i)
					.getInstructorID()) {
				instructorDB.getInstructorDB().get(i).setInstructorName(nameField.getText());
				instructorDB.getInstructorDB().get(i).setInstructorEmail(emailField.getText());
				instructorDB.getInstructorDB().get(i).setInstructorPhoneNumber(phoneField.getText());
			}
		}
	}

}

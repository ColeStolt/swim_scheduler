package code.controllers;

import java.io.IOException;
import java.util.ArrayList;

import code.customUI.MaskedTextField;
import code.dataObjects.Client;
import code.dataObjects.Instructor;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.InstructorDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Cole Stoltz
 * Probably the dirtiest way to switch scenes.
 * It works but will keep holding onto to border panes
 * which is a memory leak. Goes away when user switches
 * scenes in the navigation though.
 *
 */


public class ClientEntryController {

	private ClientDataDB clientDB = ClientDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();
	private InstructorDataDB instructorDB = InstructorDataDB.getInstance();
	
	@FXML private BorderPane mainScenePane;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;
	@FXML private VBox clientInfoEntriesVBox;
	@FXML private VBox clientLessonInfoEntryVBox;
	@FXML private HBox buttonHBox;
	
	// Fields
	private TextField nameField;
	private MaskedTextField phoneNumberField;
	private TextField addressField;
	private MaskedTextField kidsField;
	private ChoiceBox<Instructor> instructorChoice;
	private MaskedTextField numberOfLessonsField;
	private MaskedTextField amountPerLessonField;
	private RadioButton paidInFullRadio;
	
	// List of comboBoxes for getting all instructors
	private ArrayList<ChoiceBox<Instructor>> instructorChoiceBoxes; 
	
	// Panes
	private HBox radioAndLabel;
	private BorderPane titlePane;
	private HBox instructorsAdditionHBox;
	
	// Labels
	private Label nameLabel;
	private Label phoneNumberLabel;
	private Label addressLabel;
	private Label kidsLabel;
	private Label instructorLabel;
	private Label numberOfLessonsLabel;
	private Label amountPerLessonLabel;
	private Label paidInFullLabel;
	private Label clientInfoTitle;
	private Label lessonInfoTitle;
	
	// Counter for max instructors
	private int instructorMax = 0;
	
	// Buttons
	private Button addInstructorButton;
	private Button removeInstructorButton;
	
	public void initialize() {
		instructorChoiceBoxes = new ArrayList<ChoiceBox<Instructor>>(); // This first so it can be edited
		
		instructorInfoSetup();
		lessonInfoSetup();
		if(clientTempReference.getClient() != null) {
			populateEntries();
			enableDeleteButton();
		}  else {
			
		}
	}
	
	public void cancelClientAdd() {
		try {
			clientTempReference.setClientReference(null);
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
			//mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveClient() {
		
		if(instructorDB.getInstructorDB().size() > 0) {
			// Populate an instructor arraylist to add to a client ----
			ArrayList<Instructor> tempList = new ArrayList<Instructor>();
			for(int i = 0; i < instructorChoiceBoxes.size(); i++) {
				tempList.add(instructorChoiceBoxes.get(i).getValue());
			}
			// Garbage collection should be able to handle this
			// -------
		
			if(clientTempReference.getClient() == null) {
				clientDB.getClientDB().add(new Client(nameField.getText(), addressField.getText(), phoneNumberField.getText(), Short.parseShort(parseData(kidsField.getText())), tempList, Short.parseShort(parseData(numberOfLessonsField.getText())), Float.parseFloat(parseData(amountPerLessonField.getText())), paidInFullRadio.isSelected()));
				clientDB.saveData();
			} else {
				findAndReplaceClient();
				clientTempReference.setClientReference(null);
				clientDB.saveData();
			}
		
			try {
				mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
				//mainScenePane.setBottom(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			Alert sceneAlert = new Alert(AlertType.INFORMATION);
			sceneAlert.setContentText("Need to have at least one instructor assigned.");
			sceneAlert.show();
		}
		
	}
	
	public String parseData(String data) {
		
		data = data.replace("-", "0");
		data = data.replace("$", "");
		return data;
	}
	
	public void populateChoiceBox(ChoiceBox<Instructor> choiceBox) {
		
		ObservableList<Instructor> items = FXCollections.observableArrayList(instructorDB.getInstructorDB());
		choiceBox.setItems(items);

	}
	
	public void instructorInfoSetup() {
		
		// Declare fields
		nameField = new TextField();
		phoneNumberField = new MaskedTextField("(###) ###-####");
		addressField = new TextField();
		kidsField = new MaskedTextField("#", '-');
		
		// Declare panes
		titlePane = new BorderPane();
		
		// Declare Labels
		nameLabel = new Label("Client Name:");
		phoneNumberLabel = new Label("Phone Number:");
		addressLabel = new Label("Address Of Lessons:");
		kidsLabel = new Label("Number Of Kids:");
		clientInfoTitle = new Label("Client Info");
		
		// Label Settings
		phoneNumberLabel.setId("labels");
		nameLabel.setId("labels");
		addressLabel.setId("labels");
		kidsLabel.setId("labels");
		clientInfoTitle.setId("clientInfoTitle");
		
		// Field Settings
		nameField.setId("fields");
		phoneNumberField.setId("phoneNumberField");
		addressField.setId("fields");
		kidsField.setId("fields");
		
		// Pane settings
		titlePane.setId("titlePane");
		
		// Add to panes
		titlePane.setCenter(clientInfoTitle);
		
		clientInfoEntriesVBox.getChildren().add(titlePane);
		clientInfoEntriesVBox.getChildren().add(nameLabel);
		clientInfoEntriesVBox.getChildren().add(nameField);
		clientInfoEntriesVBox.getChildren().add(phoneNumberLabel);
		clientInfoEntriesVBox.getChildren().add(phoneNumberField);
		clientInfoEntriesVBox.getChildren().add(addressLabel);
		clientInfoEntriesVBox.getChildren().add(addressField);
		clientInfoEntriesVBox.getChildren().add(kidsLabel);
		clientInfoEntriesVBox.getChildren().add(kidsField);
	}
	
	public void lessonInfoSetup() {
		// Declare Fields
		instructorChoice = new ChoiceBox<Instructor>();
		numberOfLessonsField = new MaskedTextField("##", '-');
		amountPerLessonField = new MaskedTextField("$###.00", '-');
		paidInFullRadio = new RadioButton();
		addInstructorButton = new Button("+");
		removeInstructorButton = new Button("-");
		
		// Declare Labels
		instructorLabel = new Label("Instructor:");
		numberOfLessonsLabel = new Label("Number Of Lessons (Per Kid):");
		amountPerLessonLabel = new Label("Amount Per Lesson: ");
		paidInFullLabel = new Label("Paid In Full:");
		lessonInfoTitle = new Label("Lesson Info");
		
		// Label Settings
		instructorLabel.setId("labels");
		numberOfLessonsLabel.setId("labels");
		amountPerLessonLabel.setId("labels");
		paidInFullLabel.setId("labels");
		lessonInfoTitle.setId("lessonInfoTitle");
		
		// Choicebox settings
		instructorChoice.setId("choiceBox");
		populateChoiceBox(instructorChoice);
		instructorChoiceBoxes.add(instructorChoice);
		instructorChoice.getSelectionModel().selectFirst();
		
		// Field settings
		numberOfLessonsField.setId("fields");
		amountPerLessonField.setId("fields");
		
		// Radio settings
		paidInFullRadio.setId("radioButton");
		
		// Button Settings
		addInstructorButton.setId("addButton");
		removeInstructorButton.setId("addButton"); // same css id for same style
		
		// Declare Panes
		radioAndLabel = new HBox();
		instructorsAdditionHBox = new HBox();
		titlePane = new BorderPane();
		
		// Pane settings
		titlePane.setId("titlePane");
		addInstructorButton(addInstructorButton, instructorsAdditionHBox);
		removeInstructorButton(removeInstructorButton, instructorsAdditionHBox);
		
		// Add to panes
		radioAndLabel.getChildren().add(paidInFullLabel);
		radioAndLabel.getChildren().add(paidInFullRadio);
		titlePane.setCenter(lessonInfoTitle);
		instructorsAdditionHBox.getChildren().add(instructorChoice);
		instructorsAdditionHBox.getChildren().add(addInstructorButton);
		instructorsAdditionHBox.getChildren().add(removeInstructorButton);
		
		// Add to vbox
		clientLessonInfoEntryVBox.getChildren().add(titlePane);
		clientLessonInfoEntryVBox.getChildren().add(instructorLabel);
		clientLessonInfoEntryVBox.getChildren().add(instructorsAdditionHBox);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsLabel);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsField);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonLabel);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonField);
		clientLessonInfoEntryVBox.getChildren().add(radioAndLabel);
	}
	
	
	// Fill the fields with data from the client object
	// The client object comes from the client card 
	// which is seen on the screen
	private void populateEntries(){
		
		// Get size of instructor array
		// Create choice box for each
		// Set appropriate value
		if(clientTempReference.getClient().getInstructor().size() > 1)	{
			for(int i = 1; i < clientTempReference.getClient().getInstructor().size(); i++) {
				ChoiceBox<Instructor> instructorChoice = new ChoiceBox<Instructor>();
				instructorChoice.setId("choiceBox");
				populateChoiceBox(instructorChoice);
				instructorChoice.setValue(clientTempReference.getClient().getInstructor().get(i));
				instructorChoiceBoxes.add(instructorChoice);
				instructorsAdditionHBox.getChildren().add(instructorsAdditionHBox.getChildren().size()-2, instructorChoice);
				instructorMax++;
			}
		}
		
		
		nameField.setText(clientTempReference.getClient().getClientName());
		phoneNumberField.setPlainText(clientTempReference.getClient().getPhoneNumber());
		addressField.setText(clientTempReference.getClient().getAddressOfLessons());
		kidsField.setPlainText(String.valueOf(clientTempReference.getClient().getNumberOfKids()));
		numberOfLessonsField.setPlainText(String.valueOf(clientTempReference.getClient().getNumberOfLessons()));
		amountPerLessonField.setPlainText(String.valueOf(clientTempReference.getClient().getAmountPerLesson()));
		paidInFullRadio.setSelected(clientTempReference.getClient().isPaidInFull());
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
			
			// Temp client name in case of null client object
			String tempName = clientTempReference.getClient().getClientName();
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					deleteClient();
					clientDB.saveData();
					Alert sceneAlert = new Alert(AlertType.INFORMATION);
					sceneAlert.setContentText("Client " + tempName + " has been successfully deleted.");
					sceneAlert.show();
					mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			}));
		
		// Add button to hbox
		buttonHBox.getChildren().add(deleteButton);
		
		
	}
	
	private void addInstructorButton(Button addInstructorButton, HBox instructorHBox) {
		addInstructorButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(instructorMax < 3) {
					ChoiceBox<Instructor> instructorChoice = new ChoiceBox<Instructor>();
					instructorChoice.setId("choiceBox");
					populateChoiceBox(instructorChoice);
					instructorChoice.setValue(instructorChoice.getItems().get(0));
					instructorChoiceBoxes.add(instructorChoice);
					instructorHBox.getChildren().add(instructorHBox.getChildren().size()-2, instructorChoice);
					instructorMax++;
				}else {
					return;
				}
			} 
			
			}));
	}
	
	private void removeInstructorButton(Button removeInstructorButton, HBox instructorHBox) {
		removeInstructorButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(instructorMax > 0) {
					instructorChoiceBoxes.remove(instructorChoiceBoxes.get(instructorChoiceBoxes.size()-1));
					instructorHBox.getChildren().remove(instructorHBox.getChildren().size()-3);
					instructorMax--;
				}else {
					return;
				}
			} 
			
			}));
	}
	
	// Deletes a client from the table
	private void deleteClient() {
		for(int i = 0; i < clientDB.getClientDB().size(); i++) {
			if(clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
				clientDB.getClientDB().remove(i);
				break;
			}
		}
		
		// Set client to null for next iteration
		clientTempReference.setClientReference(null);
		
	}
	
	
	// Finds client by ID number and name
	// Then replaces the object data in the array list with the data
	// From the field entries
	private void findAndReplaceClient(){
		for(int i = 0; i < clientDB.getClientDB().size(); i++) {
			if(clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
				clientDB.getClientDB().get(i).setClientName(nameField.getText());
				clientDB.getClientDB().get(i).setPhoneNumber(phoneNumberField.getText());
				clientDB.getClientDB().get(i).setAddressOfLessons(addressField.getText());
				clientDB.getClientDB().get(i).setNumberOfKids(Short.parseShort(parseData(kidsField.getText())));
				
				// Populate an instructor arraylist to add to a client ----
				ArrayList<Instructor> tempList = new ArrayList<Instructor>();
				for(int j = 0; j < instructorChoiceBoxes.size(); j++) {
					tempList.add(instructorChoiceBoxes.get(j).getValue());
				}
				// Garbage collection should be able to handle this
				// -------
				
				clientDB.getClientDB().get(i).setInstructor(tempList);
				clientDB.getClientDB().get(i).setNumberOfLessons(Short.parseShort(parseData(numberOfLessonsField.getText())));
				clientDB.getClientDB().get(i).setAmountPerLesson(Float.parseFloat(parseData(amountPerLessonField.getText())));
				clientDB.getClientDB().get(i).setPaidInFull(paidInFullRadio.isSelected());
				clientDB.getClientDB().get(i).updateTotal();
				break;
			}
		}
		
	}
}

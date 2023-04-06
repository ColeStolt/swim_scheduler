package code.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import code.customUI.MaskedTextField;
import code.dataObjects.Client;
import code.datapersistance_dao.ClientDataDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

	ClientDataDB clientDB = ClientDataDB.getInstance();
	
	@FXML private BorderPane mainScenePane;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;
	@FXML private VBox clientInfoEntriesVBox;
	@FXML private VBox clientLessonInfoEntryVBox;
	
	// Fields
	private TextField nameField;
	private MaskedTextField phoneNumberField;
	private TextField addressField;
	private MaskedTextField kidsField;
	private ChoiceBox<String> instructorChoice;
	private MaskedTextField numberOfLessonsField;
	private MaskedTextField amountPerLessonField;
	private RadioButton paidInFullRadio;
	
	// Panes
	HBox radioAndLabel;
	BorderPane titlePane;
	
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
	
	public void initialize() {
		instructorInfoSetup();
		lessonInfoSetup();
	}
	
	public void cancelClientAdd() {
		try {
			mainScenePane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
			mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveClient() {
		
		clientDB.getClientDB().add(new Client(nameField.getText(), phoneNumberField.getText(), addressField.getText(), Short.parseShort(parseData(kidsField.getText())), instructorChoice.getValue(), Short.parseShort(parseData(numberOfLessonsField.getText())), Float.parseFloat(parseData(amountPerLessonField.getText())), paidInFullRadio.isSelected()));
		clientDB.saveData();
		
		try {
			mainScenePane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
			mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String parseData(String data) {
		
		data = data.replace("-", "0");
		data = data.replace("$", "");
		return data;
	}
	
	public void populateChoiceBox(ChoiceBox<String> choiceBox) {
		try {
				BufferedReader dataFile = new BufferedReader(new FileReader("src\\data\\instructorNames.data"));
				String dataLine;
				while((dataLine = dataFile.readLine()) != null) {
					choiceBox.getItems().add(dataLine);
			}
			
			dataFile.close();
			
		} catch (FileNotFoundException e) {
			// Show error to user if file is not located
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load the \"instructorNames.data\" file.\nContact the developer about this issue.");
			sceneAlert.show();
		} catch (IOException e) {
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not parse data file in \"instructorNames \".\nContact the developer about this issue.");
			sceneAlert.show();
		}
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
		instructorChoice = new ChoiceBox<String>();
		numberOfLessonsField = new MaskedTextField("##", '-');
		amountPerLessonField = new MaskedTextField("$###.00", '-');
		paidInFullRadio = new RadioButton();
	
		
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
		instructorChoice.setValue("Niko");
		populateChoiceBox(instructorChoice);
		
		// Field settings
		numberOfLessonsField.setId("fields");
		amountPerLessonField.setId("fields");
		
		// Radio settings
		paidInFullRadio.setId("radioButton");
		
		// Declare Panes
		radioAndLabel = new HBox();
		titlePane = new BorderPane();
		
		// Pane settings
		titlePane.setId("titlePane");
		
		// Add to panes
		radioAndLabel.getChildren().add(paidInFullLabel);
		radioAndLabel.getChildren().add(paidInFullRadio);
		titlePane.setCenter(lessonInfoTitle);
		
		// Add to vbox
		clientLessonInfoEntryVBox.getChildren().add(titlePane);
		clientLessonInfoEntryVBox.getChildren().add(instructorLabel);
		clientLessonInfoEntryVBox.getChildren().add(instructorChoice);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsLabel);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsField);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonLabel);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonField);
		clientLessonInfoEntryVBox.getChildren().add(radioAndLabel);
	}
	
}

package code.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import code.customUI.MaskedTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
	
	@FXML private BorderPane mainScenePane;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;
	@FXML private VBox clientInfoEntriesVBox;
	
	// Fields
	private TextField nameField;
	private MaskedTextField phoneNumberField;
	private TextField addressField;
	private MaskedTextField kidsField;
	private ChoiceBox instructorField;
	
	
	// Labels
	private Label nameLabel;
	private Label phoneNumberLabel;
	private Label addressLabel;
	private Label kidsLabel;
	
	public void initialize() {
		instructorInfoSetup();
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
		
		try {
			BufferedWriter writeData = new BufferedWriter(new FileWriter("src\\data\\client.data"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void populateChoiceBox() {
		
	}
	
	public void instructorInfoSetup() {
		
		// Declare fields
		nameField = new TextField();
		phoneNumberField = new MaskedTextField("(###) ###-####");
		addressField = new TextField();
		kidsField = new MaskedTextField("###");
		
		// Declare Labels
		nameLabel = new Label("Client Name:");
		phoneNumberLabel = new Label("Phone Number:");
		addressLabel = new Label("Address Of Lessons:");
		kidsLabel = new Label("Number Of Kids:");
		
		// Label Settings
		phoneNumberLabel.setId("labels");
		nameLabel.setId("labels");
		addressLabel.setId("labels");
		kidsLabel.setId("labels");
		
		// Field Settings
		nameField.setId("fields");
		phoneNumberField.setId("phoneNumberField");
		phoneNumberField.setPlaceholder('_');
		addressField.setId("fields");
		kidsField.setId("fields");
		kidsField.setPlaceholder('\0');
		
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
		
	}
	
}

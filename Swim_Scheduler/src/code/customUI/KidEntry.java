package code.customUI;



import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Cole Stoltz
 * 
 * Purpose: Holds 3 UI objects in a nice format for entering Kid object information.
 *
 */

public class KidEntry extends HBox{

	// Instance Variables
	private TextField nameInputField;
	
	// Default Constructor
	public KidEntry() {
		UISetup();
	}
	
	// Parameterized constructor
	public KidEntry(String placeHolderText) {
		UISetup();
		nameInputField.setPromptText(placeHolderText);
	}
	
	public KidEntry(String placeHolderText, String fieldText) {
		UISetup();
		nameInputField.setPromptText(placeHolderText);
		nameInputField.setText(fieldText);
	}

	// UI Setup
	private void UISetup() {
		
		// Create objects
		nameInputField = new TextField();
		
		// Set IDs
		nameInputField.setId("fields");
		
		// Add to pane
		this.getChildren().add(nameInputField);
		
	}
	
	// Getters / Setter
	
	public TextField getNameInputField() {
		return nameInputField;
	}


	public void setNameInputField(TextField nameInputField) {
		this.nameInputField = nameInputField;
	}
	
}

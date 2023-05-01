package code.controllers;

import java.io.IOException;

import code.customUI.InstructorCard;
import code.datapersistance_dao.InstructorDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Controls the scene for viewing instructors and their clients
public class InstructorSceneController {

	// Instructor database
	InstructorDataDB instructorDB = InstructorDataDB.getInstance();
	MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	
	// Scene object
	@FXML VBox instructorListVBox;
	@FXML TextField instructorSearchBar;
	@FXML Button addInstructorButton;
	
	public void initialize() {
		instructorListVBox.setFillWidth(true);
		
		// Populate with instructor cards
		for(int i = 0; i < instructorDB.getInstructorDB().size(); i++) {
			instructorListVBox.getChildren().add(new InstructorCard(instructorDB.getInstructorDB().get(i)));
		}
	}
	
	public void addInstructor() {
		try {
			mainScreen.getPane().setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/InstructorDataFieldsScene.fxml")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// Same as client search capabilities
	public void updateClientList() {
		instructorListVBox.getChildren().removeAll(instructorListVBox.getChildren());
		for(int i = 0; i < instructorDB.getInstructorDB().size(); i++) {
			if(instructorDB.getInstructorDB().get(i).getInstructorName().toLowerCase().contains(instructorSearchBar.getText().toLowerCase())) {
				instructorListVBox.getChildren().add(new InstructorCard(instructorDB.getInstructorDB().get(i)));
			}
		}
	}
	
}

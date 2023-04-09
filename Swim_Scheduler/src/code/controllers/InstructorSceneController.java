package code.controllers;

import code.datapersistance_dao.InstructorDataDB;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

// Controls the scene for viewing instructors and their clients
public class InstructorSceneController {

	// Instructor database
	InstructorDataDB instructorDB = InstructorDataDB.getInstance();
	
	// Scene object
	@FXML VBox instructorListVBox;
	
}

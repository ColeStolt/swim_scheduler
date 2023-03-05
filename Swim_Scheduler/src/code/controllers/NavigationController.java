package code.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Cole Stoltz
 * Simple controller class to handle navigation between scenes
 * of the program.
 *
 * Each method contains an alert if the proper FXML file cannot be found.
 */

public class NavigationController {

	@FXML private BorderPane mainBorderPane;
	@FXML private Button homeButton;
	@FXML private Button scheduleButton;
	@FXML private Button clientsButton;
	@FXML private Button instructorsButton;
	
	/**
	 * @param ActionEvent
	 * @return void
	 * Switches to to home scene FXML file.
	 */
	
	public void switchToHomeScene(ActionEvent e) {
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/HomeScene.fxml")));
		} catch (IOException e1) {
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the \"home\" page.\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	
	/**
	 * 
	 * @param ActionEvent
	 * @return void
	 * Switches to scheduling scene FXML file.
	 */
	
	public void switchToScheduleScene(ActionEvent e) {
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduleScene.fxml")));
		} catch (IOException e1) {
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the \"schedule\" page.\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	
	/**
	 * @param ActioneEvent
	 * @return void
	 * Switches to client scene FXML file.
	 */
	
	public void switchToClientScene(ActionEvent e) {
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
		} catch (IOException e1) {
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the \"client\" page.\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	
	/**
	 * @param ActioneEvent
	 * @return void
	 * Switches to instructor scene FXML file.
	 */
	
	public void switchToInstructorScene(ActionEvent e) {
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/InstructorsScene.fxml")));
		} catch (IOException e1) {
			
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the \"instructor\" page.\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	
}

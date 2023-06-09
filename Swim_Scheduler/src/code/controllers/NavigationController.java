package code.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import code.datapersistance_dao.MainScreenSingleton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	@FXML private Label timeLabel;
	@FXML private Label dateLabel;
	
	// Main screen reference
	MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	
	/**
	 * No parameter function that initializes data before loading the FXML file.
	 * @return void
	 */
	
	public void initialize() {
		initializeDateAndTime();
		mainScreen.setPane(mainBorderPane);
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/HomeScene.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param ActionEvent
	 * @return void
	 * Switches to to home scene FXML file.
	 */
	
	public void switchToHomeScene(ActionEvent e) {
		
		try {
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/HomeScene.fxml")));
			homeButton.setStyle("-fx-background-color: #0f0f0f;"+
			"-fx-underline: true;");
			scheduleButton.setStyle("-fx-background-color: none;");
			clientsButton.setStyle("-fx-background-color: none;");
			instructorsButton.setStyle("-fx-background-color: none;");
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
			mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ScheduledAndUnscheduledScene.fxml")));
			homeButton.setStyle("-fx-background-color: none;");
			scheduleButton.setStyle("-fx-background-color: #0f0f0f;"+
					"-fx-underline: true;");
			clientsButton.setStyle("-fx-background-color: none;");
			instructorsButton.setStyle("-fx-background-color: none;");
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
			homeButton.setStyle("-fx-background-color: none;");
			scheduleButton.setStyle("-fx-background-color: none;");
			clientsButton.setStyle("-fx-background-color: #0f0f0f;"+
					"-fx-underline: true;");
			instructorsButton.setStyle("-fx-background-color: none;");
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
			homeButton.setStyle("-fx-background-color: none;");
			scheduleButton.setStyle("-fx-background-color: none;");
			clientsButton.setStyle("-fx-background-color: none;");
			instructorsButton.setStyle("-fx-background-color: #0f0f0f;"+
					"-fx-underline: true;");
		} catch (IOException e1) {
			
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load a scene file for the \"instructor\" page.\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	
	/**
	 * Sets the date and time to update every second.
	 */
	
	public void initializeDateAndTime() {
		AnimationTimer updater = new AnimationTimer() {
			@Override // This must be overwritten when using the timer
			// Is called every frame
			public void handle(long now) {
				timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:mm a")));
				dateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			}
			
		};
		
		updater.start();
		
	}
	
}

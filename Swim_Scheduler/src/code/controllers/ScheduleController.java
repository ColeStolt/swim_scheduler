package code.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import code.services.TextService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ScheduleController {

	@FXML Label sundayLabel;
	@FXML Label mondayLabel;
	@FXML Label tuesdayLabel;
	@FXML Label wednesdayLabel;
	@FXML Label thursdayLabel;
	@FXML Label fridayLabel;
	@FXML Label saturdayLabel;
	@FXML Button sundayButton;
	@FXML Button mondayButton;
	@FXML Button tuesdayButton;
	@FXML Button wednesdayButton;
	@FXML Button thursdayButton;
	@FXML Button fridayButton;
	@FXML Button saturdayButton;
	@FXML Button backButton;
	@FXML Button forwardButton;
	@FXML ComboBox<String> repeatComboBox;
	
	public void initialize() {
		String format = "MM/dd/yyyy";
		String input = "10/14/2000";
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);

		LocalDate date2 = LocalDate.now();
		System.out.println(df.format(date2.plusDays(100)));

		date2 = date2.plusDays(100);
		
		System.out.println(date2.getDayOfWeek());
		
		setupComboBox();
	}
	

	public void setupComboBox() {
		
		repeatComboBox.getItems().add("1 WEEK");
		repeatComboBox.setValue(repeatComboBox.getItems().get(0));
		
		for(int i = 0; i < 7; i++) {
			repeatComboBox.getItems().add(i + 2 + " WEEKS");
		}
	}
	
	public void sendAndSaveSchedule() {
		TextService.sendText("", "");
	}
	
}

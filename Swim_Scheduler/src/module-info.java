module Swim_Scheduler {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires twilio;

	
	opens main to javafx.graphics, javafx.fxml;
	opens code.controllers to javafx.graphics, javafx.fxml;
}

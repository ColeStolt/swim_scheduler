module Swim_Scheduler {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens main to javafx.graphics, javafx.fxml;
	opens code.controllers to javafx.graphics, javafx.fxml;
}
